package pt.ulisboa.tecnico.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.bank.dao.AccountDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.Currencies;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicateAccountException;

@Service("createAccount")
public class CreateAccountService {

	@Autowired
	private AccountDAO accountDAO;
	
	@Transactional
	public Account createNewAccount(User user, String accountNumber, Double balance) throws DuplicateAccountException {
		Account account = new Account();
		account.setBalance(balance);
		account.setBlocked(false);
		account.setCurrency(Currencies.EUR);
		account.setNumber(accountNumber);
		account.setOwner(user);
		
		account = accountDAO.createAccount(account);
		if( account != null ) 
			return account;
		throw new DuplicateAccountException(accountNumber);
	}
}
