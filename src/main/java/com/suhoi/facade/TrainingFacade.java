package com.suhoi.facade;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingFacade {

    void addNewTraining(CreateTrainingDto createTrainingDto);

    List<TrainingDto> getTrainingsForUser();

    List<Training> getAllTrainingsForUserWithId();

    Integer getCaloriesBetweenDate(RangeDto dto);

    void edit(UpdateTrainingDto dto);

    void delete (Long id);
}
