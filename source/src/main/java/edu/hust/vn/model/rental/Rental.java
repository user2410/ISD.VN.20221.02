package edu.hust.vn.model.rental;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.payment.PaymentTransaction;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;

public class Rental {
    private static Rental instance;

    private int id;
    private Dock dock;
    private ObjectProperty<Bike> bike;
    private ObjectProperty<LocalDateTime> startTime;
    private ObjectProperty<LocalDateTime> endTime;
    private ObjectProperty<LocalDateTime> lastActiveTime;
    private BooleanProperty active;

    private Rental(){
        clear();
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

    public ObjectProperty<Bike> bikeProperty() {
        return bike;
    }

    public Bike getBike() {
        return bike.get();
    }

    public void setBike(Bike bike) {
        this.bike.set(bike);
    }

    public LocalDateTime getStartTime() {
        return startTime.get();
    }

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime.set(startTime);
    }

    public LocalDateTime getEndTime() {
        return endTime.get();
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime.set(endTime);
    }

    public LocalDateTime getLastActiveTime() {
        return lastActiveTime.get();
    }

    public ObjectProperty<LocalDateTime> lastActiveTimeProperty() {
        return lastActiveTime;
    }

    public void setLastActiveTime(LocalDateTime lastActiveTime) {
        this.lastActiveTime.set(lastActiveTime);
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

    public void clear(){
        dock = null;
        bike = new SimpleObjectProperty<>(null);
        startTime = new SimpleObjectProperty<>(null);
        endTime = new SimpleObjectProperty<>(null);
        lastActiveTime = new SimpleObjectProperty<>(null);
        active = new SimpleBooleanProperty(false);
    }
}
