package OnlineShopping;



public interface Item {
    String getName();
    String getType();
    double getPrice();
    String getImagePath();
    int getQuantity();
    void setQuantity(int quantity);
    void setName(String name);        // Add this method
    void setPrice(double price);      // Add this method
    void setImagePath(String imagePath); // Add this method
    void setType(String type); 
    void addToDatabase();
}
