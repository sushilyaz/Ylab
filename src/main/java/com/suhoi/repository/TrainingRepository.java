package com.suhoi.repository;

import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingRepository {

    /**
     * Для проверки, заносились ли сегодня тренировки с данным типом
     *
     * @param userId
     * @param typeOfTrainingId
     * @param date
     * @return
     */
    Optional<Training> getTrainingForDateById(Long userId, Long typeOfTrainingId, LocalDate date);
    /**
     * Сохранить новую тренировку
     *
     * @param training
     */
    void save(Training training);

    /**
     * Получить тренировки для конкретного пользователя
     *
     * @param id
     * @return
     */
    List<Training> getAllByUserIdOrderByDate(Long id);

    /**
     * Обновить существующую тренировку.
     * Обновление пока что обязательно со всеми полями, которые имеет UpdateTrainingDto
     *
     * @param dto
     */
    void update(UpdateTrainingDto dto);

    /**
     * Удаление по id записи
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Получить тренировки для конкретного пользователя в диапазоне времени
     *
     * @param startDate
     * @param endDate
     * @param id
     * @return
     */
    List<Training> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id);

    /**
     * Для пользователя, чтобы он смог выбрать id, чтобы выбрать запись для удаления
     *
     * @return
     */
    List<Training> findAll();
}
