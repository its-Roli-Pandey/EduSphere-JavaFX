package com.edusphere.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:edusphere.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(URL);
        initializeDatabase(conn); 
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL"
                + ");";

        String insertAdminSQL = "INSERT OR IGNORE INTO users (id, username, password, role) "
                + "VALUES (1, 'admin', 'admin123', 'Admin');";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            stmt.execute(insertAdminSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
