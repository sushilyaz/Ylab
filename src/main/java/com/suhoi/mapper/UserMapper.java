package com.suhoi.mapper;

import com.suhoi.dto.CreateUserDto;
import com.suhoi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для сущности пользователь
 */
@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(com.suhoi.model.Role.SIMPLE)")
    User toEntity(CreateUserDto createUserDto);
}
