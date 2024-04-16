package com.suhoi.service.impl;

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
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final AuditService auditService;

    public TrainingServiceImpl(TrainingRepository trainingRepository, AuditService auditService) {
        this.trainingRepository = trainingRepository;
        this.auditService = auditService;
    }
    //    private static volatile TrainingServiceImpl INSTANCE;
//
//    // для тестов, т.к. иначе замокать не получится, т.к. классы синглтон с приватным конструктором
//    @Setter
//    private TrainingRepository trainingRepository;
//    private final AuditService auditService;
//
//    private TrainingServiceImpl() {
//        this.trainingRepository = TrainingRepositoryImpl.getInstance();
//        this.auditService = AuditServiceImpl.getInstance();
//    }
//
//    public static TrainingServiceImpl getInstance() {
//        if (INSTANCE == null) {
//            synchronized (TrainingServiceImpl.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new TrainingServiceImpl();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    @Override
    public void addTrainingIfNotExist(Training training) {
        Long userId = training.getUserId();
        Long typeOfTrainingId = training.getTypeOfTrainingId();
        LocalDate date = training.getDate();
        if (trainingRepository.getTrainingForDateById(userId, typeOfTrainingId, date).isPresent()) {
            auditService.save("TrainingService.addTrainingIfNotExist failed");
            System.out.println("User " + userId + " has already training with id " + typeOfTrainingId);
            TrainingDailyRunner.menu();
        }
        trainingRepository.save(training);
        auditService.save("TrainingService.addTrainingIfNotExist success");
    }

    @Override
    public List<Training> getAllForUser() {
        List<Training> rs = trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId());
        if (rs.isEmpty()) {
            auditService.save("TrainingService.getAllForUser failed");
            System.out.println("Training not found");
            TrainingDailyRunner.menu();
        }
        auditService.save("TrainingService.getAllForUser success");
        return rs;
    }

    @Override
    public Integer getTrainsBetweenDate(RangeDto dto) {
        List<Training> trainBetweenDate = trainingRepository.getTrainBetweenDate(dto.getStartDate(), dto.getEndDate(), UserUtils.getCurrentUser().getId());
        if (trainBetweenDate.isEmpty()) {
            auditService.save("TrainingService.getTrainsBetweenDate failed");
            System.out.println("Training not found");
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
        if (all.isEmpty()) {
            auditService.save("TrainingService.getAllTrainsByUserId failed");
            System.out.println("Training not found");
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
