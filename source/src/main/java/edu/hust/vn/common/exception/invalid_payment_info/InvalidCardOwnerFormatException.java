package edu.hust.vn.common.exception.invalid_payment_info;

public class InvalidCardOwnerFormatException extends InvalidPaymentInfoException {
    private String cardOwner;

    public InvalidCardOwnerFormatException(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    @Override
    public String getMessage(){ return super.getMessage()+" card owner : "+cardOwner;}
}
