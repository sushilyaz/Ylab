package com.suhoi.repository.impl;

import com.suhoi.dto.TrainDto;
import com.suhoi.dto.UpdateTrainDto;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.model.Train;
import com.suhoi.model.TypeOfTrain;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TrainRepository;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.*;

public class TrainRepositoryImpl implements TrainRepository {

    private static volatile TrainRepositoryImpl INSTANCE;

    private TrainRepositoryImpl() {
    }

    public static TrainRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (TrainRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrainRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Train train) {
        RuntimeDB.addTrain(train);
    }


    @Override
    public List<TrainDto> getTrainOrderByDate(Long id) {
        List<Train> trains = RuntimeDB.getTrains().stream()
                .filter(o -> o.getUserId() == id)
                .sorted(Comparator.comparing(Train::getDate).reversed())
                .toList();
        List<TypeOfTrain> typeOfTrains = RuntimeDB.getType_of_trains();

        return join(trains, typeOfTrains);
    }

    @Override
    public List<TrainDto> getTrainOrderByDate() {
        List<Train> trains = RuntimeDB.getTrains().stream()
                .sorted(Comparator.comparing(Train::getDate).reversed())
                .toList();
        List<TypeOfTrain> typeOfTrains = RuntimeDB.getType_of_trains();

        return join(trains, typeOfTrains);
    }

    @Override
    public void update(UpdateTrainDto dto) {
        List<Train> trains = RuntimeDB.getTrains();

        Optional<Train> trainOptional = trains.stream()
                .filter(train -> train.getId().equals(dto.getId()) && train.getUserId() == UserUtils.getCurrentUser().getId())
                .findFirst();


        if (trainOptional.isPresent()) {
            Train trainToUpdate = trainOptional.get();

            trainToUpdate.setCalories(dto.getCalories());
            trainToUpdate.setAdvanced(dto.getAdvanced());

            for (int i = 0; i < trains.size(); i++) {
                if (trains.get(i).getId().equals(dto.getId())) {
                    trains.set(i, trainToUpdate);
                    break;
                }
            }
            System.out.println("Data updated success");
        } else {
            // Обработка случая, если Train с указанным id не найден
            throw new DataNotFoundException("Train with id " + dto.getId() + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        List<Train> trains = RuntimeDB.getTrains();

        // Найти нужный по идентификатору Train
        Optional<Train> trainOptional = trains.stream()
                .filter(train -> train.getId().equals(id) && train.getUserId() == UserUtils.getCurrentUser().getId())
                .findFirst();

        // Если Train с таким идентификатором найден
        if (trainOptional.isPresent()) {
            Train trainToDelete = trainOptional.get();
            // Удалить найденный Train из списка
            trains.remove(trainToDelete);
            System.out.println("Data removed success");
        } else {
            // Обработка случая, если Train с указанным идентификатором не найден
            throw new DataNotFoundException("Train with id " + id + "not found");
        }
    }

    @Override
    public List<TrainDto> getTrainBetweenDate(LocalDate startDate, LocalDate endDate, Long id) {
        List<Train> trains = RuntimeDB.getTrains();
        List<Train> filteredTrains = trains.stream()
                .filter(train -> train.getDate().isAfter(startDate) && train.getDate().isBefore(endDate) && train.getUserId() == id)
                .toList();

        List<TypeOfTrain> typeOfTrains = RuntimeDB.getType_of_trains();

        return join(filteredTrains, typeOfTrains);
    }

    @Override
    public List<Train> findAll(Long id) {

        return RuntimeDB.getTrains().stream()
                .filter(o-> Objects.equals(o.getUserId(), id))
                .toList();
    }

    /*
    Выглядит пока что мудрено, но когда появится postgres, все хорошо решится через JOIN ON
     */
    private List<TrainDto> join(List<Train> trains, List<TypeOfTrain> typeOfTrains) {
        List<TrainDto> trainDtos = new ArrayList<>();

        for (Train train : trains) {
            Optional<TypeOfTrain> typeOfTrainOptional = typeOfTrains.stream()
                    .filter(t -> t.getId().equals(train.getTypeOfTrainId()))
                    .findFirst();

            if (typeOfTrainOptional.isPresent()) {
                TypeOfTrain typeOfTrain = typeOfTrainOptional.get();

                TrainDto trainDto = TrainDto.builder()
                        .typeOfTrain(typeOfTrain.getName())
                        .duration(train.getDuration())
                        .calories(train.getCalories())
                        .advanced(train.getAdvanced())
                        .date(train.getDate())
                        .build();

                trainDtos.add(trainDto);
            }
        }
        return trainDtos;
    }
}
