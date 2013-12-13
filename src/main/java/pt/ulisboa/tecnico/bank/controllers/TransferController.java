package pt.ulisboa.tecnico.bank.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pt.ulisboa.tecnico.bank.exceptions.BankException;
import pt.ulisboa.tecnico.bank.services.AccountService;

@Controller
@SessionAttributes({"row1", "row2", "col1", "col2" , "num1", "num2"})
public class TransferController {
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	public String transferShowPage(ModelMap modelMap, HttpServletRequest session) {
		char[] alphabet = "ABCDEFG".toCharArray();
		int rowIndex = ((int) (Math.random() * 100 ))% 7;
		int columnIndex = ((int) (Math.random() * 100 ))% 9;
		int numberIndex = ((int) (Math.random() * 100 ))% 3;
		int rowIndex2 = ((int) (Math.random() * 100 ))% 7;
		int columnIndex2 = ((int) (Math.random() * 100 ))% 9;
		int numberIndex2 = ((int) (Math.random() * 100 ))% 3;
		
		session.getSession().setAttribute("row1", alphabet[rowIndex] );
		session.getSession().setAttribute("col1", columnIndex );
		session.getSession().setAttribute("num1", numberIndex );
		
		session.getSession().setAttribute("row2", alphabet[rowIndex2] );
		session.getSession().setAttribute("col2", columnIndex2 );
		session.getSession().setAttribute("num2", numberIndex2 );
		
		modelMap.addAttribute("ROW1", alphabet[rowIndex] );
		modelMap.addAttribute("COL1", columnIndex );
		modelMap.addAttribute("NUM1", numberIndex );
		
		modelMap.addAttribute("ROW2", alphabet[rowIndex2] );
		modelMap.addAttribute("COL2", columnIndex2 );
		modelMap.addAttribute("NUM2", numberIndex2 );
		
		return "transfer";
	}

	@RequestMapping(value = "transfer/submit", method = RequestMethod.POST)
	public String transferSubmitForm(
			@RequestParam("accountNumber") String accountNumber,
			@RequestParam("accountNumber2") String accountNumber2,
			@RequestParam("amount") String amount,
			@RequestParam("matrix1") String matrix1,
			@RequestParam("matrix2") String matrix2, ModelMap modelMap, HttpServletRequest session) {
		
		modelMap.addAttribute("accountNumber", accountNumber);
		modelMap.addAttribute("accountNumber2", accountNumber2);
		modelMap.addAttribute("amount", amount);

		if (!accountService.checkAccountExistence(accountNumber)) {
			modelMap.addAttribute("error", "true");
			modelMap.addAttribute("errorMSG", "Account " + accountNumber+ " doesn't exits");
			return "transfer";
		}
		if (!accountService.checkAccountExistence(accountNumber2)) {
			modelMap.addAttribute("error", "true");
			modelMap.addAttribute("errorMSG", "Account2 " + accountNumber2 + " doesn't exits");
			return "transfer";
		}
		
		String row1 = new String( session.getSession().getAttribute("row1")  + "");
		String row2 = new String( session.getSession().getAttribute("row2")  + "");
		int col1 = Integer.parseInt( new String( session.getSession().getAttribute("col1")  + ""));
		int col2 = Integer.parseInt( new String( session.getSession().getAttribute("col2")  + ""));
		int num1 = Integer.parseInt( new String( session.getSession().getAttribute("num1")  + ""));
		int num2 = Integer.parseInt( new String( session.getSession().getAttribute("num2")  + ""));
		
		if( ! accountService.checkMatrixInput(accountNumber, row1, col1, num1, matrix1)){
			modelMap.addAttribute("error", "true");
			modelMap.addAttribute("errorMSG", "Matrix error value");
			return "redirect:/transfer";
		}

		if( ! accountService.checkMatrixInput(accountNumber2, row2, col2, num2, matrix2)){
			modelMap.addAttribute("error", "true");
			modelMap.addAttribute("errorMSG", "Matrix error value");
			return "transfer";
		}
		
		try {
			accountService.transfer(accountNumber, accountNumber2, new Double(amount));
		} catch(BankException e) {
			modelMap.addAttribute("error", "true");
			modelMap.addAttribute("errorMSG", e.getMessage());
			return "transfer";
		}
		
		modelMap.addAttribute("success", "Transfer successful!");
		return "success";
	}
}
