package edu.hust.vn.common.exception.invalid_payment_info;

public class NullCardNumberException extends InvalidPaymentInfoException{
    @Override
    public String getMessage(){ return super.getMessage() + ": Empty card number field";}
}
