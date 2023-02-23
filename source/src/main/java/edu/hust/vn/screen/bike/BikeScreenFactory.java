package edu.hust.vn.screen.bike;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardBike;
import edu.hust.vn.model.bike.StandardEBike;

import java.io.IOException;

public class BikeScreenFactory {
    public static BikeScreenHandler initialize(Bike bike) throws IOException {
        if(bike instanceof StandardEBike){
            return new StandardEBikeScreenHandler(bike);
        }else if (bike instanceof StandardBike){
            return new BikeScreenHandler(bike);
        } else {
            return new TwinBikeScreenHandler(bike);
        }
    }
}
