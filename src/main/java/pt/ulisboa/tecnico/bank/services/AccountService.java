package pt.ulisboa.tecnico.bank.services;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.bank.dao.AccountDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.Currencies;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.BankException;
import pt.ulisboa.tecnico.bank.exceptions.DuplicateAccountException;
import pt.ulisboa.tecnico.bank.exceptions.InsufficientFundsException;
import pt.ulisboa.tecnico.bank.exceptions.InvalidDepositAmountExcepiton;
import pt.ulisboa.tecnico.bank.exceptions.NoSuchAccountException;
import pt.ulisboa.tecnico.bank.security.HashUtil;
import pt.ulisboa.tecnico.bank.security.SecurityMatrixUtil;

@Service("createAccount")
public class AccountService {

	@Autowired
	private AccountDAO accountDAO;
	
	@Transactional
	public Account createNewAccount(User user, String accountNumber, Double balance) throws BankException {
        if(balance < 0)
            throw new InsufficientFundsException(accountNumber);
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
	
	@Transactional
	public boolean checkAccountExistence(String accountNumber){
		return accountDAO.checkAccountExistence(accountNumber);
	}
	
	@Transactional
	public void withdraw(String accountNumber, Double amount)   throws BankException {
		Account account = accountDAO.getAccount(accountNumber);
		
		if( account == null )
			throw new NoSuchAccountException(accountNumber);
		if( account.getBalance() - amount < 0)
			throw new InsufficientFundsException(accountNumber);
		
		account.setBalance( account.getBalance() - amount);
		
		accountDAO.update(account);
	}

	@Transactional
	public void deposit(String accountNumber, Double amount) throws BankException {
		Account account = accountDAO.getAccount(accountNumber);
		
		if( account == null )
			throw new NoSuchAccountException(accountNumber);
		
		if( amount < 0 )
			throw new InvalidDepositAmountExcepiton(accountNumber, amount);
		
		account.setBalance( account.getBalance() + amount);
		
		accountDAO.update(account);
	}
	
	@Transactional
	public void transfer(String fromAccount, String toAccount, Double amount) throws BankException {
		withdraw(fromAccount, amount);
		deposit(toAccount, amount);
	}

	@Transactional
	public boolean checkMatrixInput(String accountName, String row, int col,
			int number, String matrix) {
		Account account = accountDAO.getAccount(accountName);

		TreeMap<String, ArrayList<ArrayList<String>>> map = SecurityMatrixUtil.generateFromJSON(account
				.getOwner().getMatrix());

		String hash = HashUtil.hashPassword(matrix, account.getOwner().getSalt(), Integer.parseInt(account.getOwner().getIterations() ) );
		return map.get(row).get(col).get(number).equals(hash);
	}
}
