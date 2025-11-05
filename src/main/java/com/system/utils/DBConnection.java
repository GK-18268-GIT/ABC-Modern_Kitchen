package com.system.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class DBConnection {
	private static volatile DBConnection instance;
    private Connection connection;

    // Database credentials
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    private DBConnection() {
    	
    	loadConfig();
    	
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error initializing database connection", e);
        }
    }
    
    private void loadConfig() {
    	try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
    		if(input == null) {
    			throw new RuntimeErrorException(null, "config.properties file not found in classpath");
    		}
    		
    		Properties props = new Properties();
    		props.load(input);
    		
    		DB_URL = props.getProperty("db.url");
    		DB_USER = props.getProperty("db.user");
    		DB_PASSWORD = props.getProperty("db.password");
    		
    	} catch(Exception e) {
    		throw new RuntimeException("Failed to load database configuration", e);
    	}
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Reconnecting to the database...");
                this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reconnecting to the database", e);
        }
        return connection;
    }
}