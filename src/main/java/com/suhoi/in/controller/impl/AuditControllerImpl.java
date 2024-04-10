package com.suhoi.in.controller.impl;

import com.suhoi.exception.PermissionDeniedException;
import com.suhoi.in.TrainingDailyRunner;
import com.suhoi.in.controller.AuditController;
import com.suhoi.model.Audit;
import com.suhoi.model.Role;
import com.suhoi.service.AuditService;
import com.suhoi.service.impl.AuditServiceImpl;
import com.suhoi.util.UserUtils;

import java.time.LocalDateTime;
import java.util.List;

public class AuditControllerImpl implements AuditController {

    private final AuditService auditService;

    public AuditControllerImpl() {
        this.auditService = AuditServiceImpl.getInstance();
    }

    @Override
    public void save(String action) {
        Audit build = Audit.builder()
                .username(UserUtils.getCurrentUser().getUsername())
                .action(action)
                .dateTime(LocalDateTime.now())
                .build();

        auditService.save(build);
    }

    @Override
    public void getAll() {
        // Проверка на роль. Операция доступна только админу
        if (UserUtils.getCurrentUser().getRole().equals(Role.ADMIN)) {
            List<Audit> audits = auditService.getAll();
            for (Audit audit : audits) {
                System.out.println(audit);
            }
            TrainingDailyRunner.menu();
        } else {
            throw new PermissionDeniedException("Permission denied");
        }

    }
}
