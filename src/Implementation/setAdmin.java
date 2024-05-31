package Implementation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class setAdmin {

	 private static final String DB_URL = "jdbc:mysql://localhost:3306/kizora";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "";

	    public void changeUserRoleToAdmin(String email) {
	        if (isUserInDatabase(email)) {
	            updateUserRoleToAdmin(email);
	        } else {
	            System.out.println("User is not found in database");
	        }
	    }

	    private boolean isUserInDatabase(String email) {
	        String query = "SELECT id FROM users WHERE email = ?";
	        boolean userExists = false;

	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, email);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                userExists = resultSet.next();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return userExists;
	    }


	    private void updateUserRoleToAdmin(String cid) {
	        String updateQuery = "UPDATE users SET role = 'admin' WHERE email = ?";

	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

	            statement.setString(1, cid);
	            int rowsAffected = statement.executeUpdate();
	            
	            if (rowsAffected > 0) {
	                System.out.println("User role has been updated to admin.");
	            } else {
	                System.out.println("Failed to update user role.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	   
	 public static void main(String[] args) {
	        String  email ="admin@gmail.com"; // Example user ID
	        setAdmin setAdmin = new setAdmin();
	        setAdmin.changeUserRoleToAdmin(email);
	    }

}
