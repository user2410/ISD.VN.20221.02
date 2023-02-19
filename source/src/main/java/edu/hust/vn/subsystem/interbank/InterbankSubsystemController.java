package edu.hust.vn.subsystem.interbank;

import edu.hust.vn.common.exception.subsystem.interbank.*;
import edu.hust.vn.model.payment.CreditCard;
import edu.hust.vn.model.payment.PaymentTransaction;
import edu.hust.vn.utils.Configs;
import edu.hust.vn.utils.MyMap;
import edu.hust.vn.utils.Utils;

import java.util.Map;

public class InterbankSubsystemController {

    private static final String PUBLIC_KEY = "AQzdE8O/fR8=";
    private static final String SECRET_KEY = "BUXj/7/gHHI=";
    private static final String PAY_COMMAND = "pay";
    private static final String VERSION = "1.0.0";

    private static InterbankBoundary interbankBoundary = new InterbankBoundary();

    public PaymentTransaction refund(CreditCard card, int amount, String contents) {
        return null;
    }

    private String generateData(Map<String, Object> data) {
        return ((MyMap) data).toJSON();
    }

    public PaymentTransaction payRental(CreditCard card, int amount, String contents) {
        return null;
    }

}

