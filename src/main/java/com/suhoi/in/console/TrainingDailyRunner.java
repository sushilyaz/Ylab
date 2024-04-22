package com.suhoi.in.console;

import com.suhoi.util.ConnectionPool;
import com.suhoi.util.UserUtils;
import java.util.Scanner;


public class TrainingDailyRunner {
    /**
     * Окно авторизации и регистрации
     */
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
                StartHandler.registration();
                break;
            case 2:
                StartHandler.authentication();
                break;
            case 3:
                System.out.println("Exit");
                ConnectionPool.closePool();
                System.exit(0);
                break;
            default:
                start();
                System.out.println("Invalid number");
        }
    }

    /**
     * Главное меню
     */
    public static void menu() {
        System.out.println();
        System.out.println("Choose action: ");
        System.out.println("1 - Add trainings");
        System.out.println("2 - Get all trainings sorted by Date for current user. (if you login as Admin - for all users)");
        System.out.println("3 - Get burned calories between Date");
        System.out.println("4 - Update training");
        System.out.println("5 - Delete training");
        System.out.println("6 - Add new type of trainings (for admin)");
        System.out.println("7 - Open audit (for admin)");
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
                    MenuHandler.addNewTraining();
                    TrainingDailyRunner.menu();
                    break;
                case 2:
                    MenuHandler.getAllTrainingsForUser();
                    TrainingDailyRunner.menu();
                    break;
                case 3:
                    MenuHandler.getCaloriesBetweenDates();
                    TrainingDailyRunner.menu();
                    break;
                case 4:
                    MenuHandler.edit();
                    TrainingDailyRunner.menu();
                    break;
                case 5:
                    MenuHandler.delete();
                    TrainingDailyRunner.menu();
                    break;
                case 6:
                    MenuHandler.addNewTypeOfTraining();
                    TrainingDailyRunner.menu();
                    break;
                case 7:
                    MenuHandler.getAudit();
                    TrainingDailyRunner.menu();
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
