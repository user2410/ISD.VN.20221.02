package edu.hust.vn.common.exception.subsystem.interbank;;

/**
 * The InvalidCardException wraps all unchecked 
 * exceptions You can use this exception to inform
 * 
 * @author SinhVien
 *
 */
public class InvalidCardException extends PaymentException {
	public InvalidCardException() {
		super("ERROR: Invalid card!");
	}
}
