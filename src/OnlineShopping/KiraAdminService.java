package OnlineShopping;

import java.util.List;

public interface KiraAdminService {
    void addKira(String type, KiraFactory factory, String name, double price, String imagePath, int quantity);
    void displayKiras();
    void editKira(String name, String newName, double newPrice, String newImagePath, int newQuantity);
    void removeKira(String name);
    List<Item> getKirasFromDatabase();  // Add this method declaration
}

