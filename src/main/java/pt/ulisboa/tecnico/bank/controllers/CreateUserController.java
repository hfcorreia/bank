package pt.ulisboa.tecnico.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pt.ulisboa.tecnico.bank.services.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: SONY
 * Date: 12/12/13
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@SessionAttributes
public class CreateUserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createuser", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String createUserShowPage() {
        return "createuser";
    }

    @RequestMapping(value = "createuser/submit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String createUserSubmitForm(@RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       @RequestParam("passwordConfirm") String passwordConfirm,
                                       @RequestParam("email") String email,
                                       @RequestParam("role") String role, ModelMap modelMap) {

        if(!password.equals(passwordConfirm)) {
            modelMap.addAttribute("error", true);
            modelMap.addAttribute("errorMsg", "Passwords do not match.");
            return "createuser";

        } else {

        if(role.equals("user"))
            userService.createNormalUser(username, password, "salt", "1024");
        else if(role.equals("admin"))
            userService.createAdminUser(username, password, "salt", "1024");

        return "redirect:/createaccount";
        }
    }
}
