package com.suhoi.service.impl;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.exception.EmptyListException;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.repository.impl.TrainingRepositoryImpl;
import com.suhoi.service.TrainingService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.List;

public class TrainingServiceImpl implements TrainingService {

    private static volatile TrainingServiceImpl INSTANCE;

    private final TrainingRepository trainingRepository;
    private final TypeOfTrainingService typeOfTrainingService;

    private TrainingServiceImpl() {
        this.trainingRepository = TrainingRepositoryImpl.getInstance();
        this.typeOfTrainingService = TypeOfTrainingServiceImpl.getInstance();
    }

    public static TrainingServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TrainingServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrainingServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void addTrain(CreateTrainingDto dto) {
        Long typeOfTrainId = typeOfTrainingService.getType(dto.getTypeOfTrain()).getId();

        Training training = Training.builder()
                .userId(UserUtils.getCurrentUser().getId())
                .typeOfTrainingId(typeOfTrainId)
                .duration(dto.getDuration())
                .calories(dto.getCalories())
                .advanced(dto.getAdvanced())
                .date(LocalDate.now())
                .build();

        if (isAddedTrainToday(dto)) {
            throw new DataNotFoundException("Training with type " + dto.getTypeOfTrain() + " today already added");
        } else {
            trainingRepository.save(training);
        }
    }

    @Override
    public List<TrainingDto> getTrains() {
        if (UserUtils.getCurrentUser().getRole().equals(Role.SIMPLE)) {
            return trainingRepository.getTrainOrderByDate(UserUtils.getCurrentUser().getId());
        } else {
            // для ADMIN
            return trainingRepository.getTrainOrderByDate();
        }

    }

    @Override
    public Integer getTrainsBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<TrainingDto> trainBetweenDate = trainingRepository.getTrainBetweenDate(startDate, endDate, UserUtils.getCurrentUser().getId());
        if (trainBetweenDate.isEmpty()) {
            throw new EmptyListException("Data not exist for this dates");
        }
        Integer burnedCalories = 0;
        for (TrainingDto dto : trainBetweenDate) {
            burnedCalories = burnedCalories + dto.getCalories();
        }
        return burnedCalories;
    }

    @Override
    public List<Training> getAllTrainsByUserId() {
        List<Training> all = trainingRepository.findAll(UserUtils.getCurrentUser().getId());
        if (all.isEmpty()) {
            throw new EmptyListException("You have no trainings");
        } else {
            return all;
        }
    }

    @Override
    public void deleteById(Long id) {
        trainingRepository.delete(id);
    }

    @Override
    public void update(UpdateTrainingDto dto) {
        trainingRepository.update(dto);
    }

    private boolean isAddedTrainToday(CreateTrainingDto dto) {
        return trainingRepository.getTrainOrderByDate(UserUtils.getCurrentUser().getId()).stream()
                .anyMatch(t -> t.getDate().equals(LocalDate.now()) && t.getTypeOfTrain().equals(dto.getTypeOfTrain()));
    }

}
