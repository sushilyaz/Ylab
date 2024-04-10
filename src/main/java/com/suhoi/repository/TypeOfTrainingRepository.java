package com.suhoi.repository;

import com.suhoi.model.TypeOfTraining;

import java.util.List;
import java.util.Optional;

public interface TypeOfTrainingRepository {

    void save(TypeOfTraining build);

    Optional<TypeOfTraining> getTypeByName(String name);

    List<TypeOfTraining> getTypesOfTrain();
}
