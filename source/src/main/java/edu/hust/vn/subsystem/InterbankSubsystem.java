package edu.hust.vn.subsystem;

import edu.hust.vn.common.exception.subsystem.interbank.PaymentException;
import edu.hust.vn.common.exception.subsystem.interbank.UnrecognizedException;
import edu.hust.vn.model.payment.CreditCard;
import edu.hust.vn.model.payment.PaymentTransaction;
import edu.hust.vn.subsystem.interbank.InterbankSubsystemController;

public class InterbankSubsystem implements InterbankInterface {
    /**
     * Represent the controller of the subsystem
     */
    private InterbankSubsystemController ctl;

    /**
     * Initializes a newly created {@code InterbankSubsystem} object so that it
     * represents an Interbank subsystem.
     */
    public InterbankSubsystem() {
        String str = new String();
        this.ctl = new InterbankSubsystemController();
    }

    /**
     * @see InterbankInterface#payRental(CreditCard, int, String)
     */
    @Override
    public PaymentTransaction payRental(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        return ctl.payRental(card, amount, contents);
    }

    /**
     * @see InterbankInterface#refund(CreditCard, int, String)
     */
    @Override
    public PaymentTransaction refund(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        return ctl.refund(card, amount, contents);
    }
}
