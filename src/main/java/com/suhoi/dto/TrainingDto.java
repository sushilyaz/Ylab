package com.suhoi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

/**
 * Dto вывода тренировки (для читаемости)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingDto {
    String typeOfTrain;
    Duration duration;
    Integer calories;
    Map<String, String> advanced;
    LocalDate date;
}
