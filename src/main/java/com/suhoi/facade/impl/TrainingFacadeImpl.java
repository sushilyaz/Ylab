package com.suhoi.facade.impl;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.in.controller.impl.TrainingControllerImpl;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.service.TrainingService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.service.impl.TrainingServiceImpl;
import com.suhoi.service.impl.TypeOfTrainingServiceImpl;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;
    private final TypeOfTrainingService typeOfTrainingService;

    public TrainingFacadeImpl(TrainingService trainingService, TypeOfTrainingService typeOfTrainingService) {
        this.trainingService = trainingService;
        this.typeOfTrainingService = typeOfTrainingService;
    }

    @Override
    public void addNewTraining(CreateTrainingDto createTrainingDto) {
        TypeOfTraining typeByName = typeOfTrainingService.getTypeByName(createTrainingDto.getTypeOfTrain());
        Training build = Training.builder()
                .typeOfTrainingId(typeByName.getId())
                .userId(UserUtils.getCurrentUser().getId())
                .calories(createTrainingDto.getCalories())
                .duration(createTrainingDto.getDuration())
                .advanced(createTrainingDto.getAdvanced())
                .date(LocalDate.now())
                .build();
        trainingService.addTrainingIfNotExist(build);
    }

    @Override
    public List<TrainingDto> getTrainingsForUser() {
        List<Training> allForUser = trainingService.getAllForUser();
        List<TypeOfTraining> allType = typeOfTrainingService.getAllType();
        return join(allForUser, allType);
    }

    @Override
    public List<Training> getAllTrainingsForUserWithId() {
        return trainingService.getAllForUser();
    }

    @Override
    public Integer getCaloriesBetweenDate(RangeDto dto) {
        return trainingService.getTrainsBetweenDate(dto);
    }

    @Override
    public void edit(UpdateTrainingDto dto) {
        trainingService.update(dto);
    }

    @Override
    public void delete(Long id) {
        trainingService.deleteById(id);
    }

    private List<TrainingDto> join(List<Training> trainings, List<TypeOfTraining> typeOfTrainings) {
        List<TrainingDto> trainingDtos = new ArrayList<>();
        Map<Long, String> typeMap = typeOfTrainings.stream()
                .collect(Collectors.toMap(TypeOfTraining::getId, TypeOfTraining::getName));

        for (Training training : trainings) {
            String typeName = typeMap.get(training.getTypeOfTrainingId());
            if (typeName != null) {
                TrainingDto trainingDto = TrainingDto.builder()
                        .typeOfTrain(typeName)
                        .duration(training.getDuration())
                        .calories(training.getCalories())
                        .advanced(training.getAdvanced())
                        .date(training.getDate())
                        .build();
                trainingDtos.add(trainingDto);
            }
        }
        return trainingDtos;
    }
}
