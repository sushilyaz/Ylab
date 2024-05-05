package com.suhoi.service.impl;

import com.suhoi.model.Audit;
import com.suhoi.repository.AuditRepository;
import com.suhoi.service.AuditService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void save(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public List<Audit> getAll() {
        return auditRepository.getAll();
    }
}
