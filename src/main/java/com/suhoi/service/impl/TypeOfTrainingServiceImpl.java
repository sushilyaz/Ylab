package com.suhoi.service.impl;

import com.suhoi.exception.DataNotFoundException;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.model.User;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.repository.impl.TypeOfTrainingRepositoryImpl;
import com.suhoi.service.TypeOfTrainingService;

import java.util.List;

public class TypeOfTrainingServiceImpl implements TypeOfTrainingService {


    private final TypeOfTrainingRepository typeOfTrainingRepository;

    public TypeOfTrainingServiceImpl(TypeOfTrainingRepository typeOfTrainingRepository) {
        this.typeOfTrainingRepository = typeOfTrainingRepository;
    }

    @Override
    public TypeOfTraining getType(String type) {
        // Если такого типа нет - бросаем эксепшен
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
