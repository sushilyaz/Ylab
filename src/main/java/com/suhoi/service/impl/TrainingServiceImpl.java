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
import com.suhoi.repository.impl.TypeOfTrainingRepositoryImpl;
import com.suhoi.repository.impl.UserRepositoryImpl;
import com.suhoi.service.TrainingService;
import com.suhoi.service.TypeOfTrainingService;
import com.suhoi.util.UserUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * Javadoc методов в интерфейсе
 */
public class TrainingServiceImpl implements TrainingService {

    private static volatile TrainingServiceImpl INSTANCE;

    private final TrainingRepository trainingRepository;
    private final TypeOfTrainingService typeOfTrainingService;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
        this.typeOfTrainingService = new TypeOfTrainingServiceImpl(TypeOfTrainingRepositoryImpl.getInstance());
    }


    @Override
    public void addTrain(CreateTrainingDto dto) {
        // Достаем тип тренировки, для проверки на занесение данных за сегодня
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
        // Проверяем, какая роль. Если роль у текущего пользователя simple - запрос на получение только его тренировок
        if (UserUtils.getCurrentUser().getRole().equals(Role.SIMPLE)) {
            return trainingRepository.getTrainOrderByDate(UserUtils.getCurrentUser().getId());
        } else {
            // Если admin - тренировки всех пользователей
            return trainingRepository.getTrainOrderByDate();
        }
    }

    @Override
    public Integer getTrainsBetweenDate(LocalDate startDate, LocalDate endDate) {
        // запрос на все тренировки
        List<TrainingDto> trainBetweenDate = trainingRepository.getTrainBetweenDate(startDate, endDate, UserUtils.getCurrentUser().getId());
        // Если список пуст, значит в заданном диапазоне не было - бросаем исключение
        if (trainBetweenDate.isEmpty()) {
            throw new EmptyListException("Data not exist for this dates");
        }
        // Подсчет калорий
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

    /**
     * Метод, проверяющий добавляли ли сегодня тренировку с этим же типом
     * @param dto
     * @return
     */
    private boolean isAddedTrainToday(CreateTrainingDto dto) {
        return trainingRepository.getTrainOrderByDate(UserUtils.getCurrentUser().getId()).stream()
                .anyMatch(t -> t.getDate().equals(LocalDate.now()) && t.getTypeOfTrain().equals(dto.getTypeOfTrain()));
    }

}
