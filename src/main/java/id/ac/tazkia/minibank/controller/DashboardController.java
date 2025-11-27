@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth){

        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
            return "dashboard-admin";

        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CS")))
            return "dashboard-cs";

        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TELLER")))
            return "dashboard-teller";

        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")))
            return "dashboard-manager";

        return "dashboard";
    }
}
