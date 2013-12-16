package pt.ulisboa.tecnico.bank.exceptions;

public class DuplicatedUserException extends BankException {

	public DuplicatedUserException(String userName) {
		super("Username " + userName + " all ready exists!");
	}
}
