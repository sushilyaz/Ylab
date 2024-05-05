package com.suhoi.util;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LiquibaseRunner {
    // Как будет spring boot, добавлю preliquibase
    private static final String CREATE_DEFAULT_SCHEMA = "CREATE SCHEMA IF NOT EXISTS services";

    /**
     * Запускаем миграции из changelog, указанный в application.yml
     * Перед стартом миграций, необходимо создать дефолтную схему, чтобы туда сохранились служебные таблицы
     */
    public static void runLiquibaseMigration() {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEFAULT_SCHEMA)) {
            preparedStatement.executeUpdate();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName("services");

            Liquibase liquibase = new Liquibase(PropertiesUtil.get("liquibase.changelog"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();

            System.out.println("Migration is completed successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
