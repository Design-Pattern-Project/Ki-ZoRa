package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Implementation.LoginImpl;

public class LoginGUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField; // Changed to JPasswordField
    private LoginImpl loginImpl;

    public LoginGUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loginImpl = LoginImpl.getInstance();

        // Create labels and text fields
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        JLabel signUpLinkLabel = new JLabel("Don't have an account? Sign Up");
        signUpLinkLabel.setForeground(Color.BLUE); // Set text color to blue for a clickable link
        signUpLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        signUpLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openSignUpGUI();
            }
        });

        // Create panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpLinkLabel);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        getContentPane().add(mainPanel);
    }

    private void login() {
        // Get input values from text fields
        String email = emailField.getText();
        String password = new String(passwordField.getPassword()); // Convert char array to string

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (loginImpl.login(email, password)) {
                String role = loginImpl.getRole();
                JOptionPane.showMessageDialog(this, "Login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (role != null) {
                    if (role.equals("user")) {
                        // Open main page GUI
                        MainPageGUI mainPageGUI = new MainPageGUI();
                        mainPageGUI.setVisible(true);
                        dispose(); // Close the login GUI
                    } else if (role.equals("admin")) {
                        // Open dashboard page GUI
                        DashboardGUI dashboardPageGUI = new DashboardGUI();
                        dashboardPageGUI.setVisible(true);
                        dispose(); // Close the login GUI
                    }
                }
            } else {
                // Login failed, display error message
                JOptionPane.showMessageDialog(this, "Login failed. Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred during login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openSignUpGUI() {
        // Open the sign-up GUI
        SignUpGUI signUpGUI = new SignUpGUI();
        signUpGUI.setVisible(true);
        // Close the current login GUI
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI().setVisible(true);
            }
        });
    }
}
