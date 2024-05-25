package com.suhoi.service.impl;

import com.suhoi.annotation.Auditable;
import com.suhoi.exception.DataAlreadyExistException;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.TypeOfTrainingRepository;
import com.suhoi.service.TypeOfTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public TypeOfTraining save(String name) {
        if (typeOfTrainingRepository.getTypeByName(name).isPresent()) {
            throw new DataAlreadyExistException("Type of training with name '" + name + "' already exists");
        }

        TypeOfTraining build = TypeOfTraining.builder()
                .name(name)
                .build();

        return typeOfTrainingRepository.save(build);
    }
}
