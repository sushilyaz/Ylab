package com.suhoi.facade;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;

/**
 * Ментор Алексей намекнул, что вместо того, чтобы возвращать DTO из DAO можно использовать паттерн "Фасад"
 * Находится между контроллером и сервисом.
 * Тут я собираю DTOшки из сущностей и наоборот
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
