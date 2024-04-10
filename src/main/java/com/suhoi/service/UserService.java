package com.suhoi.service;

import com.suhoi.dto.UserDto;

public interface UserService {
    /**
     * Регистрация нового пользователя
     * Если пользователь уже существует - исключение
     * Если нет - сохраняем
     *
     * @param dto
     */
    void signUp(UserDto dto);

    /**
     * Авторизация пользователя
     * Если такой пользователь существует и пароль введен правильно - авторизация
     * Если хоть 1 из этих требований не соблюдено - исключение
     *
     * @param dto
     */
    void signIn(UserDto dto);
}
