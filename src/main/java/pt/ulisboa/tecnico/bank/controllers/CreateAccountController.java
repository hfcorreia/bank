package pt.ulisboa.tecnico.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.services.AccountService;
import pt.ulisboa.tecnico.bank.services.UserService;

/**
 * Created with IntelliJ IDEA.
 * User: SONY
 * Date: 12/12/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CreateAccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createaccount", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String createAccountShowPage() {
        return "createaccount";
    }

    @RequestMapping(value = "createaccount/submit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String createAccountSubmitForm(@RequestParam("owner") String owner,
                                          @RequestParam("balance") String balance,
                                          ModelMap modelMap) {

        if(!userService.checkUsernameExistence(owner)) {
            modelMap.addAttribute("error", true);
            modelMap.addAttribute("errorMsg", "User "+owner+" does not exist,");
            return "createaccount";
        }

        Double money = Double.parseDouble(balance);
        if(money <= 0) {
            modelMap.addAttribute("error", true);
            modelMap.addAttribute("errorMsg", "Insufficient balance.");
            return "createaccount";
        }

        User user = userService.getUserByUserName(owner);
        Account account = accountService.createNewAccount(user, "1234567", money);

        modelMap.addAttribute("success", "Account " + account.getNumber() + " created.");

        return "success";
    }
}
