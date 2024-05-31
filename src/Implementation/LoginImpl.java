package Implementation;

import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;
// Database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoginImpl {
    private static LoginImpl instance;
    private Map<Integer, String> loggedInUsers;
    private Connection connection;
    private String role;
    private int userId;

    private LoginImpl() {
        loggedInUsers = new HashMap<>();
        // Initialize database connection
        connectToDatabase();
    }

    public static synchronized LoginImpl getInstance() {
        if (instance == null) {
            instance = new LoginImpl();
        }
        return instance;
    }

    private void connectToDatabase() {
        try {
            // Connect to the database (replace the connection parameters with your actual database details)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kizora", "root", "");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error connecting to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean login(String email, String hashedPassword) {
        try {
            // Simulated database lookup for user credentials
            String sql = "SELECT * FROM users WHERE email = ? AND hashedPassword = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, hashedPassword);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        this.userId = resultSet.getInt("id");
                        this.role = resultSet.getString("role");
                        loggedInUsers.put(userId, generateSessionId());
                        JOptionPane.showMessageDialog(null, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing login query: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error executing login query", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void logout(int userId) {
        loggedInUsers.remove(userId);
    }

    public boolean isLoggedIn(int userId) {
        return loggedInUsers.containsKey(userId);
    }

    private String generateSessionId() {
        // Simulated method to generate a session ID
        return "session-" + System.nanoTime();
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }
}
