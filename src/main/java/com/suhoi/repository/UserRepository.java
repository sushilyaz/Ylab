package com.suhoi.repository;

import com.suhoi.model.User;

import java.util.Optional;

public interface UserRepository {

    /**
     * Сохранение нового пользователя
     *
     * @param user
     */
    void save(User user);

    /**
     * Поиск пользователя по имени
     *
     * @param username
     * @return
     */
    Optional<User> getUserByUsername(String username);
}
