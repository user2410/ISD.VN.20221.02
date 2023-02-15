package edu.hust.vn.subsystem;

import edu.hust.vn.common.exception.subsystem.interbank.PaymentException;
import edu.hust.vn.common.exception.subsystem.interbank.UnrecognizedException;
import edu.hust.vn.model.payment.CreditCard;
import edu.hust.vn.model.payment.PaymentTransaction;

public interface InterbankInterface {

    public PaymentTransaction payRental(CreditCard card, int amount, String contents)
        throws PaymentException, UnrecognizedException;

    public PaymentTransaction refund(CreditCard card, int amount, String contents)
        throws PaymentException, UnrecognizedException;
}
