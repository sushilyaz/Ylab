package com.suhoi.in.controller.impl;

import com.suhoi.exception.PermissionDeniedException;
import com.suhoi.in.console.TrainingDailyRunner;
import com.suhoi.in.controller.AuditController;
import com.suhoi.model.Audit;
import com.suhoi.model.Role;
import com.suhoi.service.AuditService;
import com.suhoi.service.impl.AuditServiceImpl;
import com.suhoi.util.UserUtils;

import java.time.LocalDateTime;
import java.util.List;

public class AuditControllerImpl implements AuditController {

    private AuditService auditService;

    public AuditControllerImpl(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public List<Audit> getAll() {
        try {
            if (!UserUtils.getCurrentUser().getRole().equals(Role.ADMIN)) {
                throw new PermissionDeniedException("Permission denied");
            }
        } catch (PermissionDeniedException e) {
            System.out.println(e.getMessage());
            TrainingDailyRunner.menu();
        }
        List<Audit> audits = auditService.getAll();
        return audits;
    }
}
