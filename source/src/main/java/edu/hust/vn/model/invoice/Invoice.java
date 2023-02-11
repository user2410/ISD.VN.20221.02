package edu.hust.vn.model.invoice;

import edu.hust.vn.model.rental.Rental;

public class Invoice {
    private Rental rental;
    private int amount;

    public Invoice(){}

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }
}
