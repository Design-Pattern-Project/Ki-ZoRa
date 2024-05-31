package GUI;

import javax.swing.*;
import java.awt.*;

public class Landing {
    private JFrame frame;
    private JLabel welcomeLabel;

    public Landing() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("BSB");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold the welcome text
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE); // Optional: Set background color
        welcomePanel.setLayout(new GridBagLayout()); // Set layout to GridBagLayout for centering

        // Create a JLabel with the welcome text
        welcomeLabel = new JLabel("Welcome to BSB");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
        welcomeLabel.setForeground(Color.BLACK); // Set text color to black

        // Add the welcome label to the welcome panel with GridBagLayout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomePanel.add(welcomeLabel, gbc);

        // Add the welcome panel to the frame
        frame.add(welcomePanel, BorderLayout.CENTER);
    }

    public void displayGUI() {
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of Landing and display the GUI
        Landing landing = new Landing();
        landing.displayGUI();
    }
}

