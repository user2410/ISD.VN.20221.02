package edu.hust.vn.entity;

public class Rental {
    private int id;
    private int bikeId;
    private int startTime;
    private int returnTime;

    public Rental(int id, int bikeId, int startTime) {
        this.id = id;
        this.bikeId = bikeId;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(int returnTime) {
        this.returnTime = returnTime;
    }
}
