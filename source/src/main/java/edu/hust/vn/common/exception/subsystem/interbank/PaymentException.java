package edu.hust.vn.common.exception.subsystem.interbank;

public class PaymentException extends RuntimeException{
    public PaymentException(String message) {
        super(message);
    }
}
