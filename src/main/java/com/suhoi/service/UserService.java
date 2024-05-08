package com.suhoi.service;

import com.suhoi.model.User;

public interface UserService {
    /**
     * Регистрация нового пользователя
     * Если пользователь уже существует - исключение
     * Если нет - сохраняем
     */
    User createUserIfNotExist(User createUser);

    /**
     * Авторизация пользователя
     * Если такой пользователь существует и пароль введен правильно - авторизация
     * Если хоть 1 из этих требований не соблюдено - исключение
     *
     * @param authUser
     */
    void auth(User authUser);
}
