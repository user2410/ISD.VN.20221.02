package edu.hust.vn.controller.strategy.paymentinfo_validation;

import edu.hust.vn.common.exception.invalid_payment_info.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;

public class CardValidationStrategy implements PaymentInfoValidationStrategy{
    @Override
    public void validate(HashMap<String, String> info) throws InvalidPaymentInfoException {
        validateCardOwner(info.get("cardOwner"));
        validateCardNumber(info.get("cardNumber"));
        validateExpDate(info.get("expDate"));
        validateSecurityCode(info.get("cvvCode"));
    }

    /**
     * validate card number only contains digits, letters and underscore
     *
     * @param cardNumber string of card number
     */
    public static void validateCardNumber(String cardNumber) throws InvalidPaymentInfoException {
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
    public static void validateCardOwner(String cardOwner) throws InvalidPaymentInfoException {
        // check card owner is not empty
        if (cardOwner == null|| cardOwner.isBlank()) throw new NullCardOwnerException();
        if (cardOwner.length() == 0 || !cardOwner.matches("[0-9a-zA-Z ]+")) throw new InvalidCardOwnerFormatException(cardOwner);
    }

    /**
     * validate security code only contains digits and length is 3
     *
     * @param securityCode string of security code
     */
    public static void validateSecurityCode(String securityCode) throws InvalidPaymentInfoException {
        // check security code is not empty
        if (securityCode == null|| securityCode.isBlank()) throw new NullSecurityCodeException();

        // check security code exactly 3
        // check card owner contains only digits
        if (securityCode.length() != 3 || !securityCode.matches("[0-9]+")) throw new InvalidSecurityCodeFormatException(securityCode);

    }

    /**
     * validate expiration date has correct format MMyy and not yet reached
     *
     * @param expDate string of expiration date
     */
    public static void validateExpDate(String expDate) throws InvalidPaymentInfoException  {
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
