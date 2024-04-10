package com.suhoi.repository;

import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository {
    /**
     * Сохранить новую тренировку
     * INSERT
     *
     * @param training
     */
    void save(Training training);

    /**
     * Получить тренировки для конкретного пользователя
     * SELECT tot.name, t.duration, t.calories, t.advanced, t.date
     * FROM trainings t
     * JOIN type_of_trainings tot
     * ON t.type_of_trainings_id = tot.id
     * WHERE t.user_id = ?
     *
     * @param id
     * @return
     */
    List<TrainingDto> getTrainOrderByDate(Long id);

    /**
     * Получить тренировки всех пользователей (для админа)
     * SELECT tot.name, t.duration, t.calories, t.advanced, t.date
     * FROM trainings t
     * JOIN type_of_trainings tot
     * ON t.type_of_trainings_id = tot.id
     *
     * @return
     */
    List<TrainingDto> getTrainOrderByDate();

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
     */
    void delete(Long id);

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
    List<TrainingDto> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id);

    /**
     * Для пользователя, чтобы он смог выбрать id, чтобы выбрать запись для удаления
     * SELECT * FROM trainings
     *
     * @param id
     * @return
     */
    List<Training> findAll(Long id);
}
