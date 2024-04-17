package com.suhoi.service.impl;

import com.suhoi.dto.RangeDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.exception.EmptyListException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.repository.impl.TrainingRepositoryImpl;
import com.suhoi.service.AuditService;
import com.suhoi.service.TrainingService;
import com.suhoi.util.UserUtils;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
            System.out.println("Training with id " + typeOfTrainingId + " already exist");
            TrainingDailyRunner.menu();
        }
        trainingRepository.save(training);
        auditService.save("TrainingService.addTrainingIfNotExist success");
    }

    @Override
    public List<Training> getAllForUser() {
        List<Training> rs = new ArrayList<>();
        if (UserUtils.getCurrentUser().getRole().equals(Role.SIMPLE)) {
            rs = trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId());
        } else {
            rs = trainingRepository.findAll();
        }
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

//    @Override
//    public List<Training> getAllTrainsByUserId() {
//        List<Training> all = trainingRepository.findAll(UserUtils.getCurrentUser().getId());
//        if (all.isEmpty()) {
//            auditService.save("TrainingService.getAllTrainsByUserId failed");
//            System.out.println("Training not found");
//            TrainingDailyRunner.menu();
//            return null;
//        }
//        auditService.save("TrainingService.getAllTrainsByUserId success");
//        return all;
//    }

    @Override
    public void deleteById(Long id) {
        List<Training> list = trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId());
        boolean checkId = list.stream().anyMatch(training -> training.getId().equals(id));
        if (!checkId) {
            auditService.save("TrainingService.deleteById failed");
            System.out.println("Training not found");
            TrainingDailyRunner.menu();
        }
        auditService.save("TrainingService.deleteById success");
        trainingRepository.delete(id);
    }

    @Override
    public void update(UpdateTrainingDto dto) {
        auditService.save("called TrainingService.update");
        dto.setUserId(UserUtils.getCurrentUser().getId());
        trainingRepository.update(dto);
    }
}
