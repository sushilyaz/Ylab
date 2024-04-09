package com.suhoi.in;

import com.suhoi.in.controller.TrainController;
import com.suhoi.in.controller.TypeOfTrainController;
import com.suhoi.in.controller.UserController;
import com.suhoi.in.controller.impl.UserControllerImpl;
import com.suhoi.model.User;
import com.suhoi.util.UserUtils;

import java.util.Scanner;


public class TrainingDailyRunner {

    private static UserController userController = new UserControllerImpl();
    private static TrainController trainController;
    private static TypeOfTrainController typeOfTrainController;

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
        System.out.println("Вы попали в главное меню");
    }

}
