package edu.hust.vn.common.exception.subsystem.interbank;;

/**
 * The InternalServerErrorException wraps all unchecked 
 * exceptions You can use this exception to inform
 *
 */
public class InternalServerErrorException extends PaymentException {

	public InternalServerErrorException() {
		super("ERROR: Internal Server Error!");
	}

}
