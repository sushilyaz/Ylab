package com.suhoi.service;

import com.suhoi.model.TypeOfTrain;

import java.util.List;

public interface TypeOfTrainService {
    TypeOfTrain getType(String type);

    List<TypeOfTrain> getAllType();

    void save(TypeOfTrain build);
}
