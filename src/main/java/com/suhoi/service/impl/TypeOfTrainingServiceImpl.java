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

    private final TypeOfTrainingRepository typeOfTrainingRepository;
    private final AuditService auditService;

    public TypeOfTrainingServiceImpl(TypeOfTrainingRepository typeOfTrainingRepository, AuditService auditService) {
        this.typeOfTrainingRepository = typeOfTrainingRepository;
        this.auditService = auditService;
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
        if (typeOfTrainingRepository.getTypeByName(name).isPresent()) {
            System.out.println("Данный тип уже существует");
            TrainingDailyRunner.menu();
        }

        TypeOfTraining build = TypeOfTraining.builder()
                .name(name)
                .build();

        typeOfTrainingRepository.save(build);
    }
}
