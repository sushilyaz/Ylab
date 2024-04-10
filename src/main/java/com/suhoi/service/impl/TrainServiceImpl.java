package com.suhoi.service.impl;

import com.suhoi.dto.CreateTrainDto;
import com.suhoi.dto.TrainDto;
import com.suhoi.dto.UpdateTrainDto;
import com.suhoi.exception.EmptyListException;
import com.suhoi.exception.TodayTrainException;
import com.suhoi.model.Role;
import com.suhoi.model.Train;
import com.suhoi.repository.TrainRepository;
import com.suhoi.repository.impl.TrainRepositoryImpl;
import com.suhoi.service.TrainService;
import com.suhoi.service.TypeOfTrainService;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.List;

public class TrainServiceImpl implements TrainService {

    private static volatile TrainServiceImpl INSTANCE;

    private final TrainRepository trainRepository;
    private final TypeOfTrainService typeOfTrainService;

    private TrainServiceImpl() {
        this.trainRepository = TrainRepositoryImpl.getInstance();
        this.typeOfTrainService = TypeOfTrainServiceImpl.getInstance();
    }

    public static TrainServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TrainServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrainServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void addTrain(CreateTrainDto dto) {
        Long typeOfTrainId = typeOfTrainService.getType(dto.getTypeOfTrain()).getId();

        Train train = Train.builder()
                .userId(UserUtils.getCurrentUser().getId())
                .typeOfTrainId(typeOfTrainId)
                .duration(dto.getDuration())
                .calories(dto.getCalories())
                .advanced(dto.getAdvanced())
                .date(LocalDate.now())
                .build();

        if (isAddedTrainToday(dto)) {
            throw new TodayTrainException("Training with type " + dto.getTypeOfTrain() + " today already added");
        } else {
            trainRepository.save(train);
        }
    }

    @Override
    public List<TrainDto> getTrains() {
        if (UserUtils.getCurrentUser().getRole().equals(Role.SIMPLE)) {
            return trainRepository.getTrainOrderByDate(UserUtils.getCurrentUser().getId());
        } else {
            // для ADMIN
            return trainRepository.getTrainOrderByDate();
        }

    }

    @Override
    public Integer getTrainsBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<TrainDto> trainBetweenDate = trainRepository.getTrainBetweenDate(startDate, endDate, UserUtils.getCurrentUser().getId());
        if (trainBetweenDate.isEmpty()) {
            throw new EmptyListException("Data not exist for this dates");
        }
        Integer burnedCalories = 0;
        for (TrainDto dto : trainBetweenDate) {
            burnedCalories = burnedCalories + dto.getCalories();
        }
        return burnedCalories;
    }

    @Override
    public List<Train> getAllTrainsByUserId() {
        List<Train> all = trainRepository.findAll(UserUtils.getCurrentUser().getId());
        if (all.isEmpty()) {
            throw new EmptyListException("You have no trainings");
        } else {
            return all;
        }
    }

    @Override
    public void deleteById(Long id) {
        trainRepository.delete(id);
    }

    @Override
    public void update(UpdateTrainDto dto) {
        trainRepository.update(dto);
    }

    private boolean isAddedTrainToday(CreateTrainDto dto) {
        return trainRepository.getTrainOrderByDate(UserUtils.getCurrentUser().getId()).stream()
                .anyMatch(t -> t.getDate().equals(LocalDate.now()) && t.getTypeOfTrain().equals(dto.getTypeOfTrain()));
    }

}
