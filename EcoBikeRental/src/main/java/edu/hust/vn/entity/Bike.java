package edu.hust.vn.entity;

public class Bike extends Entity{
    private int id;
    private String licensePlate;
    private int price;
    private int battery = 80;
    public Bike(int id, String licensePlate, int price) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getPrice() {
        return price;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
