package com.suhoi.service.impl;

import com.suhoi.exception.DataNotFoundException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.repository.impl.TypeOfTrainingRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.TypeOfTrainingService;

import java.util.List;

public class TypeOfTrainingServiceImpl implements TypeOfTrainingService {


    private static volatile TypeOfTrainingServiceImpl INSTANCE;
    private final TypeOfTrainingRepository typeOfTrainingRepository;
    private final AuditService auditService;

    private TypeOfTrainingServiceImpl() {
        this.typeOfTrainingRepository = TypeOfTrainingRepositoryImpl.getInstance();
        this.auditService = AuditServiceImpl.getInstance();
    }

    public static TypeOfTrainingServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TypeOfTrainingServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TypeOfTrainingServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public TypeOfTraining getTypeByName(String type) {
        try {
            return typeOfTrainingRepository.getTypeByName(type)
                    .orElseThrow(() -> new DataNotFoundException("Type of train with name '" + type + "' doesn't exist"));
        } catch (DataNotFoundException e) {
            auditService.save("called TypeOfTrainingsService getTypeByName");
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
            return null;
        }
    }

    @Override
    public List<TypeOfTraining> getAllType() {
        auditService.save("called getAllType");
        return typeOfTrainingRepository.getTypesOfTrain();
    }

    @Override
    public void save(String name) {
        auditService.save("called TypeOfTrainingsService save");
        try {
            typeOfTrainingRepository.getTypeByName(name).ifPresent(typeOfTraining -> {
                throw new DataNotFoundException("This type already exist");
            });
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
        }

        TypeOfTraining build = TypeOfTraining.builder()
                .name(name)
                .build();

        typeOfTrainingRepository.save(build);
    }
}
