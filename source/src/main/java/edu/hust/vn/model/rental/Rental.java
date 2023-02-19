package edu.hust.vn.model.rental;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Rental {
    private int id;
    private Lock lock;
    private ObjectProperty<Bike> bike = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();
    private LongProperty totalTime = new SimpleLongProperty();
    private BooleanProperty active = new SimpleBooleanProperty();

    public Rental(){
        clear();
    }

    public void assign(Rental rental){
        this.id = rental.id;
        this.lock = rental.lock;
        this.setBike(rental.getBike());
        this.startTime.set(rental.getStartTime());
        this.totalTime.set(rental.getTotalTime());
        this.endTime.set(rental.getEndTime());
        this.active.set(rental.isActive());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
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

    public long getTotalTime() {
        return totalTime.get();
    }

    public LongProperty totalTimeProperty() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime.set(totalTime);
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
        lock = null;
        bike.set(null);
        startTime.set(null);
        endTime.set(null);
        totalTime.set(0);
        active.set(false);
    }

    public void startRenting(){
        totalTime.set(0);
        active.set(true);
    }
}
