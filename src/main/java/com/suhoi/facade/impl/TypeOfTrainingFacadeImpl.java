package com.suhoi.facade.impl;

import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.service.TypeOfTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypeOfTrainingFacadeImpl implements TypeOfTrainingFacade {

    private final TypeOfTrainingService typeOfTrainingService;

    @Override
    public void addNewTypeOfTraining(String name) {
        typeOfTrainingService.save(name);
    }
}
