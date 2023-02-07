package edu.hust.vn.model.rental;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.payment.PaymentTransaction;

import java.time.LocalDateTime;

public class Rental {
    private String id;
    private Dock dock;
    private Bike bike;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime lastActiveTime;
    private boolean active;
    private PaymentTransaction rentalTransaction;
    private PaymentTransaction returnTransaction;

    public Rental(String id, Bike bike, LocalDateTime startTime, PaymentTransaction rentalTransaction) {
        this.id = id;
        this.bike = bike;
        this.startTime = startTime;
        this.rentalTransaction = rentalTransaction;
    }
}
