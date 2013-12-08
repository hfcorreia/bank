package pt.ulisboa.tecnico.bank.dao;

import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 3:07 AM
 */
public interface AccountDAO extends DAO<Account> {
	
	 public Account getAccount (String accountName);

	 public User getAccountOwner (String accountName);
	 
	 public boolean checkAccountExistence(String accountName);
	    
	 public Account createAccount(Account account);
	  
}
