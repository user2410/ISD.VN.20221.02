package edu.hust.vn.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Dock extends Entity{
    private int id;
    private String address;
    private String name;
    private int capacity;
    public Dock(int id, String address, String name, int capacity) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // fake
    private int availableBike = 49;
    public int getAvailableBike(){
        return availableBike;
    }
    public void setAvailableBike(int availableBike){
        this.availableBike  = availableBike;
    }
    public  int getAvailableLot(){
        return capacity - getAvailableBike();
    }
    public IntegerProperty getAvailableBikeProperty(){
        // tinh toan cho trong
       return new SimpleIntegerProperty(getAvailableBike());
    }

    public IntegerProperty getAvailableLotProperty(){
        // tinh toan cho trong
        return new SimpleIntegerProperty(getAvailableLot() );
    }

    public boolean isFull(){
        if ( getAvailableLot() > 0 ){
            return false;
        }else return true;
    }

    public void addBikeToDock(Bike bike){
        setAvailableBike(getAvailableBike() + 1);
    }
}
