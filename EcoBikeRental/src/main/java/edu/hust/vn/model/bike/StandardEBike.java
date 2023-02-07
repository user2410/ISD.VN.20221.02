package edu.hust.vn.model.bike;

public class StandardEBike extends Bike{
    private float batteryLife;

    private float remainingBatteryLife;

    public StandardEBike(Bike bike, float batteryLife){
        this.id = bike.id;
        this.licensePlate = bike.licensePlate;
        this.nPedals = 2;
        this.nSaddles = 1;
        this.type = BIKE_TYPE.STANDARD_BIKE;
        this.price = bike.price;
        this.batteryLife = this.remainingBatteryLife = batteryLife;
    }

    public float getBatteryLife() {
        return batteryLife;
    }

    public float getRemainingBatteryLife() {
        return remainingBatteryLife;
    }

    public void setRemainingBatteryLife(float remainingBatteryLife) {
        this.remainingBatteryLife = remainingBatteryLife;
    }
}
