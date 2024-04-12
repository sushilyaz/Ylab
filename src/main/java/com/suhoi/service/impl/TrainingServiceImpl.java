package com.suhoi.service.impl;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.exception.EmptyListException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.model.Training;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.repository.impl.TrainingRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.TrainingService;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * Javadoc методов в интерфейсе
 */
public class TrainingServiceImpl implements TrainingService {

    private static volatile TrainingServiceImpl INSTANCE;

    private final TrainingRepository trainingRepository;
    private final AuditService auditService;

    private TrainingServiceImpl() {
        this.trainingRepository = TrainingRepositoryImpl.getInstance();
        this.auditService = AuditServiceImpl.getInstance();
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
    public void addTrainingIfNotExist(Training training) {
        Long userId = training.getId();
        Long typeOfTrainingId = training.getTypeOfTrainingId();
        LocalDate date = training.getDate();
        try {
            trainingRepository.getTrainingForDateById(userId, typeOfTrainingId, date)
                    .ifPresent(t -> {
                        throw new DataNotFoundException("Training with id " + training.getId() + " already exists");
                    });
        } catch (DataNotFoundException e) {
            auditService.save("TrainingService.addTrainingIfNotExist failed");
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
        }
        trainingRepository.save(training);
        auditService.save("TrainingService.addTrainingIfNotExist success");
    }

    @Override
    public List<Training> getAllForUser() {
        List<Training> rs = trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId());
        try {
            if (rs.isEmpty()) throw new EmptyListException("There are no trainings");
        } catch (EmptyListException e) {
            auditService.save("TrainingService.getAllForUser failed");
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
        }
        auditService.save("TrainingService.getAllForUser success");
        return rs;
    }

    @Override
    public Integer getTrainsBetweenDate(RangeDto dto) {
        List<Training> trainBetweenDate = trainingRepository.getTrainBetweenDate(dto.getStartDate(), dto.getEndDate(), UserUtils.getCurrentUser().getId());
        try {
            if (trainBetweenDate.isEmpty()) throw new EmptyListException("Data not exist for this dates");
        } catch (EmptyListException e) {
            auditService.save("TrainingService.getTrainsBetweenDate failed");
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
        }
        Integer burnedCalories = 0;
        for (Training training : trainBetweenDate) {
            burnedCalories = burnedCalories + training.getCalories();
        }
        auditService.save("TrainingService.getTrainsBetweenDate success");
        return burnedCalories;
    }

    @Override
    public List<Training> getAllTrainsByUserId() {
        List<Training> all = trainingRepository.findAll(UserUtils.getCurrentUser().getId());
        try {
            if (all.isEmpty()) throw new EmptyListException("You have no trainings");
        } catch (EmptyListException e) {
            auditService.save("TrainingService.getAllTrainsByUserId failed");
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
            return null;
        }
        auditService.save("TrainingService.getAllTrainsByUserId success");
        return all;
    }

    @Override
    public void deleteById(Long id) {
        auditService.save("called TrainingService.deleteById");
        trainingRepository.delete(id, UserUtils.getCurrentUser().getId());
    }

    @Override
    public void update(UpdateTrainingDto dto) {
        auditService.save("called TrainingService.update");
        dto.setUserId(UserUtils.getCurrentUser().getId());
        trainingRepository.update(dto);
    }
}
