package edu.hust.vn.model.payment;

public class PaymentTransaction {
    private String transactionId;
    private String transactionContent;
    private int amount;
    private String createdAt;

    public PaymentTransaction(String transactionId, String transactionContent, int amount, String createdAt) {
        this.transactionId = transactionId;
        this.transactionContent = transactionContent;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
