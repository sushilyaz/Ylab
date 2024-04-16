package com.suhoi.repository.impl;

import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TypeOfTrainingRepository;

import java.util.List;
import java.util.Optional;

public class TypeOfTrainingRepositoryImpl implements TypeOfTrainingRepository {

//    private static volatile TypeOfTrainingRepositoryImpl INSTANCE;
//
//    private TypeOfTrainingRepositoryImpl() {
//    }
//
//    public static TypeOfTrainingRepositoryImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (TypeOfTrainingRepositoryImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new TypeOfTrainingRepositoryImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void save(TypeOfTraining build) {
        RuntimeDB.addTypeOfTrain(build);
        System.out.println("New type added success");
    }

    @Override
    public Optional<TypeOfTraining> getTypeByName(String name) {
        return RuntimeDB.getType_of_training().stream()
                .filter(o -> o.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<TypeOfTraining> getTypesOfTrain() {
        return RuntimeDB.getType_of_training();
    }
}
