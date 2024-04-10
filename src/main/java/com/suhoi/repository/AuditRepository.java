package com.suhoi.repository;

import com.suhoi.model.Audit;

import java.util.List;

public interface AuditRepository {
    /**
     * Сохраняем действие пользователей
     * INSERT
     *
     * @param audit
     */
    void save(Audit audit);

    /**
     * Достаем все действия пользователя (для админа)
     * SELECT
     *
     * @return
     */
    List<Audit> getAll();
}
