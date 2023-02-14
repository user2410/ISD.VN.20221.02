package edu.hust.vn.model.bike;

public class TwinBike extends Bike{
    private int nRearSeats = 1;

    public TwinBike() {
        nPedals = 4;
        nSaddles = 2;
        this.nRearSeats = 1;
    }

    public int getnRearSeats() {
        return nRearSeats;
    }

    public void setnRearSeats(int nRearSeats) {
        this.nRearSeats = nRearSeats;
    }

    @Override
    public String typeAsString() {
        return "TWIN_BIKE";
    }
}
