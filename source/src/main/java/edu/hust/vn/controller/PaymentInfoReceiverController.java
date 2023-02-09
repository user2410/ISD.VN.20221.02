package edu.hust.vn.controller;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;

public interface PaymentInfoReceiverController {

    public void setPaymentInfo(String key, String value);
    public void validatePaymentInfo() throws InvalidPaymentInfoException;
    public void submitPaymentInfo();
}
