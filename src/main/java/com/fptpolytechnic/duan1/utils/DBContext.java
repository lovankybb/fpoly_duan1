package com.fptpolytechnic.duan1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    private static final String HOST_NAME = "localhost";
    private static final String PORT = "1433";

    public static Connection getConnection() {
        Connection conn = null;

        String databaseName = AppConfig.getInstance().getProperty("app.datasource.database");
        String username = AppConfig.getInstance().getProperty("app.datasource.username");
        String password = AppConfig.getInstance().getProperty("app.datasource.password");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true", HOST_NAME, PORT, databaseName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERROR: Connect to db failed");
            e.printStackTrace();
        }
        return  conn;
    }
}
