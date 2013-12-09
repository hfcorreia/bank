package pt.ulisboa.tecnico.bank.exceptions;

import pt.ulisboa.tecnico.bank.domain.Account;

public class InsufficientFundsException extends BankException {

	public InsufficientFundsException(String accountNumber) {
		
		super("Insuficient Funds in account " + accountNumber);
	}

}
