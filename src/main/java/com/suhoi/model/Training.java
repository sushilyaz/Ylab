package com.suhoi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

/**
 * POJO trainings
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Training {
    private Long id;
    private Long userId; // FK user.id
    private Long typeOfTrainingId; // FK type_of_train.id
    private Duration duration; // продолжительность
    private Integer calories; // сженные калории
    private Map<String, String> advanced;
    private LocalDate date;
}
