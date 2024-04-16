package com.suhoi.facade.impl;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.facade.UserFacade;
import com.suhoi.in.controller.impl.UserControllerImpl;
import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.service.UserService;
import com.suhoi.service.impl.UserServiceImpl;

public class UserFacadeImpl implements UserFacade {

    private UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    //    private static volatile UserFacadeImpl INSTANCE;
//
//    private UserService userService;
//
//    private UserFacadeImpl() {
//        this.userService = UserServiceImpl.getInstance();
//    }
//
//    public static UserFacadeImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (UserFacadeImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new UserFacadeImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void signUp(CreateUserDto createUserDto) {
        User createUser = User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .role(Role.SIMPLE)
                .build();
        userService.createUserIfNotExist(createUser);
    }

    @Override
    public void auth(AuthDto authDto) {
        User authUser = User.builder()
                .username(authDto.getUsername())
                .password(authDto.getPassword())
                .build();
        userService.auth(authUser);
    }
}
