package com.serenova.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * DatabaseConnection - Manages database connections using JDBC
 * 
 * This class provides:
 * - Connection pooling (basic implementation)
 * - Centralized database configuration
 * - Exception handling
 * - Connection lifecycle management
 */
public class DatabaseConnection {
    
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    
    // ═══════════════════════════════════════════════════════════
    // DATABASE CONFIGURATION
    // ═══════════════════════════════════════════════════════════
    
    // JDBC URL format: jdbc:mysql://hostname:port/database_name
    private static final String DB_URL = "jdbc:mysql://localhost:3306/serenova_wellness";
    
    // Database credentials
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root"; // CHANGE THIS to your MySQL password
    
    // JDBC Driver class
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Connection parameters for optimization
    private static final String CONNECTION_PARAMS = 
        "?useSSL=false" +                    // Disable SSL for local development
        "&serverTimezone=UTC" +               // Set timezone
        "&allowPublicKeyRetrieval=true" +     // Allow public key retrieval
        "&useUnicode=true" +                  // Use Unicode
        "&characterEncoding=UTF-8";           // Set character encoding
    
    // Full connection URL
    private static final String FULL_URL = DB_URL + CONNECTION_PARAMS;
    
    // Static initializer - loads JDBC driver once
    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName(JDBC_DRIVER);
            logger.info("✓ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.severe("✗ MySQL JDBC Driver not found!");
            logger.severe("Add mysql-connector-j dependency to pom.xml");
            e.printStackTrace();
        }
    }
    
    // ═══════════════════════════════════════════════════════════
    // GET DATABASE CONNECTION
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Get a connection to the database
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(FULL_URL, DB_USER, DB_PASSWORD);
            logger.info("✓ Database connection established");
            return conn;
        } catch (SQLException e) {
            logger.severe("✗ Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test database connection
     * 
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                logger.info("✓ Database connection test SUCCESSFUL");
                logger.info("Database: " + conn.getMetaData().getDatabaseProductName());
                logger.info("Version: " + conn.getMetaData().getDatabaseProductVersion());
                return true;
            }
        } catch (SQLException e) {
            logger.severe("✗ Database connection test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Close database connection safely
     * 
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.info("✓ Database connection closed");
            } catch (SQLException e) {
                logger.warning("Failed to close connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get database configuration info (for debugging)
     */
    public static void printConfiguration() {
        System.out.println("════════════════════════════════════════");
        System.out.println("DATABASE CONFIGURATION");
        System.out.println("════════════════════════════════════════");
        System.out.println("URL: " + DB_URL);
        System.out.println("User: " + DB_USER);
        System.out.println("Driver: " + JDBC_DRIVER);
        System.out.println("════════════════════════════════════════");
    }
    
    // ═══════════════════════════════════════════════════════════
    // MAIN METHOD - FOR TESTING ONLY
    // ═══════════════════════════════════════════════════════════
    
    public static void main(String[] args) {
        System.out.println("Testing Database Connection...\n");
        printConfiguration();
        
        if (testConnection()) {
            System.out.println("\n✓ SUCCESS: Database is properly configured!");
        } else {
            System.out.println("\n✗ FAILED: Check your database configuration");
            System.out.println("\nTroubleshooting:");
            System.out.println("1. Ensure MySQL is running");
            System.out.println("2. Verify DB_USER and DB_PASSWORD are correct");
            System.out.println("3. Check if database 'serenova_wellness' exists");
            System.out.println("4. Ensure MySQL JDBC driver is in classpath");
        }
    }
}