package edu.hust.vn.screen.factory;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.IOException;

public class BikeScreenFactory {
    private static ObservableMap<Bike, BikeScreenHandler> bikeScreens = FXCollections.observableHashMap();

    public static BikeScreenHandler getInstance(Bike bike) throws IOException {
        if(!bikeScreens.containsKey(bike)){
            synchronized (BikeScreenHandler.class){
                if(!bikeScreens.containsKey(bike)){
                    bikeScreens.put(bike, new BikeScreenHandler(bike));
                }
            }
        }
        return bikeScreens.get(bike);
    }
}
