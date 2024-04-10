package com.suhoi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Train {
    private Long id;
    private Long userId; // FK user.id
    private Long typeOfTrainId; // FK type_of_train.id
    private Duration duration; // продолжительность
    private Integer calories; // сженные калории
    /*
     Доп. инфа, например: количество упражнений, количество ввыпитого протеина, количество уколов тренболона итд
     P.S. В БД планирую хранить в JSONB, если подход плохой - дайте знать пж
     */
    private Map<String, String> advanced;
    private LocalDate date;
}
