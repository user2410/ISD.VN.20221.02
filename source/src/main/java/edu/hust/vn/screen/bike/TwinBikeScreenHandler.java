package edu.hust.vn.screen.bike;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.TwinBike;

import java.io.IOException;

public class TwinBikeScreenHandler extends BikeScreenHandler{
    protected TwinBikeScreenHandler(Bike bike) throws IOException {
        super(bike);
        nRearSeats.setText(String.valueOf(((TwinBike)bike).getnRearSeats()));
    }
}
