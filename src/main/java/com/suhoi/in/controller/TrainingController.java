package com.suhoi.in.controller;

public interface TrainingController {
    /**
     * Добавление новой тренировки
     * POST
     */
    void addTrain();

    /**
     * Запрос на получение всех тренировок пользователя[лей]
     * GET
     */
    void getAllSortedTrainings();

    /**
     * Запрос на получение сженных калорий в срезе времени.
     * Границы диапазона НЕ ВКЛЮЧИТЕЛЬНЫ!!!
     * GET
     */
    void getCaloriesBetweenDates();

    /**
     * Обновление существующей записи
     * PUT
     */
    void edit();

    /**
     * Удаление существующей записи
     * DELETE
     */
    void delete();

}
