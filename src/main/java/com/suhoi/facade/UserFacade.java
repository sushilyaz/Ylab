package com.suhoi.facade;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.model.User;

/**
 * Паттерн фасад
 */
public interface UserFacade {

    /**
     * Собираю сущность из DTO
     */
    User signUp(CreateUserDto createUserDto);

    /**
     * Собираю сущность из DTO
     */
    void auth (AuthDto authDto);
}
