package edu.hust.vn.common.exception.subsystem.interbank;

/**
 * The NotEnoughTransactionException wraps all unchecked 
 * exceptions You can use this exception to inform
 *
 */
public class NotEnoughTransactionInfoException extends PaymentException {
	public NotEnoughTransactionInfoException() {
	super("ERROR: Not Enough Transaction Information");
}
}
