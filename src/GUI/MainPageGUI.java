package GUI;

import OnlineShopping.Item;

import javax.swing.*;
import java.awt.*;


import Implementation.ConcreteItem;
import Implementation.LoginImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainPageGUI extends JFrame {
    private List<String> cart;
    private DefaultListModel<String> cartListModel;
    private LoginImpl loginImpl;
    private JPanel productPanel;

    public MainPageGUI() {
        setTitle("E-commerce Main Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loginImpl = LoginImpl.getInstance();

        cart = new ArrayList<>();
        cartListModel = new DefaultListModel<>();

        initializeUI();}
        private void initializeUI() {
        productPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        refreshProductList(); // Load products initially

        // Create cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        JLabel cartLabel = new JLabel("Shopping Cart:");
        JList<String> cartList = new JList<>(cartListModel);
        JScrollPane cartScrollPane = new JScrollPane(cartList);
        cartPanel.add(cartLabel, BorderLayout.NORTH);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Create logout and refresh buttons
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        JButton refreshButton = new JButton("Refresh Products");
        refreshButton.addActionListener(e -> refreshProductList());

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(productPanel, BorderLayout.CENTER);
        mainPanel.add(cartPanel, BorderLayout.EAST);

        // Panel for buttons
        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.add(logoutButton);
        southPanel.add(refreshButton);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        getContentPane().add(mainPanel);
    }

    private void refreshProductList() {
        List<Item> items = getItemsFromDatabase();
        productPanel.removeAll();
        for (Item item : items) {
            JPanel productItemPanel = createProductPanel(item);
            productPanel.add(productItemPanel);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductPanel(Item item) {
        JPanel productItemPanel = new JPanel(new BorderLayout(10, 10));
        
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/" + item.getImagePath()));
        JLabel productImageLabel = new JLabel(imageIcon);
        JLabel productNameLabel = new JLabel(item.getName());
        JLabel productTypeLabel = new JLabel("Type: " + item.getType());
        JLabel productPriceLabel = new JLabel("Price: $" + item.getPrice());
        JLabel productQuantityLabel = new JLabel("Quantity: " + item.getQuantity());
        JButton addToCartButton = new JButton("Add to Cart");

        addToCartButton.addActionListener(e -> {
            if (item.getQuantity() > 0) {
                addToCart(item);
                item.setQuantity(item.getQuantity() - 1);
                productQuantityLabel.setText("Quantity: " + item.getQuantity());
                updateProductQuantityInDatabase(item);
            } else {
                JOptionPane.showMessageDialog(this, "Out of stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel detailsPanel = new JPanel(new GridLayout(4, 1));
        detailsPanel.add(productNameLabel);
        detailsPanel.add(productTypeLabel);
        detailsPanel.add(productPriceLabel);
        detailsPanel.add(productQuantityLabel);

        productItemPanel.add(productImageLabel, BorderLayout.WEST);
        productItemPanel.add(detailsPanel, BorderLayout.CENTER);
        productItemPanel.add(addToCartButton, BorderLayout.EAST);

        return productItemPanel;
    }

    private List<Item> getItemsFromDatabase() {
        List<Item> items = new ArrayList<>();
        String DB_URL = "jdbc:mysql://localhost:3306/kizora";
        String DB_USERNAME = "root";
        String DB_PASSWORD = "";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "SELECT name, type, price, image_path, quantity FROM kira";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    double price = resultSet.getDouble("price");
                    String imagePath = resultSet.getString("image_path");
                    int quantity = resultSet.getInt("quantity");
                    items.add(new ConcreteItem(name, type, price, imagePath, quantity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch items from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return items;
    }

    private void addToCart(Item item) {
        String itemDetails = item.getName() + " - $" + item.getPrice();
        cart.add(itemDetails);
        cartListModel.addElement(itemDetails);
        JOptionPane.showMessageDialog(this, item.getName() + " added to cart.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    

    private void updateProductQuantityInDatabase(Item item) {
        String DB_URL = "jdbc:mysql://localhost:3306/kizora";
        String DB_USERNAME = "root";
        String DB_PASSWORD = "";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "UPDATE kira SET quantity = ?, image_path = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, item.getQuantity());
                statement.setString(2, "/src/image/" + item.getImagePath());
                statement.setString(3, item.getName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        int userId = loginImpl.getUserId();
        loginImpl.logout(userId);
        JOptionPane.showMessageDialog(this, "Logout successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
             public void run() {
                LoginImpl loginImpl = LoginImpl.getInstance();
                // Check if the user is logged in
                int userId = loginImpl.getUserId();
                if (loginImpl.isLoggedIn(userId)) {
                    MainPageGUI mainPageGUI = new MainPageGUI();
                    mainPageGUI.setVisible(true);
                } else {
                    // Show login GUI or perform other actions as needed
                    LoginGUI loginGUI = new LoginGUI();
                    loginGUI.setVisible(true);
                }
            }
        });
    }
}



