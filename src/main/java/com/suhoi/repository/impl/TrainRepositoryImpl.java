package com.suhoi.repository.impl;

import com.suhoi.dto.TrainDto;
import com.suhoi.dto.UpdateTrainDto;
import com.suhoi.model.Train;
import com.suhoi.model.TypeOfTrain;
import com.suhoi.repository.RuntimeDB;
import com.suhoi.repository.TrainRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .sorted(Comparator.comparing(Train::getDate))
                .toList();
        List<TypeOfTrain> typeOfTrains = RuntimeDB.getType_of_trains();

        return join(trains, typeOfTrains);
    }

    @Override
    public void update(UpdateTrainDto dto) {

    }

    @Override
    public void delete(Long id) {

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
