package pt.ulisboa.tecnico.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.services.AccountService;
import pt.ulisboa.tecnico.bank.services.UserService;

import java.security.Principal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SONY
 * Date: 12/12/13
 * Time: 4:19 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AccountsListController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/listaccounts", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public String printAccountsList(ModelMap modelMap, Principal principal) {
        String username = principal.getName();
        List<Account> accounts = userService.getUserAccounts(username);
        String accountTable = "<table>";
        for(Account account : accounts) {
            accountTable = accountTable + "<tr>" + "<td>" + account.getNumber() + "</td><td>" + account.getBalance() + "</td></tr>";
        }
        accountTable += "</table>";
        modelMap.addAttribute("accounts_list", accountTable);

        return "listaccounts";
    }
}
