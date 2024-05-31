package GUI;

import javax.swing.*;
import Implementation.SignUpProxyImpl;
import OnlineShopping.User;
import OnlineShopping.AppError;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpGUI extends JFrame {
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField hashedPasswordField;
    private SignUpProxyImpl signUpProxy;

    public SignUpGUI() {
        setTitle("User Sign Up");
        setSize(400, 300); // Adjusted height to accommodate new fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        signUpProxy = new SignUpProxyImpl();

        // Create labels and text fields
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(20);

        JLabel hashedPasswordLabel = new JLabel("Password:");
        phoneNumberField = new JTextField(20);

        // Create sign up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });

        // Create login link label
        JLabel loginLinkLabel = new JLabel("Have an accountt? LogIn");
        loginLinkLabel.setForeground(Color.BLUE); // Set text color to blue for a clickable link
        loginLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        loginLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openLoginGUI();
            }
        });

        // Create nested panels with margins
        JPanel fullNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        fullNamePanel.add(fullNameLabel);
        fullNamePanel.add(fullNameField);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        addressPanel.add(addressLabel);
        addressPanel.add(addressField);

        JPanel phoneNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumberField);

        
        JPanel hashedPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        hashedPasswordField = new JTextField(20); // Initialize hashedPasswordField
        hashedPasswordPanel.add(hashedPasswordLabel);
        hashedPasswordPanel.add(hashedPasswordField); // Add hashedPasswordField to hashedPasswordPanel




        // Create panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin around the input panel
        inputPanel.add(fullNamePanel);
        inputPanel.add(emailPanel);
        inputPanel.add(addressPanel);
        inputPanel.add(phoneNumberPanel);
        inputPanel.add(hashedPasswordPanel);

        // Create panel for button and login link
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        buttonPanel.add(signUpButton);
        buttonPanel.add(loginLinkLabel);

        // Add input panel and button panel to main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        getContentPane().add(mainPanel);
    }

    private void signUp() {
        // Retrieve input values from text fields
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String hashedPassword = hashedPasswordField.getText().trim();


        if (fullName.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || hashedPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create a new User object with the provided information
            User user = new User(fullName, email, address, phoneNumber, hashedPassword);

            // Invoke signUp method of SignUpProxyImpl
            signUpProxy.signUp(user);

            // Clear the input fields after successful signup
            fullNameField.setText("");
            emailField.setText("");
            addressField.setText("");
            phoneNumberField.setText("");
            hashedPasswordField.setText("");

            JOptionPane.showMessageDialog(this, "User signed up successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (AppError ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openLoginGUI() {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpGUI().setVisible(true);
            }
        });
    }
}
