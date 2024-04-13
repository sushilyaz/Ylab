package com.suhoi.facade;

import com.suhoi.model.TypeOfTraining;

/**
 * Ментор Алексей намекнул, что вместо того, чтобы возвращать DTO из DAO можно использовать паттерн "Фасад"
 * Находится между контроллером и сервисом.
 * Тут я собираю DTOшки из сущностей и наоборот
 */
public interface TypeOfTrainingFacade {
    /**
     * Здесь можно было каскад не использовать,
     * но если в будущем (представим) логика добавления нового типа тренировок усложнится,
     * то фасад будет очень кстати
     * @return
     */
    void addNewTypeOfTraining(String name);
}
