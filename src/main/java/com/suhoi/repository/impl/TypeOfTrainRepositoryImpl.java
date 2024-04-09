package com.suhoi.repository.impl;

import com.suhoi.model.TypeOfTrain;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TypeOfTrainRepository;

import java.util.List;
import java.util.Optional;

public class TypeOfTrainRepositoryImpl implements TypeOfTrainRepository {

    private static volatile TypeOfTrainRepositoryImpl INSTANCE;

    private TypeOfTrainRepositoryImpl() {
    }

    public static TypeOfTrainRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TypeOfTrainRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TypeOfTrainRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(String name) {

    }

    @Override
    public Optional<TypeOfTrain> getTypeByName(String name) {
        return RuntimeDB.getType_of_trains().stream()
                .filter(o -> o.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<TypeOfTrain> getTypesOfTrain() {
        return RuntimeDB.getType_of_trains();
    }
}
