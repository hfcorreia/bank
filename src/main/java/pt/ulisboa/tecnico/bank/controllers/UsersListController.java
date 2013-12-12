package pt.ulisboa.tecnico.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.services.UserService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SONY
 * Date: 12/12/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class UsersListController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/listusers", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String printUsersList(ModelMap modelMap) {

        List<User> users = userService.getAllUsers();
        String userTable = "<table>";
        for(User user : users) {
            userTable = userTable + "<tr>" + "<td>" + user.getUsername() + "</td><td>" + user.getEmail() + "</td></tr>";
        }
        userTable += "</table>";
        modelMap.addAttribute("users_list", userTable);
        return "listusers";
    }
}
