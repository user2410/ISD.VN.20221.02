package edu.hust.vn.common.exception.subsystem.interbank;;

/**
 * The SuspiciousTransactionException wraps all unchecked
 * exceptions You can use this exception to inform
 * 
 * @author SinhVien
 *
 */
public class SuspiciousTransactionException extends PaymentException {
	public SuspiciousTransactionException() {
		super("ERROR: Suspicious Transaction Report!");
	}
}
