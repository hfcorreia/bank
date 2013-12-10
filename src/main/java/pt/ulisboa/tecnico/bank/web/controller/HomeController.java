package pt.ulisboa.tecnico.bank.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/homepage")
public class HomeController {
	
		@RequestMapping("/hello")
	    public String helloWorld() {
	        return "hello";
	    }
	
}
