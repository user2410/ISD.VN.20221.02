package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.payment.CreditCard;
import edu.hust.vn.model.payment.PaymentEntity;
import edu.hust.vn.model.payment.PaymentMethod;
import edu.hust.vn.subsystem.InterbankInterface;
import edu.hust.vn.subsystem.InterbankSubsystem;

import java.util.Hashtable;
import java.util.Map;

public class PaymentController extends BaseController{

    private PaymentEntity paymentEntity;

    public PaymentController(PaymentEntity paymentEntity){
        this.paymentEntity = paymentEntity;
    }

    public void payRental(int amount) throws RuntimeException{
//        DataStore.getInstance().interbank.payRental(creditCard, amount, "Rental fee payment");
    }

    public void payDeposit(int amount){
//        DataStore.getInstance().interbank.payRental(creditCard, amount, "Deposit payment");
    }

    public void refund(int amount){
//        DataStore.getInstance().interbank.payRental(creditCard, amount, "Rental fee refund");
    }
}
