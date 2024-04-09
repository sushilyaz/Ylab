package com.suhoi.repository;

import com.suhoi.dto.TrainDto;
import com.suhoi.dto.UpdateTrainDto;
import com.suhoi.model.Train;

import java.time.LocalDate;
import java.util.List;

public interface TrainRepository {
    void save(Train train);

    List<TrainDto> getTrainOrderByDate(Long id);

    void update(UpdateTrainDto dto);

    void delete(Long id);

    List<TrainDto> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id);
}
