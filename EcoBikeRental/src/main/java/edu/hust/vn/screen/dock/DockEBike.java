package edu.hust.vn.screen.dock;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.dock.Lock;

public class DockEBike extends DockBike {

    private float batteryLife;

    private float remainingBatteryLife;

    public DockEBike(StandardEBike bike, Lock lock) {
        super(bike, lock);
        this.remainingBatteryLife = batteryLife;
    }

    public float getBatteryLife() {
        return batteryLife;
    }

    public float getRemainingBatteryLife() {
        return remainingBatteryLife;
    }
}
