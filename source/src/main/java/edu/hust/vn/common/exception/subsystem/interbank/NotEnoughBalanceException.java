package edu.hust.vn.common.exception.subsystem.interbank;

/**
 * The NotEnoughBalanceException wraps all unchecked
 * exceptions You can use this exception to inform
 *
 *
 */
public class NotEnoughBalanceException extends PaymentException{

	public NotEnoughBalanceException() {
		super("ERROR: Not enough balance in card!");
	}

}
