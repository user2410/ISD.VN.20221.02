package edu.hust.vn.entity;

public class Invoice extends Entity{
    private int id;
    private int rentalId;
    private int total;

    public Invoice(int id, int rentalId) {
        this.id = id;
        this.rentalId = rentalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
