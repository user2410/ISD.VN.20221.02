package edu.hust.vn;

import edu.hust.vn.controller.strategy.paymentinfo_validation.CardValidationStrategy;
import edu.hust.vn.controller.strategy.paymentinfo_validation.PaymentInfoValidationStrategy;
import edu.hust.vn.controller.strategy.pricing.IPricing;
import edu.hust.vn.controller.strategy.pricing.Pricing;
import edu.hust.vn.db.EBRDB;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.BikeDAO;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.DockDAO;
import edu.hust.vn.model.dock.LockDAO;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.utils.Configs;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private static DataStore instance;

    public Connection dbConn;
    public LockDAO lockDAO;
    public DockDAO dockDAO;
    public BikeDAO bikeDAO;

    public ObservableList<Dock> dockList;
    public ObservableList<Bike> bikeList;
    public Rental currentRental;

    public Map<String, Image> bikeImages;

    public PaymentInfoValidationStrategy paymentInfoValidationStrategy;
    public IPricing priceCalculatingStrategy;

    private DataStore(){
        try {
            dbConn = new EBRDB(Configs.DB_URL).getConn();

            dockDAO = new DockDAO(dbConn);
            lockDAO = new LockDAO(dbConn);
            bikeDAO = new BikeDAO(dbConn);

            currentRental = new Rental();

            paymentInfoValidationStrategy = new CardValidationStrategy();
            priceCalculatingStrategy = new Pricing();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static DataStore getInstance(){
        if(DataStore.instance == null){
            synchronized (DataStore.class){
                if(DataStore.instance == null){
                    DataStore.instance = new DataStore();
                    instance.loadSingletonData();
                }
            }
        }
        return DataStore.instance;
    }

    private void loadSingletonData(){
        try {
            bikeList = FXCollections.observableArrayList(bikeDAO.findAll());
            dockList = FXCollections.observableArrayList(dockDAO.findAll());

            bikeImages = new HashMap<>(3);
            File file = new File(Configs.IMAGE_PATH + "/bike/STANDARD_BIKE.png");
            bikeImages.put("STANDARD_BIKE", new Image(file.toURI().toString()));
            file = new File(Configs.IMAGE_PATH + "/bike/STANDARD_EBIKE.png");
            bikeImages.put("STANDARD_EBIKE", new Image(file.toURI().toString()));
            file = new File(Configs.IMAGE_PATH + "/bike/TWIN_BIKE.png");
            bikeImages.put("TWIN_BIKE", new Image(file.toURI().toString()));

        } catch (SQLException e) {
            System.err.println("Load singleton data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Bike getBikeById(int id) throws SQLException {
        for(Bike b : bikeList){
            if(b.getId() == id) return b;
        }
        return bikeDAO.findByID(id);
    }

    public Dock getDockById(int id) {
        for(Dock d : dockList){
            if(d.getId() == id) return d;
        }
        return null;
    }
}
