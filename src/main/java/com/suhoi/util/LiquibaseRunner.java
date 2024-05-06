package com.suhoi.util;

import com.suhoi.config.YamlReader;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
@RequiredArgsConstructor
public class LiquibaseRunner {
    private final YamlReader yamlReader;
    private final ConnectionPool connectionPool;

    /**
     * Запускаем миграции из changelog, указанный в application.yml
     * Перед стартом миграций, необходимо создать дефолтную схему, чтобы туда сохранились служебные таблицы
     */
    public void runLiquibaseMigration() {
        String CREATE_DEFAULT_SCHEMA = "CREATE SCHEMA IF NOT EXISTS " + yamlReader.getLiquibaseSchema();
        try (Connection connection = connectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEFAULT_SCHEMA)) {
            preparedStatement.executeUpdate();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(yamlReader.getLiquibaseSchema());

            Liquibase liquibase = new Liquibase(yamlReader.getLiquibaseChangeLog(), new ClassLoaderResourceAccessor(), database);
            liquibase.update();

            System.out.println("Migration is completed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
