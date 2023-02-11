package edu.hust.vn.controller;

import edu.hust.vn.model.payment.CreditCard;

import java.util.Hashtable;
import java.util.Map;

public class PaymentController extends BaseController{

    private CreditCard creditCard;
//    private InterbankSubsystem;

    public PaymentController(Map<String, String> paymentInfo){
        creditCard = new CreditCard();
        creditCard.setOwner(paymentInfo.get("cardOwner"));
        creditCard.setCardCode(paymentInfo.get("cardNumber"));
        creditCard.setExpDate(paymentInfo.get("expDate"));
        creditCard.setCvvCode(paymentInfo.get("cvvCode"));
    }

    public void payRental(int amount) throws RuntimeException{

    }
}
