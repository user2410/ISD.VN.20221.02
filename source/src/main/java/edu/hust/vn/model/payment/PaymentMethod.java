package edu.hust.vn.model.payment;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;

import java.util.Map;

public abstract class PaymentMethod {
    protected PaymentEntity paymentEntity;

    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public abstract void createPaymentEntity(Map<String, String> info) throws InvalidPaymentInfoException;
}
