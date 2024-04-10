package com.suhoi.service.impl;

import com.suhoi.exception.DataNotFoundException;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.repository.impl.TypeOfTrainingRepositoryImpl;
import com.suhoi.service.TypeOfTrainingService;

import java.util.List;

public class TypeOfTrainingServiceImpl implements TypeOfTrainingService {

    private static volatile TypeOfTrainingServiceImpl INSTANCE;

    private final TypeOfTrainingRepository typeOfTrainingRepository;

    private TypeOfTrainingServiceImpl() {
        this.typeOfTrainingRepository = TypeOfTrainingRepositoryImpl.getInstance();
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
    public TypeOfTraining getType(String type) {
        return typeOfTrainingRepository.getTypeByName(type)
                .orElseThrow(() -> new DataNotFoundException("Type of train with name '" + type + "' doesn't exist"));
    }

    @Override
    public List<TypeOfTraining> getAllType() {
        return typeOfTrainingRepository.getTypesOfTrain();
    }

    @Override
    public void save(TypeOfTraining build) {
        typeOfTrainingRepository.save(build);
    }
}
