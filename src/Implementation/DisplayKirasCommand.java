package Implementation;

import OnlineShopping.KiraAdminService;
import OnlineShopping.Command;

public class DisplayKirasCommand implements Command {
    private KiraAdminService adminService;

    public DisplayKirasCommand(KiraAdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute() {
        adminService.displayKiras();
    }
}