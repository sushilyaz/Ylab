package com.suhoi.repository;

import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingRepository {

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
     * UPDATE
     *
     * @param dto
     */
    void update(UpdateTrainingDto dto);

    /**
     * Удаление по id записи
     * DELETE
     *
     * @param id
     * @param userId
     */
    void delete(Long id, Long userId);

    /**
     * Получить тренировки для конкретного пользователя в диапазоне времени
     * SELECT tot.name, t.duration, t.calories, t.advanced, t.date
     * FROM trainings t
     * JOIN type_of_trainings tot
     * ON t.type_of_trainings_id = tot.id
     * WHERE t.user_id =? and t.date BETWEEN ? AND ?
     *
     * @param startDate
     * @param endDate
     * @param id
     * @return
     */
    List<Training> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id);

    /**
     * Для пользователя, чтобы он смог выбрать id, чтобы выбрать запись для удаления
     * SELECT * FROM trainings
     *
     * @param id
     * @return
     */
    List<Training> findAll(Long id);
}
