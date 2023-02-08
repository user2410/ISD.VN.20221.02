package edu.hust.vn.model.bike;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class StandardEBike extends Bike{
    private FloatProperty batteryLife = new SimpleFloatProperty();

    private FloatProperty remainingBatteryLife = new SimpleFloatProperty();

    public StandardEBike(float batteryLife){
        this.nPedals = 2;
        this.nSaddles = 1;
        this.batteryLife.set(batteryLife);
        this.remainingBatteryLife.set(batteryLife);
    }

    public FloatProperty batteryLifeProperty() {
        return batteryLife;
    }

    public float getBatteryLife() {
        return batteryLife.get();
    }

    public FloatProperty remainingBatteryLifeProperty() {
        return remainingBatteryLife;
    }

    public float getRemainingBatteryLife() {
        return remainingBatteryLife.get();
    }

    public void setRemainingBatteryLife(float remainingBatteryLife) {
        this.remainingBatteryLife.set(remainingBatteryLife);
    }

    @Override
    public String typeAsString() {
        return "STANDARD_EBIKE";
    }
}
