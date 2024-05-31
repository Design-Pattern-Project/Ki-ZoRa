package Implementation;
import OnlineShopping.Item;
public class ConcreteItem implements Item {
    private String name;
    private String type;
    private double price;
    private String imagePath;
    private int quantity;
    

    public ConcreteItem(String name, String type, double price, String imagePath, int quantity) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
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
        // Implement database insertion logic here
    }
}
