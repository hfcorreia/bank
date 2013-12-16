package pt.ulisboa.tecnico.bank.exceptions;

public class DuplicateAccountException extends BankException {

	public DuplicateAccountException(String accountNumber) {
		super("Account Number: " + accountNumber + " all ready exists!");
	}
}
