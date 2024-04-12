package com.suhoi.service;

import com.suhoi.model.Audit;

import java.util.List;

public interface AuditService {
    /**
     * Сервис для сохранения аудита
     *
     * @param action
     */
    void save(String action);

    /**
     * Сервис для предоставления администратору всех действий пользователей
     * @return
     */
    List<Audit> getAll();
}
