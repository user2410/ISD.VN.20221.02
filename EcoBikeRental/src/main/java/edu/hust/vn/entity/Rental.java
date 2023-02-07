package edu.hust.vn.entity;

import java.sql.Timestamp;

public class Rental {
    private int id;
    private int bikeId;
    private Timestamp startTime;
    private Timestamp returnTime;

    public Rental(int id, int bikeId, Timestamp startTime) {
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }
}
