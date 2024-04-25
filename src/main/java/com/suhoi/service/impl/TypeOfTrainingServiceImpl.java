package com.suhoi.service.impl;

import com.suhoi.annotation.Auditable;
import com.suhoi.exception.DataAlreadyExistException;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.service.TypeOfTrainingService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TypeOfTrainingServiceImpl implements TypeOfTrainingService {

    private final TypeOfTrainingRepository typeOfTrainingRepository;

    @Override
    public TypeOfTraining getTypeByName(String type) {
            return typeOfTrainingRepository.getTypeByName(type)
                    .orElseThrow(() -> new DataNotFoundException("Type of train with name '" + type + "' doesn't exist"));
    }

    @Override
    public List<TypeOfTraining> getAllType() {
        return typeOfTrainingRepository.getTypesOfTrain();
    }

    @Auditable
    @Override
    public void save(String name) {
        if (typeOfTrainingRepository.getTypeByName(name).isPresent()) {
            throw new DataAlreadyExistException("Type of training with name '" + name + "' already exists");
        }

        TypeOfTraining build = TypeOfTraining.builder()
                .name(name)
                .build();

        typeOfTrainingRepository.save(build);
    }
}
