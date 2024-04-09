package com.suhoi.service;

import com.suhoi.dto.UserDto;

public interface UserService {
    void signUp(UserDto dto);

    void signIn(UserDto dto);
}
