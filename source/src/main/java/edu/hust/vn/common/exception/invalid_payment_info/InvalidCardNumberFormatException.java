package edu.hust.vn.common.exception.invalid_payment_info;

public class InvalidCardNumberFormatException extends InvalidPaymentInfoException {
    private String cardNumber;

    public InvalidCardNumberFormatException(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String getMessage(){ return super.getMessage()+" card number : "+cardNumber;}
}
