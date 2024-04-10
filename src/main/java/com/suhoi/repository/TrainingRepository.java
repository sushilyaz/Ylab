package com.suhoi.repository;

import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository {
    void save(Training training);

    // метод для обычного пользователя
    List<TrainingDto> getTrainOrderByDate(Long id);
    // перегруженный метод для админа
    List<TrainingDto> getTrainOrderByDate();

    void update(UpdateTrainingDto dto);

    void delete(Long id);

    List<TrainingDto> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id);

    List<Training> findAll(Long id);
}
