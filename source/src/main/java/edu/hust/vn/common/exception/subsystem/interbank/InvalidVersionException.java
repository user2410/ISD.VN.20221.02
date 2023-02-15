package edu.hust.vn.common.exception.subsystem.interbank;;

/**
 * The InvalidVersionException wraps all unchecked 
 * exceptions You can use this exception to inform
 * 
 * @author SinhVien
 *
 */
public class InvalidVersionException extends PaymentException{
	public InvalidVersionException() {
		super("ERROR: Invalid Version Information!");
	}
}
