package com.suhoi.service;

import com.suhoi.model.TypeOfTraining;

import java.util.List;

public interface TypeOfTrainingService {
    /**
     * По названию типа возвращаем сущность, чтобы в дальнейшем можно было взять оттуда id для JOIN
     * @return
     */
    TypeOfTraining getTypeByName(String name);


    /**
     * Получение всех доступных типов тренировок для валидации добавления тренировок
     *
     * @return
     */
    List<TypeOfTraining> getAllType();

    /**
     * Сохранение нового типа тренировки
     *
     * @param name
     */
    void save(String name);
}
