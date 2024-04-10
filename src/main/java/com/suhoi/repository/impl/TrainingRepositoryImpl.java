package com.suhoi.repository.impl;

import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.model.Training;
import com.suhoi.model.TypeOfTraining;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TrainingRepository;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.*;

/**
 * Javadoc в интерфейсе
 */
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
    public void save(Training training) {
        RuntimeDB.addTrain(training);
    }


    @Override
    public List<TrainingDto> getTrainOrderByDate(Long id) {
        // достаем одну сущность
        List<Training> trainings = RuntimeDB.getTrainings().stream()
                .filter(o -> o.getUserId() == id)
                .sorted(Comparator.comparing(Training::getDate).reversed())
                .toList();
        // достаем другую
        List<TypeOfTraining> typeOfTrainings = RuntimeDB.getType_of_training();
        // join
        return join(trainings, typeOfTrainings);
    }

    @Override
    public List<TrainingDto> getTrainOrderByDate() {
        // достаем одну сущность
        List<Training> trainings = RuntimeDB.getTrainings().stream()
                .sorted(Comparator.comparing(Training::getDate).reversed())
                .toList();
        // достаем другую
        List<TypeOfTraining> typeOfTrainings = RuntimeDB.getType_of_training();
        // join
        return join(trainings, typeOfTrainings);
    }

    @Override
    public void update(UpdateTrainingDto dto) {
        List<Training> trainings = RuntimeDB.getTrainings();
        // достаем сущность по id, которую хотим обновить (если она существует)
        Optional<Training> trainOptional = trainings.stream()
                .filter(train -> train.getId().equals(dto.getId()) && train.getUserId() == UserUtils.getCurrentUser().getId())
                .findFirst();

        // Если существует, обновляем
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
            // Обработка случая, если Training с указанным id не найден
            throw new DataNotFoundException("Train with id " + dto.getId() + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        List<Training> trainings = RuntimeDB.getTrainings();

        // Найти нужный по идентификатору Train
        Optional<Training> trainOptional = trainings.stream()
                .filter(train -> train.getId().equals(id) && train.getUserId() == UserUtils.getCurrentUser().getId())
                .findFirst();

        if (trainOptional.isPresent()) {
            Training trainingToDelete = trainOptional.get();
            trainings.remove(trainingToDelete);
            System.out.println("Data removed success");
        } else {
            // Обработка случая, если Train с указанным идентификатором не найден
            throw new DataNotFoundException("Train with id " + id + " not found");
        }
    }

    @Override
    public List<TrainingDto> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id) {
        // Достаем одну сущность
        List<Training> trainings = RuntimeDB.getTrainings();
        // Фильтруем
        List<Training> filteredTrainings = trainings.stream()
                .filter(train -> train.getDate().isAfter(startDate) && train.getDate().isBefore(endDate) && train.getUserId() == id)
                .toList();
        // Достаем другую сущность
        List<TypeOfTraining> typeOfTrainings = RuntimeDB.getType_of_training();
        // join и возвращаем
        return join(filteredTrainings, typeOfTrainings);
    }

    @Override
    public List<Training> findAll(Long id) {

        return RuntimeDB.getTrainings().stream()
                .filter(o -> Objects.equals(o.getUserId(), id))
                .toList();
    }

    /**
     * Метод для Join двух сущностей: Training и TypeOfTraining
     * Выглядит пока что мудрено, но когда появится postgres, все хорошо решится через JOIN ON
     */
    private List<TrainingDto> join(List<Training> trainings, List<TypeOfTraining> typeOfTrainings) {
        List<TrainingDto> trainingDtos = new ArrayList<>();

        for (Training training : trainings) {
            Optional<TypeOfTraining> typeOfTrainOptional = typeOfTrainings.stream()
                    .filter(t -> t.getId().equals(training.getTypeOfTrainingId()))
                    .findFirst();

            if (typeOfTrainOptional.isPresent()) {
                TypeOfTraining typeOfTraining = typeOfTrainOptional.get();

                TrainingDto trainingDto = TrainingDto.builder()
                        .typeOfTrain(typeOfTraining.getName())
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
