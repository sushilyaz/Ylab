package com.suhoi.util;

import com.suhoi.model.User;
import lombok.Getter;
import lombok.Setter;

public class UserUtils {

    // Метод для получения текущего пользователя
    @Getter
    @Setter
    private static User currentUser;

    private UserUtils() {}

}
