package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.payment.CreditCard;
import edu.hust.vn.subsystem.InterbankInterface;
import edu.hust.vn.subsystem.InterbankSubsystem;

import java.util.Hashtable;
import java.util.Map;

public class PaymentController extends BaseController{

    private CreditCard creditCard;

    public PaymentController(Map<String, String> paymentInfo){
        creditCard = new CreditCard();
        creditCard.setOwner(paymentInfo.get("cardOwner"));
        creditCard.setCardCode(paymentInfo.get("cardNumber"));
        creditCard.setExpDate(paymentInfo.get("expDate"));
        creditCard.setCvvCode(Integer.parseInt(paymentInfo.get("cvvCode")));
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
