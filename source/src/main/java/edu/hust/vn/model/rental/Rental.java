package edu.hust.vn.model.rental;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.payment.PaymentTransaction;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Rental {
    private static Rental instance;

    private int id;
    private Dock dock;
    private Bike bike;
    private LongProperty startTime;
    private LongProperty lastActiveTime;
    private BooleanProperty active;

    private Rental(){
        dock = null;
        bike = null;
        startTime = new SimpleLongProperty(0);
        lastActiveTime = new SimpleLongProperty(0);
        active = new SimpleBooleanProperty(false);
    }

    public static Rental getInstance(){
        if(instance == null){
            synchronized (Rental.class){
                instance = new Rental();
            }
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dock getDock() {
        return dock;
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }
}
