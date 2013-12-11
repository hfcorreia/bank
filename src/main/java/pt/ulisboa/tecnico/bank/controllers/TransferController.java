package pt.ulisboa.tecnico.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pt.ulisboa.tecnico.bank.services.AccountService;

@Controller
@SessionAttributes
public class TransferController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	public String transferShowPage() {
		return "transfer";
	}

	@RequestMapping(value = "transfer/submit", method = RequestMethod.POST)
	public String transferSubmitForm(ModelMap modelMap) { 
		String accountNumber = (String) modelMap.get("accountNumber");
		String accountNumber2 = (String) modelMap.get("accountNumber2");
		String amount = (String) modelMap.get("amount");

		if (!accountService.checkAccountExistence(accountNumber)) {
			modelMap.addAttribute("error", "true");
			return "transfer";
		}
		if (!accountService.checkAccountExistence(accountNumber2)) {
			modelMap.addAttribute("error", "true");
			return "transfer";
		}

		accountService.transfer(accountNumber, accountNumber2, new Double(
				amount));
		
		return "success";
	}
	
	@RequestMapping("/success")
	public String successCreation(ModelMap modelMap) {
		String accountNumber = (String) modelMap.get("accountNumber");
		String accountNumber2 = (String) modelMap.get("accountNumber2");
		modelMap.addAttribute("succes", "Transfered form " + accountNumber + " to " + accountNumber2 + "!");
		return "success";
	}
}
