package com.suhoi.facade.impl;

import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.service.TrainingService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.service.impl.TrainingServiceImpl;
import com.suhoi.service.impl.TypeOfTrainingServiceImpl;

public class TypeOfTrainingFacadeImpl implements TypeOfTrainingFacade {

    private final TypeOfTrainingService typeOfTrainingService;

    public TypeOfTrainingFacadeImpl(TypeOfTrainingService typeOfTrainingService) {
        this.typeOfTrainingService = typeOfTrainingService;
    }
//    private static volatile TypeOfTrainingFacadeImpl INSTANCE;
//
//    private final TypeOfTrainingService typeOfTrainingService;
//
//    private TypeOfTrainingFacadeImpl() {
//        this.typeOfTrainingService = TypeOfTrainingServiceImpl.getInstance();
//    }
//
//    public static TypeOfTrainingFacadeImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (TypeOfTrainingFacadeImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new TypeOfTrainingFacadeImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void addNewTypeOfTraining(String name) {
        typeOfTrainingService.save(name);
    }
}
