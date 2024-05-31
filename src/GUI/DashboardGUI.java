package GUI;

import OnlineShopping.Command;
import OnlineShopping.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Implementation.ConcreteItem;
import Implementation.KiraAdminServiceImpl;
import Implementation.MathraKiraFactory;
import Implementation.RemoveKiraCommand;
import Implementation.KishutharaKiraFactory;
import OnlineShopping.KiraAdminService;
import OnlineShopping.KiraFactory;

public class DashboardGUI extends JFrame {
    private KiraAdminService adminService = new KiraAdminServiceImpl();

    private JTextField nameField;
    private JTextField priceField;
    private JTextField imagePathField;
    private JTextField quantityField;
    private JComboBox<String> typeComboBox;  // Dropdown for selecting Kira type
    private JTable kiraTable;
    private DefaultTableModel tableModel;

    public DashboardGUI() {
        setTitle("Admin Dashboard - Kira Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the top panel for adding new Kira
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        JLabel imagePathLabel = new JLabel("Image Path:");
        imagePathField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();
        JLabel typeLabel = new JLabel("Type:");
        typeComboBox = new JComboBox<>(new String[]{"MathraKira", "KishutharaKira"});
        JButton addButton = new JButton("Add Kira");
        addButton.addActionListener(new AddButtonListener());
        JButton updateButton = new JButton("Update Kira");
        updateButton.addActionListener(new UpdateButtonListener());

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel());  // Filler for the grid layout
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(new JLabel());  // Filler for the grid layout
        inputPanel.add(imagePathLabel);
        inputPanel.add(imagePathField);
        inputPanel.add(new JLabel());  // Filler for the grid layout
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel());  // Filler for the grid layout
        inputPanel.add(typeLabel);
        inputPanel.add(typeComboBox);
        inputPanel.add(new JLabel());  // Filler for the grid layout
        inputPanel.add(addButton);
        inputPanel.add(updateButton);  // Add the update button to the panel
        inputPanel.add(new JLabel());  // Filler for the grid layout

        // Create the table to display Kiras
        String[] columnNames = {"Name", "Type", "Price", "Image Path", "Quantity", "Edit", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0);
        kiraTable = new JTable(tableModel);
        kiraTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        kiraTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
        kiraTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        kiraTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane tableScrollPane = new JScrollPane(kiraTable);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load initial data
        loadKiras();

        // Display the frame
        setVisible(true);
    }

    private void loadKiras() {
        tableModel.setRowCount(0);  // Clear existing rows
        List<Item> items = getItemsFromDatabase();
        for (Item item : items) {
            Object[] row = new Object[]{
                    item.getName(),
                    item.getType(),
                    item.getPrice(),
                    item.getImagePath(),
                    item.getQuantity(),
                    "Edit",
                    "Delete"
            };
            tableModel.addRow(row);
        }
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

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String imagePath = imagePathField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            String selectedType = (String) typeComboBox.getSelectedItem();

            KiraFactory factory = getFactoryByType(selectedType);
            if (factory != null) {
                adminService.addKira(selectedType, factory, name, price, imagePath, quantity);
                JOptionPane.showMessageDialog(null, "Kira added successfully!");
                loadKiras();  // Refresh the table
            } else {
                JOptionPane.showMessageDialog(null, "Error: No factory found for selected type!");
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String imagePath = imagePathField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            String selectedType = (String) typeComboBox.getSelectedItem();

            String originalName = (String) nameField.getClientProperty("originalName");
            if (originalName == null) {
                JOptionPane.showMessageDialog(null, "No item selected for update!");
                return;
            }

            KiraFactory factory = getFactoryByType(selectedType);
            if (factory != null) {
                adminService.editKira(originalName, name, price, imagePath, quantity);
                JOptionPane.showMessageDialog(null, "Kira updated successfully!");
                nameField.putClientProperty("originalName", null); // Reset the original name property
                loadKiras();  // Refresh the table
            } else {
                JOptionPane.showMessageDialog(null, "Error: No factory found for selected type!");
            }
        }
    }


    private KiraFactory getFactoryByType(String type) {
        switch (type) {
            case "MathraKira":
                return new MathraKiraFactory();
            case "KishutharaKira":
                return new KishutharaKiraFactory();
            default:
                return null;
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private String label;
        private boolean isPushed;
        private int editingRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            JButton button = new JButton(label);
            button.addActionListener(e -> fireEditingStopped());
            isPushed = true;
            editingRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if ("Edit".equals(label)) {
                    editKira();
                } else if ("Delete".equals(label)) {
                    deleteKira();
                }
            }
            isPushed = false;
            return label;
        }

        private void editKira() {
        int selectedRow = kiraTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String type = (String) tableModel.getValueAt(selectedRow, 1);
            double price = (Double) tableModel.getValueAt(selectedRow, 2);
            String imagePath = (String) tableModel.getValueAt(selectedRow, 3);
            int quantity = (Integer) tableModel.getValueAt(selectedRow, 4);

            // Populate the fields with these values for editing
            nameField.setText(name);
            priceField.setText(String.valueOf(price));
            imagePathField.setText(imagePath);
            quantityField.setText(String.valueOf(quantity));
            typeComboBox.setSelectedItem(type);

            // Store the original name to identify the record to update in the database
            nameField.putClientProperty("originalName", name);
        }
    }

        private void deleteKira() {
            int selectedRow = editingRow;
            if (selectedRow >= 0) {
                String name = (String) tableModel.getValueAt(selectedRow, 0);
                Command removeCommand = new RemoveKiraCommand(adminService, name);
                removeCommand.execute();
                loadKiras();  // Refresh the table
            }
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardGUI());
    }
}
