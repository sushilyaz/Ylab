package com.suhoi.in.controller;

import com.suhoi.dto.*;
import com.suhoi.model.Training;

import java.util.List;

public interface TrainingController {
    /**
     * Добавление новой тренировки
     */
    void addNewTraining(CreateTrainingDto dto);

    /**
     * Запрос на получение всех тренировок пользователя
     *
     * @return
     */
    List<TrainingDto> getAllTrainingForUser();


    /**
     * Получения сущности тренировок (с id для удаления)
     *
     * @return
     */
    List<Training> getAllTrainingForUserWithId();

    /**
     * Запрос на получение сженных калорий в срезе времени.
     * Границы диапазона НЕ ВКЛЮЧИТЕЛЬНЫ!!!
     *
     * @return
     */
    Integer getCaloriesBetweenDates(RangeDto dto);

    /**
     * Обновление существующей записи
     */
    void edit(UpdateTrainingDto dto);

    /**
     * Удаление существующей записи
     */
    void delete(Long id);

}
