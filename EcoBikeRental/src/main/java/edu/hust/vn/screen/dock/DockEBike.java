package edu.hust.vn.screen.dock;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.dock.Lock;
import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;

public class DockEBike extends DockBike {

    public DockEBike(DockScreenHandler dockScreen, StandardEBike bike, Lock lock) {
        super(dockScreen, bike, lock);
    }

    public FloatProperty batteryLifeProperty(){
        return ((StandardEBike)bike).batteryLifeProperty();
    }

    public FloatProperty remainingBatteryLifeProperty(){
        return ((StandardEBike)bike).remainingBatteryLifeProperty();
    }

}
