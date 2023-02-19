package edu.hust.vn.model.payment;

import edu.hust.vn.common.exception.invalid_payment_info.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class PaymentCreditCard extends PaymentMethod{

    @Override
    public void createPaymentEntity(Map<String, String> info) throws InvalidPaymentInfoException {
        String cardOwner = info.get("cardOwner");
        String cardCode = info.get("cardCode");
        String expDate = info.get("expDate");
        String cvvCode = info.get("cvvCode");

        validateCardOwner(cardOwner);
        validateCardNumber(cardCode);
        validateExpDate(expDate);
        validateSecurityCode(cvvCode);

        CreditCard card = new CreditCard();
        card.setOwner(cardOwner);
        card.setCardCode(cardCode);
        card.setExpDate(expDate);
        card.setCvvCode(cvvCode);
        paymentEntity = card;
    }

    /**
     * validate card number only contains digits, letters and underscore
     *
     * @param cardNumber string of card number
     */
    private static void validateCardNumber(String cardNumber) throws InvalidPaymentInfoException {
        // check card number is not empty
        if (cardNumber == null || cardNumber.isBlank())
            throw new NullCardNumberException();
        if (cardNumber.length() == 0 || !cardNumber.matches("[0-9a-zA-Z_]+"))
            throw new InvalidCardNumberFormatException(cardNumber);

    }

    /**
     * validate card owner only contains digits, letters and spaces
     *
     * @param cardOwner string of card owner
     */
    private static void validateCardOwner(String cardOwner) throws InvalidPaymentInfoException {
        // check card owner is not empty
        if (cardOwner == null|| cardOwner.isBlank()) throw new NullCardOwnerException();
        if (cardOwner.length() == 0 || !cardOwner.matches("[0-9a-zA-Z ]+")) throw new InvalidCardOwnerFormatException(cardOwner);
    }

    /**
     * validate security code only contains digits and length is 3
     *
     * @param securityCode string of security code
     */
    private static void validateSecurityCode(String securityCode) throws InvalidPaymentInfoException {
        // check security code is not empty
        if (securityCode == null|| securityCode.isBlank()) throw new NullSecurityCodeException();

        // check security code exactly 3
        // check card owner contains only digits
        if (securityCode.length() != 3 || !securityCode.matches("[0-9]+")) throw new InvalidSecurityCodeFormatException(securityCode);

    }

    /**
     * validate expiration date has correct format dd/MM/yyyy
     *
     * @param expDate string of expiration date
     */
    private static void validateExpDate(String expDate) throws InvalidPaymentInfoException  {
        // check expire date is not empty
        if (expDate == null || expDate.isBlank()) throw new NullExpDateException();

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date ret = sdf.parse(expDate.trim());
            if (!sdf.format(ret).equals(expDate.trim())) {
                throw new InvalidExpDateFormatException(expDate);
            }
        } catch (ParseException e) {
            throw new InvalidExpDateFormatException(expDate);
        }
    }
}
