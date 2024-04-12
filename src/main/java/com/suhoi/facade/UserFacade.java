package com.suhoi.facade;

import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;

public interface UserFacade {
    void signUp(CreateUserDto createUserDto);
    void auth (AuthDto authDto);
}
