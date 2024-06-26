package com.suhoi.facade;

import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.model.Training;

import java.util.List;

/**
 * Паттерн фасад
 */
public interface TrainingFacade {

    /**
     * Собираю сущность из DTO
     * @param createTrainingDto
     */
    Training addNewTraining(CreateTrainingDto createTrainingDto);

    /**
     * Собираю DTO из сущностей
     * @return
     */
    List<TrainingDto> getTrainingsForUser();

    /**
     * Здесь можно было каскад не использовать,
     * но если в будущем (представим) логика получения тренировок усложнится,
     * то фасад будет очень кстати
     * @return
     */
    List<Training> getAllTrainingsForUserWithId();

    /**
     * Здесь можно было каскад не использовать,
     * но если в будущем (представим) логика подсчета калорий усложнится,
     * то фасад будет очень кстати
     * @return
     */
    Integer getCaloriesBetweenDate(RangeDto dto);

    /**
     * Здесь можно было каскад не использовать,
     * но если в будущем (представим) логика обновления сущности усложнится,
     * то фасад будет очень кстати
     * @return
     */
    void edit(UpdateTrainingDto dto);

    /**
     * Здесь можно было каскад не использовать,
     * но если в будущем (представим) логика удаления сущности усложнится,
     * то фасад будет очень кстати
     * @return
     */
    void delete (Long id);
}
