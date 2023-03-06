package edu.hust.vn.model.payment;

public class PaymentTransaction {
    private String errorCode;
    private PaymentEntity paymentEntity;
    private String transactionId;
    private String transactionContent;
    private int amount;
    private String createdAt;

    public PaymentTransaction(String errorCode, PaymentEntity entity, String transactionId, String transactionContent,
                              int amount, String createdAt) {
        super();
        this.errorCode = errorCode;
        this.paymentEntity = entity;
        this.transactionId = transactionId;
        this.transactionContent = transactionContent;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

