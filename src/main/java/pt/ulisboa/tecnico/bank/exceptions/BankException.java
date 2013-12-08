package pt.ulisboa.tecnico.bank.exceptions;

public class BankException extends RuntimeException {

	public BankException(String message) {
		super(message);
	}
	
	public BankException(Exception exception) {
		super(exception);
	}
}
