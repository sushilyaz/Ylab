package com.suhoi.facade;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;

/**
 * Паттерн фасад
 */
public interface UserFacade {

    /**
     * Собираю сущность из DTO
     */
    void signUp(CreateUserDto createUserDto);

    /**
     * Собираю сущность из DTO
     */
    void auth (AuthDto authDto);
}
