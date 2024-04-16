package com.suhoi.util;

import com.suhoi.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс для хранения текущего пользователя
 */
public final class UserUtils {
    @Getter
    @Setter
    private static User currentUser;

    private UserUtils() {
    }

}
