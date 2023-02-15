package edu.hust.vn.controller.strategy.paymentinfo_validation;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;

import java.util.HashMap;

public interface PaymentInfoValidationStrategy {
    public void validate(HashMap<String, String> info) throws InvalidPaymentInfoException;
}
