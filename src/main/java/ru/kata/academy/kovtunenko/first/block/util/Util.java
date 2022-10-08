package ru.kata.academy.kovtunenko.first.block.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Util {
    private static final String SERVER_ADDRESS = "31.31.199.29";
    private static final String SERVER_PORT = "3306";
    private static final String DATA_BASE = "test";
    private static final String USER = "root";
    private static final String PASSWORD = "reporter2015";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:mysql://%s:%s/%s", SERVER_ADDRESS, SERVER_PORT, DATA_BASE),
                USER, PASSWORD);
    }
}
