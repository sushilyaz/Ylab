package com.suhoi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto создания пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    String username;
    String password;
}
