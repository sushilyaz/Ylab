package com.suhoi.service;

import com.suhoi.model.Audit;

import java.util.List;

public interface AuditService {
    void save(Audit audit);
    List<Audit> getAll();
}
