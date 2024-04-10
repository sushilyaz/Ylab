package com.suhoi.service;

import com.suhoi.model.TypeOfTraining;

import java.util.List;

public interface TypeOfTrainingService {
    /**
     * По названию типа возвращаем сущность, чтобы в дальнейшем можно было взять оттуда id для JOIN
     *
     * @param type
     * @return
     */
    TypeOfTraining getType(String type);


    /**
     * Получение всех доступных типов тренировок для валидации добавления тренировок
     *
     * @return
     */
    List<TypeOfTraining> getAllType();

    /**
     * Сохранение нового типа тренировки
     *
     * @param build
     */
    void save(TypeOfTraining build);
}
