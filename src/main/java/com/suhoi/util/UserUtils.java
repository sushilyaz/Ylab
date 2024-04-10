package com.suhoi.util;

import com.suhoi.model.User;
import lombok.Getter;
import lombok.Setter;

public class UserUtils {

    // Геттер и сеттер для текущего пользователя
    @Getter
    @Setter
    private static User currentUser;

    private UserUtils() {}

}
