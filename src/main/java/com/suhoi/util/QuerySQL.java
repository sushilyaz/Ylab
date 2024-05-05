package com.suhoi.util;


public final class QuerySQL {
    public static final String GET_ALL_SQL = """
            SELECT id, username, action, datetime
            FROM ylab.audits
            """;

    public static final String SAVE_SQL_AUDIT = """
            INSERT INTO ylab.audits (username, action, datetime)
            VALUES (?, ?, ?);
            """;

    public static final String GET_TRAINING_FOR_DATE_SQL = """
            SELECT id, user_id, type_of_training_id, duration, calories, date, advanced
            FROM ylab.trainings
            WHERE user_id = ? AND type_of_training_id = ? AND date = ?;
            """;

    public static final String SAVE_SQL_TRAINING = """
            INSERT INTO ylab.trainings (user_id, type_of_training_id, duration, calories, date, advanced)
            VALUES (?, ?, ?, ?, ?, ?::jsonb);
            """;

    public static final String GET_ALL_BY_USER_ID_SQL = """
            SELECT id, user_id, type_of_training_id, duration, calories, date, advanced
            FROM ylab.trainings
            WHERE user_id = ?
            ORDER BY date DESC;
            """;

    public static final String UPDATE_SQL = """
            UPDATE ylab.trainings
            SET calories = ?,
                advanced = ?::jsonb
            WHERE id = ? AND user_id = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM ylab.trainings
            WHERE id = ?;
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, user_id, type_of_training_id, duration, calories, date, advanced
            FROM ylab.trainings;
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, user_id, type_of_training_id, duration, calories, date, advanced
            FROM ylab.trainings
            WHERE id = ? AND user_id = ?;
            """;

    public static final String GET_TRAININGS_BETWEEN_DATE_SQL = """
            SELECT id, user_id, type_of_training_id, duration, calories, date, advanced
            FROM ylab.trainings
            WHERE user_id = ? AND date BETWEEN ? AND ?;
            """;

    public static final String SAVE_SQL_TOT = """
            INSERT INTO ylab.type_of_trainings (name)
            VALUES (?);
            """;

    public static final String GET_BY_NAME_SQL = """
            SELECT id, name
            FROM ylab.type_of_trainings
            WHERE name = ?;
            """;

    public static final String GET_ALL_SQL_TOT = """
            SELECT id, name
            FROM ylab.type_of_trainings
            """;

    public static final String SAVE_SQL_USER = """
            INSERT INTO ylab.users (username, password, role)
            VALUES (?, ?, ?);
            """;

    public static final String GET_BY_USERNAME_SQL = """
            SELECT id, username, password, role
            FROM ylab.users
            WHERE username = ?;
            """;
}
