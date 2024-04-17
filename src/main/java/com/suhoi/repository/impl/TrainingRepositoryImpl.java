package com.suhoi.repository.impl;

import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.*;

public class TrainingRepositoryImpl implements TrainingRepository {

    private static volatile TrainingRepositoryImpl INSTANCE;

    private TrainingRepositoryImpl() {
    }

    public static TrainingRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TrainingRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrainingRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<Training> getTrainingForDateById(Long userId, Long typeOfTrainingId, LocalDate date) {
        return RuntimeDB.getTrainings().stream()
                .filter(training -> training.getDate().equals(date) && training.getUserId().equals(userId) && training.getTypeOfTrainingId().equals(typeOfTrainingId))
                .findFirst();
    }

    @Override
    public void save(Training training) {
        RuntimeDB.addTrain(training);
    }


    @Override
    public List<Training> getAllByUserIdOrderByDate(Long id) {
        return RuntimeDB.getTrainings().stream()
                .filter(o -> o.getUserId().equals(id))
                .sorted(Comparator.comparing(Training::getDate).reversed())
                .toList();
    }

    @Override
    public void update(UpdateTrainingDto dto) {
        List<Training> trainings = RuntimeDB.getTrainings();
        Optional<Training> trainOptional = trainings.stream()
                .filter(train -> train.getId().equals(dto.getId()) && train.getUserId().equals(dto.getUserId()))
                .findFirst();
        if (trainOptional.isPresent()) {
            Training trainingToUpdate = trainOptional.get();

            trainingToUpdate.setCalories(dto.getCalories());
            trainingToUpdate.setAdvanced(dto.getAdvanced());

            for (int i = 0; i < trainings.size(); i++) {
                if (trainings.get(i).getId().equals(dto.getId())) {
                    trainings.set(i, trainingToUpdate);
                    break;
                }
            }
            System.out.println("Data updated success");
        } else {
            System.out.println("Data not found");
            TrainingDailyRunner.menu();
        }
    }

    @Override
    public void delete(Long id, Long userId) {
        List<Training> trainings = RuntimeDB.getTrainings();

        Optional<Training> trainOptional = trainings.stream()
                .filter(train -> train.getId().equals(id) && train.getUserId().equals(userId))
                .findFirst();

        if (trainOptional.isPresent()) {
            Training trainingToDelete = trainOptional.get();
            trainings.remove(trainingToDelete);
            System.out.println("Data removed success");
        } else {
            System.out.println("Train with id " + id + " not found");
            TrainingDailyRunner.menu();
        }

    }

    @Override
    public List<Training> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id) {
        return RuntimeDB.getTrainings().stream()
                .filter(train -> train.getDate().isAfter(startDate) && train.getDate().isBefore(endDate) && train.getUserId().equals(id))
                .toList();
    }

    @Override
    public List<Training> findAll(Long id) {

        return RuntimeDB.getTrainings().stream()
                .filter(o -> Objects.equals(o.getUserId(), id))
                .toList();
    }
}
