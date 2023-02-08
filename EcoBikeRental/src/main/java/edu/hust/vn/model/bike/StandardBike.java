package edu.hust.vn.model.bike;

public class StandardBike extends Bike{
    public StandardBike() {
        nPedals = 2;
        nSaddles = 1;
    }

    @Override
    public String typeAsString() {
        return "STANDARD_BIKE";
    }
}
