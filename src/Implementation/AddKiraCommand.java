package Implementation;

import OnlineShopping.KiraAdminService;
import OnlineShopping.KiraFactory;
import OnlineShopping.Command;

public class AddKiraCommand implements Command {
    private KiraAdminService adminService;
    private String type;
    private KiraFactory factory;
    private String name;
    private double price;
    private String imagePath;
    private int quantity;

    public AddKiraCommand(KiraAdminService adminService, String type, KiraFactory factory, String name, double price, String imagePath, int quantity) {
        this.adminService = adminService;
        this.type = type;
        this.factory = factory;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        adminService.addKira(type, factory, name, price, imagePath, quantity);
    }
}