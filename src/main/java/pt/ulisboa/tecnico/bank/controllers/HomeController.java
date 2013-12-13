package pt.ulisboa.tecnico.bank.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pt.ulisboa.tecnico.bank.domain.Role;
import pt.ulisboa.tecnico.bank.domain.Roles;

@Controller
@SessionAttributes({"param.error"})
public class HomeController {

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal, UsernamePasswordAuthenticationToken currentUser  ) {
    	if(currentUser != null) {
    		Role admin = new Role();
    		admin.setName(Roles.ADMINISTRATOR.name());
    		Role user = new Role();
    		user.setName(Roles.USER.name());
            if(currentUser.getAuthorities().contains( admin ) ) {
                return "redirect:/listusers";
            } else if(currentUser.getAuthorities().contains(user)) {
                return "redirect:/listaccounts";
            }
        }
    	return "hello";
    }

    @RequestMapping(value={"","/index"}, method = RequestMethod.GET)
    public String index(ModelMap mode){
    	return "login";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLogin(HttpServletRequest request, ModelMap model, UsernamePasswordAuthenticationToken currentUser) {
        if(request.getParameter("param.error") != null){
            request.getSession().setAttribute("param.error", request.getParameter("param.error"));
        }
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String submitLogin(HttpServletRequest request, ModelMap model, UsernamePasswordAuthenticationToken currentUser) {
        if(request.getParameter("param.error") != null){
            request.getSession().setAttribute("param.error", request.getParameter("param.error"));
        }
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET )
    public String logout(ModelMap model, UsernamePasswordAuthenticationToken currentUser) {
        return "login";
    }    
    
    @RequestMapping(value={"/404*"}, method=RequestMethod.GET)
    public String notFound(HttpServletRequest request, UsernamePasswordAuthenticationToken currentUser){
        return "404";
    }

    @RequestMapping(value={"/403*"}, method=RequestMethod.GET)
    public String forbidden(HttpServletRequest request, UsernamePasswordAuthenticationToken currentUser){
        return "403";
    }
    
}
