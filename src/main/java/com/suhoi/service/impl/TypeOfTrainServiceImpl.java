package com.suhoi.service.impl;

import com.suhoi.exception.TypeIsNotExistException;
import com.suhoi.exception.UserIsExistException;
import com.suhoi.model.TypeOfTrain;
import com.suhoi.repository.TypeOfTrainRepository;
import com.suhoi.repository.impl.TrainRepositoryImpl;
import com.suhoi.repository.impl.TypeOfTrainRepositoryImpl;
import com.suhoi.service.TypeOfTrainService;

import java.util.List;
import java.util.Optional;

public class TypeOfTrainServiceImpl implements TypeOfTrainService {

    private static volatile TypeOfTrainServiceImpl INSTANCE;

    private final TypeOfTrainRepository typeOfTrainRepository;

    private TypeOfTrainServiceImpl() {
        this.typeOfTrainRepository = TypeOfTrainRepositoryImpl.getInstance();
    }

    public static TypeOfTrainServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TypeOfTrainServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TypeOfTrainServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public TypeOfTrain getType(String type) {
        return typeOfTrainRepository.getTypeByName(type)
                .orElseThrow(() -> new TypeIsNotExistException("Type of train with name '" + type + "' doesn't exist"));
    }

    @Override
    public List<TypeOfTrain> getAllType() {
        return typeOfTrainRepository.getTypesOfTrain();
    }
}
