package com.suhoi.in.controller;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;

public interface UserController {
    /**
     * Регистрация
     */
    void signUp(CreateUserDto createUserDto);
    /**
     * Авторизация
     */
    void authentication(AuthDto authDto);
}
