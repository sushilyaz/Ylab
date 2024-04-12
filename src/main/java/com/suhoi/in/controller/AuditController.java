package com.suhoi.in.controller;

import com.suhoi.model.Audit;

import java.util.List;

/**
 * Контроллер аудита
 */
public interface AuditController {
    /**
     * Запрос на получение действий пользователя (для админа)
     *
     * @return
     */
    List<Audit> getAll();
}
