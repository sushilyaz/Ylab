package com.suhoi.facade;

import com.suhoi.model.TypeOfTraining;

/**
 * Паттерн фасад
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
