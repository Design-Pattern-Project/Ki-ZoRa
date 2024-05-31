package Implementation;

import OnlineShopping.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MathraKira implements Item {
    private String name;
    private double price;
    private String imagePath;
    private int quantity;
    private String type;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "Mentha Kira";
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }


    @Override
    public void addToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kizora", "root", "")) {
            String sql = "INSERT INTO kira (name, type, price, image_path, quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, type);
                statement.setDouble(3, price);
                statement.setString(4, imagePath);
                statement.setInt(5, quantity);
                statement.executeUpdate();
                System.out.println(name + " added to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
   
