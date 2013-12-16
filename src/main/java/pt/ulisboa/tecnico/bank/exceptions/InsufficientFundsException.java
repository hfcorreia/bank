package pt.ulisboa.tecnico.bank.exceptions;


public class InsufficientFundsException extends BankException {

	public InsufficientFundsException(String accountNumber) {
		
		super("Insuficient Funds in account " + accountNumber);
	}

}
