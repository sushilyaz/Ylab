package com.suhoi.in;

import com.suhoi.in.controller.TrainingController;
import com.suhoi.in.controller.TypeOfTrainingController;
import com.suhoi.in.controller.UserController;
import com.suhoi.in.controller.impl.TrainingControllerImpl;
import com.suhoi.in.controller.impl.TypeOfTrainingControllerImpl;
import com.suhoi.in.controller.impl.UserControllerImpl;
import com.suhoi.util.UserUtils;

import java.util.Scanner;


public class TrainingDailyRunner {

    private static final UserController userController = new UserControllerImpl();
    private static final TrainingController trainingController = new TrainingControllerImpl();
    private static final TypeOfTrainingController typeOfTrainingController = new TypeOfTrainingControllerImpl();

    public static void start() {
        System.out.println();
        System.out.println("At first you need to register or log in. Choose an action");
        System.out.println("1 - Registration user (enter \"1\")");
        System.out.println("2 - Login user (enter \"2\")");
        System.out.println("3 - Exit (enter \"3\")");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter: ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid number");
            start();
        }

        switch (choice) {
            case 1:
                userController.signUp();
                break;
            case 2:
                userController.signIn();
                menu();
                break;
            case 3:
                System.out.println("Exit");
                System.exit(0);
                break;
            default:
                start();
                System.out.println("Invalid number");
        }
    }

    public static void menu() {
        System.out.println();
        System.out.println("Choose action: ");
        System.out.println("1 - Add trainings");
        System.out.println("2 - Get all trainings sorted by Date for current user. (if you login as Admin - for all users)");
        System.out.println("3 - Get burned calories between Date");
        System.out.println("4 - Update training");
        System.out.println("5 - Delete training");
        System.out.println("6 - Add new type of trainings (for ADMIN!)");
        System.out.println("7 - ");
        System.out.println("8 - Log out");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter: ");
            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid number");
                menu();
            }
            switch (choice) {
                case 1:
                    trainingController.addTrain();
                    break;
                case 2:
                    trainingController.getAllSortedTrainings();
                    break;
                case 3:
                    trainingController.getCaloriesBetweenDates();
                    break;
                case 4:
                    trainingController.edit();
                    break;
                case 5:
                    trainingController.delete();
                    break;
                case 6:
                    typeOfTrainingController.addNewTypeOfTrainings();
                    break;
                case 8:
                    UserUtils.setCurrentUser(null);
                    start();
                    break;
                default:
                    System.out.println("Invalid number");
                    menu();
            }
        }
    }

}
