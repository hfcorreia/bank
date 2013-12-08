package pt.ulisboa.tecnico.bank.exceptions;

public class InvalidDepositAmountExcepiton extends BankException {

	public InvalidDepositAmountExcepiton(String message, Double amount) {
		super("Deposit to " + message + " is a negative amount: " + amount);
	}
}
