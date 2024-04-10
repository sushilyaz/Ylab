package com.suhoi.service;

import com.suhoi.model.TypeOfTraining;

import java.util.List;

public interface TypeOfTrainingService {
    TypeOfTraining getType(String type);

    List<TypeOfTraining> getAllType();

    void save(TypeOfTraining build);
}
