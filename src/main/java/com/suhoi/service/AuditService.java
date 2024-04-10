package com.suhoi.service;

import com.suhoi.model.Audit;

import java.util.List;

public interface AuditService {
    /**
     * Сервис для сохранения аудита
     * @param audit
     */
    void save(Audit audit);

    /**
     * Сервис для предоставления администратору всех действий пользователей
     * @return
     */
    List<Audit> getAll();
}
