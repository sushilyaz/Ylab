package com.suhoi.facade.impl;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.facade.UserFacade;
import com.suhoi.mapper.UserMapper;
import com.suhoi.model.User;
import com.suhoi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

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
