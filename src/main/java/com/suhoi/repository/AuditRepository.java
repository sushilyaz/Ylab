package com.suhoi.repository;

import com.suhoi.model.Audit;

import java.util.List;

public interface AuditRepository {
    void save(Audit audit);
    List<Audit> getAll();
}
