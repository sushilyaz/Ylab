package com.suhoi.service;

import com.suhoi.dto.CreateTrainDto;
import com.suhoi.dto.TrainDto;
import com.suhoi.dto.UpdateTrainDto;
import com.suhoi.model.Train;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {
    void addTrain(CreateTrainDto dto);

    List<TrainDto> getTrains();
    Integer getTrainsBetweenDate(LocalDate startDate, LocalDate endDate);

    List<Train> getAllTrainsByUserId();

    void deleteById(Long id);

    void update(UpdateTrainDto dto);
}
