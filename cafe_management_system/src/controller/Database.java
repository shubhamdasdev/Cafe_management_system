package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "fp510";
    private static final String PASSWORD = "510";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void createTablesIfNotExist() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Create cms_users table
            String createUsersTable = """
                    CREATE TABLE IF NOT EXISTS cms_users (
                        user_id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        type VARCHAR(20) NOT NULL,
                        age INT,
                        address VARCHAR(255),
                        shift VARCHAR(50),
                        dress_code VARCHAR(50),
                        mobile_number VARCHAR(15),
                        ssn VARCHAR(20),
                        gender VARCHAR(10),
                        salary DECIMAL(10, 2),
                        first_name VARCHAR(50),
                        last_name VARCHAR(50)
                    );
                    """;
            stmt.execute(createUsersTable);

            // Create cms_orders table
            String createOrdersTable = """
                    CREATE TABLE IF NOT EXISTS cms_orders (
                        order_id INT AUTO_INCREMENT PRIMARY KEY,
                        table_number INT NOT NULL,
                        food_id INT,
                        drink_id INT,
                        quantity INT NOT NULL,
                        order_details TEXT,
                        status VARCHAR(50) DEFAULT 'in-progress',
                        FOREIGN KEY (food_id) REFERENCES cms_food_items(food_id),
                        FOREIGN KEY (drink_id) REFERENCES cms_drinks(drink_id)
                    );
                    """;
            stmt.execute(createOrdersTable);

            // Create cms_food_items table
            String createFoodItemsTable = """
                    CREATE TABLE IF NOT EXISTS cms_food_items (
                        food_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL,
                        quantity INT NOT NULL,
                        food_type VARCHAR(50),
                        spice_level VARCHAR(50)
                    );
                    """;
            stmt.execute(createFoodItemsTable);

            // Create cms_drinks table
            String createDrinksTable = """
                    CREATE TABLE IF NOT EXISTS cms_drinks (
                        drink_id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        price DECIMAL(10, 2) NOT NULL,
                        quantity INT NOT NULL,
                        type VARCHAR(50)
                    );
                    """;
            stmt.execute(createDrinksTable);

            System.out.println("Tables created or already exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
