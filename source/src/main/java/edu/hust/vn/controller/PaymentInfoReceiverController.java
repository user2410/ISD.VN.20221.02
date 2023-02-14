package edu.hust.vn.controller;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;

import java.util.Map;

public interface PaymentInfoReceiverController {

    public Map<String, String> getPaymentInfo();
    public void setPaymentInfo(String key, String value);
    public void validatePaymentInfo() throws InvalidPaymentInfoException;
}
