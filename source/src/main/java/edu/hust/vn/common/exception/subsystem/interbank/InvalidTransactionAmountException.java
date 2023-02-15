package edu.hust.vn.common.exception.subsystem.interbank;;

/**
 * The InvalidCardException wraps all unchecked 
 * exceptions You can use this exception to inform
 *
 */
public class InvalidTransactionAmountException extends PaymentException {
	public InvalidTransactionAmountException() {
		super("ERROR: Invalid Transaction Amount!");
	}
}
