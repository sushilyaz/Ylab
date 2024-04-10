package com.suhoi.service;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    /**
     * Добавление новой тренировки
     * Если сегодня тренировка с одним типом добавлялась - эксепшен
     *
     * @param dto
     */
    void addTrain(CreateTrainingDto dto);

    /**
     * Вывод всех тренировок отсортированных по дате.
     * Здесь осуществляется проверка по роли.
     * Если роль admin, то возвращаются тренировки всех пользователей. Если роль - simple - только этого пользователя
     *
     * @return
     */
    List<TrainingDto> getTrains();

    /**
     * Подсчет калорий в разрезе времени у конкретного пользователя
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getTrainsBetweenDate(LocalDate startDate, LocalDate endDate);

    /**
     * Возвращаем все тренировки конкретного пользователя. Возвращается вся модель, чтобы можно было выбрать ID, по которому удалять
     * Есть уже getTrains, но метод ниже нужен для того, чтобы пользователь смог выбрать id, по которому он будет удалять запись в БД
     *
     * @return
     */
    List<Training> getAllTrainsByUserId();

    /**
     * Удаление записи о тренировке по ее id
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * Обновление существующей записи
     *
     * @param dto
     */
    void update(UpdateTrainingDto dto);
}
