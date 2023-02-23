package edu.hust.vn.screen.bike;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.utils.Utils;
import javafx.beans.binding.Bindings;

import java.io.IOException;

public class StandardEBikeScreenHandler extends BikeScreenHandler{

    protected StandardEBikeScreenHandler(Bike bike) throws IOException {
        super(bike);
        StandardEBike ebike = (StandardEBike)bike;
        batteryLifeLabel.setVisible(true);
        batteryPercentageLabel.setVisible(true);
        batteryLife.textProperty().bind(ebike.batteryLifeProperty().asString());
        batteryPercentage.textProperty().bind(Bindings.createStringBinding(() -> Utils.round(ebike.remainingBatteryLifeProperty().get() / ebike.batteryLifeProperty().get() * 100.0, 2) +" %",
                ebike.batteryLifeProperty(), ebike.remainingBatteryLifeProperty()));
    }
}
