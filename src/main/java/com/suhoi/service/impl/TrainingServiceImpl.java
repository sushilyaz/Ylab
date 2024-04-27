package com.suhoi.service.impl;

import com.suhoi.annotation.Auditable;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.model.Role;
import com.suhoi.model.Training;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.service.TrainingService;
import com.suhoi.util.UserUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    @Auditable
    @Override
    public void addTrainingIfNotExist(Training training) {

        Long userId = training.getUserId();
        Long typeOfTrainingId = training.getTypeOfTrainingId();
        LocalDate date = training.getDate();
        if (trainingRepository.getTrainingForDateById(userId, typeOfTrainingId, date).isPresent()) {
            throw new DataNotFoundException("Training with id " + typeOfTrainingId + " already exist");
        }
        trainingRepository.save(training);
    }

    @Auditable
    @Override
    public List<Training> getAllForUser() {
        List<Training> rs = new ArrayList<>();
        if (UserUtils.getCurrentUser().getRole().equals(Role.SIMPLE)) {
            rs = trainingRepository.getAllByUserIdOrderByDate(UserUtils.getCurrentUser().getId());
        } else {
            rs = trainingRepository.findAll();
        }
        if (rs.isEmpty()) {
            throw new DataNotFoundException("You have no training data");
        }
        return rs;
    }

    @Auditable
    @Override
    public Integer getTrainsBetweenDate(RangeDto dto) {
        List<Training> trainBetweenDate = trainingRepository.getTrainBetweenDate(dto.getStartDate(), dto.getEndDate(), UserUtils.getCurrentUser().getId());
        if (trainBetweenDate.isEmpty()) {
            throw new DataNotFoundException("Training between this dates is empty");
        }
        Integer burnedCalories = 0;
        for (Training training : trainBetweenDate) {
            burnedCalories = burnedCalories + training.getCalories();
        }
        return burnedCalories;
    }

    // поправил метод для оптимизации (если у пользователя будет много тренировок, то в старой версии могли возникнуть проблемы с перфомансом)
    @Auditable
    @Override
    public void deleteById(Long id) {
        if (trainingRepository.findById(id, UserUtils.getCurrentUser().getId()).isEmpty()) {
            throw new DataNotFoundException("Training with id " + id + " not found");
        }
        trainingRepository.delete(id);
    }

    // поправил метод для оптимизации (если у пользователя будет много тренировок, то в старой версии могли возникнуть проблемы с перфомансом)
    @Auditable
    @Override
    public void update(UpdateTrainingDto dto) {
        if (dto.getId() == null || dto.getUserId() == null || dto.getCalories() == null || dto.getAdvanced() == null) {
            throw new NoValidDataException("Fill out all fields");
        }
        if (trainingRepository.findById(dto.getId(), dto.getUserId()).isEmpty()) {
            throw new DataNotFoundException("Training with id " + dto.getId() + " not found");
        }
        dto.setUserId(UserUtils.getCurrentUser().getId());
        trainingRepository.update(dto);
    }
}
