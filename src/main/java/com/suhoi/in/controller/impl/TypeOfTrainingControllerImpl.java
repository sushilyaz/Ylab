package com.suhoi.in.controller.impl;

import com.suhoi.exception.PermissionDeniedException;
import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.in.controller.TypeOfTrainingController;
import com.suhoi.model.Role;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.impl.TypeOfTrainingRepositoryImpl;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.service.impl.TypeOfTrainingServiceImpl;
import com.suhoi.util.UserUtils;

import java.util.Scanner;

public class TypeOfTrainingControllerImpl implements TypeOfTrainingController {

    private final TypeOfTrainingService typeOfTrainingService;

    public TypeOfTrainingControllerImpl() {
        this.typeOfTrainingService = new TypeOfTrainingServiceImpl(TypeOfTrainingRepositoryImpl.getInstance());
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

        TypeOfTraining build = TypeOfTraining.builder()
                .name(typeOfTrain)
                .build();

        typeOfTrainingService.save(build);
    }
}
