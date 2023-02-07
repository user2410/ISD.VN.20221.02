package edu.hust.vn.model.bike;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bike {
    protected int id;
    protected StringProperty licensePlate = new SimpleStringProperty();
    protected int nSaddles;
    protected int nPedals;
    protected int price;
    public enum BIKE_TYPE{
        STANDARD_BIKE, STANDARD_EBIKE, TWIN_BIKE
    }
    protected BIKE_TYPE type;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BIKE_TYPE getType() {
        return type;
    }

    public void setType(BIKE_TYPE type) {
        this.type = type;
    }
}
