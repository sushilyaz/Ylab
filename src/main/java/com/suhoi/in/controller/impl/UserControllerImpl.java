package com.suhoi.in.controller.impl;

import com.suhoi.dto.UserDto;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.in.controller.UserController;
import com.suhoi.model.User;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.UserService;
import com.suhoi.service.impl.UserServiceImpl;
import com.suhoi.util.UserUtils;

import java.util.Optional;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl() {
        this.userService = new UserServiceImpl(UserRepositoryImpl.getInstance());
    }

    // POST
    @Override
    public void signUp() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username (min 4 symbol): ");
        String username = scanner.nextLine();
        System.out.print("Enter password (min 4 symbol): ");
        String password = scanner.nextLine();

        /*
        Когда приложение закончит выполнение, выдаст все ошибки, которые были совершены в ходе работы приложения (кастомные exceptions)
         */
        if (username.length() < 4 || password.length() < 4) {
            throw new NoValidDataException("Data no valid. Username and password must be consist minimum 4 symbols");
        }

        UserDto userDto = UserDto.builder()
                .username(username)
                .password(password)
                .build();

        userService.signUp(userDto);

        TrainingDailyRunner.start();
    }

    @Override
    public void signIn() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String authUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String authPassword = scanner.nextLine();

        UserDto userDto = UserDto.builder()
                .username(authUsername)
                .password(authPassword)
                .build();

        userService.signIn(userDto);
    }
}
