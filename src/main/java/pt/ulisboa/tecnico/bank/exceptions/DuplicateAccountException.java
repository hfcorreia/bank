package pt.ulisboa.tecnico.bank.exceptions;

public class DuplicateAccountException extends Exception {

	public DuplicateAccountException(String accountNumber) {
		super("Account Number: " + accountNumber + " all ready exists!");
	}
}
