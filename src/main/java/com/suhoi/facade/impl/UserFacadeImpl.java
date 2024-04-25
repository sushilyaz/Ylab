package com.suhoi.facade.impl;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.facade.UserFacade;
import com.suhoi.mapper.UserMapper;
import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.service.UserService;
import com.suhoi.service.impl.UserServiceImpl;
import org.mapstruct.factory.Mappers;

public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void signUp(CreateUserDto createUserDto) {
        User user = userMapper.toEntity(createUserDto);
        userService.createUserIfNotExist(user);
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
