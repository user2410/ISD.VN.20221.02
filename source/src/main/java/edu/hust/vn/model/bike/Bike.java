package edu.hust.vn.model.bike;

import edu.hust.vn.model.dock.Lock;
import javafx.beans.property.*;

public abstract class Bike {
    protected int id;
    protected StringProperty licensePlate = new SimpleStringProperty();
    protected int nSaddles;
    protected int nPedals;
    protected IntegerProperty price = new SimpleIntegerProperty();
    protected ObjectProperty<Lock> lock = new SimpleObjectProperty<>(null);

    public Bike(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringProperty licensePlateProperty() {
        return licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate.get();
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate.set(licensePlate);
    }

    public int getnSaddles() {
        return nSaddles;
    }

    public int getnPedals() {
        return nPedals;
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public ObjectProperty<Lock> lockProperty() {
        return lock;
    }

    public Lock getLock() {
        return lock.get();
    }

    public void setLock(Lock lock) {
        this.lock.set(lock);
    }

    public abstract String typeAsString();
}
