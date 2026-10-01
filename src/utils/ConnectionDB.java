package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/db_cinema_booking";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
