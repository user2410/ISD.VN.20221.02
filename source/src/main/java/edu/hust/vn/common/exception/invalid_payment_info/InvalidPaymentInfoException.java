package edu.hust.vn.common.exception.invalid_payment_info;

import edu.hust.vn.common.exception.EBRException;

public class InvalidPaymentInfoException extends EBRException {
    @Override
    public String getMessage(){ return "Invalid payment info";}
}
