package Implementation;

import OnlineShopping.Command;
import OnlineShopping.KiraAdminService;

public class RemoveKiraCommand implements Command {
    private KiraAdminService adminService;
    private String kiraName;

    public RemoveKiraCommand(KiraAdminService adminService, String kiraName) {
        this.adminService = adminService;
        this.kiraName = kiraName;
    }

    @Override
    public void execute() {
        adminService.removeKira(kiraName);
    }
}
