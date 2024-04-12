package com.suhoi.in.controller.impl;

import com.suhoi.exception.PermissionDeniedException;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.facade.impl.TrainingFacadeImpl;
import com.suhoi.facade.impl.TypeOfTrainingFacadeImpl;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.in.controller.TypeOfTrainingController;
import com.suhoi.model.Role;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.util.UserUtils;

public class TypeOfTrainingControllerImpl implements TypeOfTrainingController {

    private static volatile TypeOfTrainingControllerImpl INSTANCE;
    private TypeOfTrainingFacade typeOfTrainingFacade;

    private TypeOfTrainingControllerImpl() {
        this.typeOfTrainingFacade = TypeOfTrainingFacadeImpl.getInstance();
    }

    public static TypeOfTrainingControllerImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TypeOfTrainingControllerImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TypeOfTrainingControllerImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void addNewTypeOfTrainings(String name) {
        try {
            if (!UserUtils.getCurrentUser().getRole().equals(Role.ADMIN)) {
                throw new PermissionDeniedException("Permission denied");
            }
        } catch (PermissionDeniedException e) {
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
        }
        typeOfTrainingFacade.addNewTypeOfTraining(name);
    }
}
