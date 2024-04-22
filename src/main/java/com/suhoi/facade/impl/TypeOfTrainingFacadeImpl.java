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

    @Override
    public void addNewTypeOfTraining(String name) {
        typeOfTrainingService.save(name);
    }
}
