package com.suhoi.service;

import com.suhoi.dto.RangeDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.util.List;

public interface TrainingService {
    /**
     * Добавление новой тренировки
     * Если сегодня тренировка с одним типом добавлялась - эксепшен
     */
    void addTrainingIfNotExist(Training training);

    /**
     * Вывод всех тренировок отсортированных по дате.
     * Здесь осуществляется проверка по роли.
     * Если роль admin, то возвращаются тренировки всех пользователей. Если роль - simple - только этого пользователя
     *
     * @return
     */
    List<Training> getAllForUser();

    /**
     * Подсчет калорий в разрезе времени у конкретного пользователя
     *
     * @return
     */
    Integer getTrainsBetweenDate(RangeDto dto);

    /**
     * Удаление записи о тренировке по ее id
     */
    void deleteById(Long id);

    /**
     * Обновление существующей записи
     *
     * @param dto
     */
    void update(UpdateTrainingDto dto);
}
