package com.suhoi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    Long id;
    String username;
    String password;
    Role role; // админ или обычный пользователь
}
