package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("properties")) {
            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database.");
        } catch (IOException e) {
            System.err.println("Could not load properties: " + e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("Could not connect to database: " + e.getMessage());
            System.exit(1);
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
