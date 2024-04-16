package com.suhoi.in.controller.impl;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.facade.UserFacade;
import com.suhoi.facade.impl.UserFacadeImpl;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.in.controller.UserController;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.UserService;
import com.suhoi.service.impl.UserServiceImpl;

import java.util.Scanner;

public class UserControllerImpl implements UserController {

    private final UserFacade userFacade;

    public UserControllerImpl(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    //    private static volatile UserControllerImpl INSTANCE;
//    private UserFacade userFacade;
//
//    private UserControllerImpl() {
//        this.userFacade = UserFacadeImpl.getInstance();
//    }
//
//    public static UserControllerImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (UserControllerImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new UserControllerImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void signUp(CreateUserDto createUserDto) {
        userFacade.signUp(createUserDto);
    }
    // POST
    @Override
    public void authentication(AuthDto authDto) {
        userFacade.auth(authDto);
    }
}
