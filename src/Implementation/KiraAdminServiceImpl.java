package Implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OnlineShopping.Item;
import OnlineShopping.KiraFactory;
import OnlineShopping.KiraAdminService;

public class KiraAdminServiceImpl implements KiraAdminService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kizora";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void addKira(String type, KiraFactory factory, String name, double price, String imagePath, int quantity) {
        Item kira = factory.createKira();
        kira.setName(name);
        kira.setPrice(price);
        kira.setImagePath(imagePath);
        kira.setQuantity(quantity);
        kira.setType(type);
        kira.addToDatabase();
        
        System.out.println(name + " added to database.");
    }

    @Override
    public void displayKiras() {
    List<Item> items = getKirasFromDatabase();
    for (Item kira : items) {
        System.out.println(kira.getType() + ": " + kira.getName() + " - $" + kira.getPrice() + " - Quantity: " + kira.getQuantity());
    }
}

@Override
public void editKira(String name, String newName, double newPrice, String newImagePath, int newQuantity) {
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        String sql = "UPDATE kira SET name = ?, price = ?, image_path = ?, quantity = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setDouble(2, newPrice);
            preparedStatement.setString(3, newImagePath);
            preparedStatement.setInt(4, newQuantity);
            preparedStatement.setString(5, name);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(name + " updated.");
            } else {
                System.out.println(name + " not found.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the error appropriately in your production code
    }
}

    @Override
    public void removeKira(String kiraName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "DELETE FROM kira WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, kiraName);
                preparedStatement.executeUpdate();
                System.out.println(kiraName + " removed from database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the error appropriately in your production code
        }
    }

     @Override
    public List<Item> getKirasFromDatabase() {
        List<Item> items = new ArrayList<>();

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
            // Handle the error appropriately in your production code
        }
        return items;
    }
}
