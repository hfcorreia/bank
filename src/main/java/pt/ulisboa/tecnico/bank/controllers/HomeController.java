package pt.ulisboa.tecnico.bank.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"param.error"})
public class HomeController {

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal ) {

        String name = principal.getName();
        model.addAttribute("username", name);
        return "hello";

    }

    @RequestMapping(value={"","/index"}, method = RequestMethod.GET)
    public String index(ModelMap mode){
    	return "login";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLogin(HttpServletRequest request, ModelMap model) {
        if(request.getParameter("param.error") != null){
            request.getSession().setAttribute("param.error", request.getParameter("param.error"));
        }
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String submitLogin(HttpServletRequest request, ModelMap model) {
        if(request.getParameter("param.error") != null){
            request.getSession().setAttribute("param.error", request.getParameter("param.error"));
        }
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {

        return "login";

    }
}
