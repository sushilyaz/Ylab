package com.suhoi.in.console;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.in.controller.UserController;
import com.suhoi.in.controller.impl.UserControllerImpl;
import com.suhoi.util.DI;

import java.util.Scanner;

public class StartHandler {

    private static final UserController userController = DI.userControllerDI();

    /**
     * Взаимодействие с пользователем в окне регистрации
     */
    public static void registration() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username (min 4 symbol): ");
        String username = scanner.nextLine();
        System.out.print("Enter password (min 4 symbol): ");
        String password = scanner.nextLine();

        try {
            if (username.length() < 4 || password.length() < 4) {
                throw new NoValidDataException("Data no valid. Username and password must be consist minimum 4 symbols");
            }
        } catch (NoValidDataException e) {
            System.out.println(e.getMessage());
            TrainingDailyRunner.start();
        }

        CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .password(password)
                .build();

        userController.signUp(createUserDto);
        TrainingDailyRunner.start();
    }

    /**
     * Взаимодействие с пользователем в окне авторизаци
     */
    public static void authentication() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String authUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String authPassword = scanner.nextLine();

        AuthDto authDto = AuthDto.builder()
                .username(authUsername)
                .password(authPassword)
                .build();

        userController.authentication(authDto);
        TrainingDailyRunner.menu();
    }
}
