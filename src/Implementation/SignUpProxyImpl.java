package Implementation;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Import necessary classes from the framework package
// import framework.SignUpProxy;
// import framework.User;
// import framework.ValidationHandler;
// import util.AppError;

import OnlineShopping.SignUpProxy;
import OnlineShopping.User;
import OnlineShopping.ValidationHandler;
import OnlineShopping.AppError;


// Database 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


class FullNameHandler implements ValidationHandler {
    private ValidationHandler nextHandler;

    @Override
    public void setNextHandler(ValidationHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleValidationRequest(User user) {
        String fullName = user.getFullName();
        try {
            if (fullName.isEmpty()) {
                throw new AppError("Full name cannot be empty.", 400);
            }

            if (nextHandler != null) {
                nextHandler.handleValidationRequest(user);
            }
        } catch (AppError e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class EmailHandler implements ValidationHandler {
    private ValidationHandler nextHandler;

    @Override
    public void setNextHandler(ValidationHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleValidationRequest(User user) {
        String email = user.getEmail();
        try {
            String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                throw new AppError("Email format is incorrect.", 400);
            }

            if (nextHandler != null) {
                nextHandler.handleValidationRequest(user);
            }
        } catch (AppError e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class PhoneNumberHandler implements ValidationHandler {
    private ValidationHandler nextHandler;

    @Override
    public void setNextHandler(ValidationHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleValidationRequest(User user) {
        String phoneNumber = user.getPhoneNumber();
        try {
            String phonePattern = "\\d{8}"; 
            Pattern pattern = Pattern.compile(phonePattern);
            Matcher matcher = pattern.matcher(phoneNumber);

            if (!matcher.matches()) {
                throw new AppError("Phone number format is incorrect.", 400);
            }

            if (nextHandler != null) {
                nextHandler.handleValidationRequest(user);
            }
        } catch (AppError e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class HashedPasswordHandler implements ValidationHandler {
    private ValidationHandler nextHandler;

    @Override
    public void setNextHandler(ValidationHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handleValidationRequest(User user) {
        String hashedPassword = user.getHashedPassword();
        try {
            if (hashedPassword.isEmpty()) {
                throw new AppError("Password cannot be empty.", 400);
            }

            // Example of a simple password complexity check
            if (hashedPassword.length() < 8) {
                throw new AppError("Password must be at least 8 characters long.", 400);
            }

            if (!hashedPassword.matches(".*[A-Z].*")) {
                throw new AppError("Password must contain at least one uppercase letter.", 400);
            }

            if (!hashedPassword.matches(".*[a-z].*")) {
                throw new AppError("Password must contain at least one lowercase letter.", 400);
            }

            if (!hashedPassword.matches(".*\\d.*")) {
                throw new AppError("Password must contain at least one digit.", 400);
            }

            if (!hashedPassword.matches(".*[!@#$%^&*].*")) {
                throw new AppError("Password must contain at least one special character (e.g., !@#$%^&*).", 400);
            }

            if (nextHandler != null) {
                nextHandler.handleValidationRequest(user);
            }
        } catch (AppError e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Real subject class
class SignUpRealSubject implements SignUpProxy {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kizora";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void signUp(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "INSERT INTO users (fullName, email, address, phoneNumber, hashedPassword) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getFullName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhoneNumber());
                statement.setString(5, user.getHashedPassword());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "User signed up successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to sign up user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error during sign-up: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Proxy class
public class SignUpProxyImpl implements SignUpProxy {
    private final SignUpRealSubject realSubject;
    private final ValidationHandler validationHandler;

    public SignUpProxyImpl() {
        this.realSubject = new SignUpRealSubject();
        this.validationHandler = new FullNameHandler();

        ValidationHandler email = new EmailHandler();
        ValidationHandler phone = new PhoneNumberHandler();
        ValidationHandler password = new HashedPasswordHandler();

        validationHandler.setNextHandler(email);
        email.setNextHandler(phone);
        phone.setNextHandler(password);
    }

    @Override
    public void signUp(User user) {
        try {
            validationHandler.handleValidationRequest(user);
            realSubject.signUp(user);
        } catch (AppError e) {
            String errorMessage = "Error during sign-up: " + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
}

