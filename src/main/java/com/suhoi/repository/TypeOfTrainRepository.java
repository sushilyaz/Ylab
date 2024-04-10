package com.suhoi.repository;

import com.suhoi.model.TypeOfTrain;

import java.util.List;
import java.util.Optional;

public interface TypeOfTrainRepository {

    void save(TypeOfTrain build);

    Optional<TypeOfTrain> getTypeByName(String name);

    List<TypeOfTrain> getTypesOfTrain();
}
