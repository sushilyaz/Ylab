package com.suhoi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Dto обновления тренировки
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTrainingDto {
    private Long id;
    private Integer calories;
    private Map<String, String> advanced;
}
