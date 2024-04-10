package com.suhoi.in.controller.impl;

import com.suhoi.exception.PermissionDeniedException;
import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.in.controller.TypeOfTrainController;
import com.suhoi.model.Role;
import com.suhoi.model.TypeOfTrain;
import com.suhoi.service.TrainService;
import com.suhoi.service.TypeOfTrainService;
import com.suhoi.service.impl.TrainServiceImpl;
import com.suhoi.service.impl.TypeOfTrainServiceImpl;
import com.suhoi.util.UserUtils;

import java.util.Scanner;

public class TypeOfTrainControllerImpl implements TypeOfTrainController {

    private final TypeOfTrainService typeOfTrainService;

    public TypeOfTrainControllerImpl() {
        this.typeOfTrainService = TypeOfTrainServiceImpl.getInstance();
    }

    @Override
    public void addNewTypeOfTrainings() {
        if (UserUtils.getCurrentUser().getRole().equals(Role.SIMPLE)) {
            throw new PermissionDeniedException("Permission denied");
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new type of train: ");
        String typeOfTrain = scanner.nextLine();

        TypeOfTrain build = TypeOfTrain.builder()
                .name(typeOfTrain)
                .build();

        typeOfTrainService.save(build);
        TrainingDailyRunner.menu();
    }
}
