package pt.ulisboa.tecnico.bank.exceptions;

public class NoSuchAccountException extends BankException {

	public NoSuchAccountException(String accountNumber) {
		super("Account " + accountNumber + " does not exist!");
	}

}
