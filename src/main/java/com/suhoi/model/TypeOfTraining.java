package com.suhoi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO типов тренировок
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfTraining {
    Long id;
    String name; // название тренировки
}
