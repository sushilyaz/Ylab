package com.suhoi.in.controller;

public interface AuditController {
    /**
     * Сохранение действий пользователя
     *
     * @param action
     */
    void save(String action);

    /**
     * Запрос на получение действий пользователя (для админа)
     */
    void getAll();
}
