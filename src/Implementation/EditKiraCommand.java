package Implementation;

import OnlineShopping.KiraAdminService;
import OnlineShopping.Command;

public class EditKiraCommand implements Command {
    private KiraAdminService adminService;
    private String name;
    private String newName;
    private double newPrice;
    private String newImagePath;
    private int newQuantity;

    public EditKiraCommand(KiraAdminService adminService, String name, String newName, double newPrice, String newImagePath, int newQuantity) {
        this.adminService = adminService;
        this.name = name;
        this.newName = newName;
        this.newPrice = newPrice;
        this.newImagePath = newImagePath;
        this.newQuantity = newQuantity;
    }

    @Override
    public void execute() {
        adminService.editKira(name, newName, newPrice, newImagePath, newQuantity);
    }
}