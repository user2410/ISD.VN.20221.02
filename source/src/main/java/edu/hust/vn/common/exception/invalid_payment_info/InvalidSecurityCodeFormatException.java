package edu.hust.vn.common.exception.invalid_payment_info;

public class InvalidSecurityCodeFormatException extends InvalidPaymentInfoException {
    private String cvvCode;

    public InvalidSecurityCodeFormatException(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    @Override
    public String getMessage(){ return super.getMessage()+": "+cvvCode;}
}
