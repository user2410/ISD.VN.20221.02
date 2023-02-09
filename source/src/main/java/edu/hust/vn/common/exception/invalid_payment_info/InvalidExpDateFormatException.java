package edu.hust.vn.common.exception.invalid_payment_info;

public class InvalidExpDateFormatException extends InvalidPaymentInfoException {
    private String expDate;

    public InvalidExpDateFormatException(String expDate) {
        this.expDate = expDate;
    }

    @Override
    public String getMessage(){ return super.getMessage()+" expiry date: "+expDate;}
}
