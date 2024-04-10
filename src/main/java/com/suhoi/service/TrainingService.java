package com.suhoi.service;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    void addTrain(CreateTrainingDto dto);

    List<TrainingDto> getTrains();
    Integer getTrainsBetweenDate(LocalDate startDate, LocalDate endDate);
    // Есть уже getTrains, но метод ниже нужен для того, чтобы пользователь смог выбрать id, по которому он будет удалять запись в БД
    List<Training> getAllTrainsByUserId();

    void deleteById(Long id);

    void update(UpdateTrainingDto dto);
}
