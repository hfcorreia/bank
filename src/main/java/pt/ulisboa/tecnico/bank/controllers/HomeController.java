package pt.ulisboa.tecnico.bank.controllers;


import org.springframework.stereotype.Controller;
import java.security.Principal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal ) {

        String name = principal.getName();
        model.addAttribute("username", name);
        return "hello";

    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLogin(HttpServletRequest request, ModelMap model) {
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String submitLogin(HttpServletRequest request, ModelMap model) {
        return "login";

    }

    @RequestMapping(value="/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        return "login";

    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {

        return "login";

    }
}
