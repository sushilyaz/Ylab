package com.suhoi.repository.impl;

import com.suhoi.model.Audit;
import com.suhoi.repository.AuditRepository;
import com.suhoi.util.ConnectionPool;
import com.suhoi.util.QuerySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditRepositoryImpl implements AuditRepository {

    private static final String SAVE_SQL = QuerySQL.SAVE_SQL_AUDIT;

    private static final String GET_ALL_SQL = QuerySQL.GET_ALL_SQL;


    @Override
    public void save(Audit audit) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, audit.getUsername());
            preparedStatement.setString(2, audit.getAction());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(audit.getDateTime()));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                audit.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Audit> getAll() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Audit> audits = new ArrayList<>();
            while (resultSet.next()) {
                Audit audit = buildAudit(resultSet);
                audits.add(audit);
            }
            return audits;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Audit buildAudit(ResultSet resultSet) throws SQLException {
        return Audit.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .action(resultSet.getString("action"))
                .dateTime(resultSet.getTimestamp("datetime").toLocalDateTime())
                .build();
    }
}
