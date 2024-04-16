package com.suhoi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO аудита
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit {
    Long id;
    String username;
    String action;
    LocalDateTime dateTime;
}
