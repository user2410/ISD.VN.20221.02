package edu.hust.vn;

import edu.hust.vn.db.EBRDB;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.BikeDAO;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.DockDAO;
import edu.hust.vn.model.dock.LockDAO;
import edu.hust.vn.utils.Configs;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public class DataStore {
    private static DataStore instance;

    public Connection dbConn;
    public LockDAO lockDAO;
    public DockDAO dockDAO;
    public BikeDAO bikeDAO;

    public ObservableList<Dock> dockList;
    public ObservableList<Bike> bikeList;

    private DataStore(){
        try {
            dbConn = new EBRDB(Configs.DB_URL).getConn();

            dockDAO = new DockDAO(dbConn);
            lockDAO = new LockDAO(dbConn);
            bikeDAO = new BikeDAO(dbConn);

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
        } catch (SQLException e) {
            System.err.println("Load singleton data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Bike getBikeById(int id) {
        for(Bike b : bikeList){
            if(b.getId() == id) return b;
        }
        return null;
    }

    public Dock getDockById(int id) {
        for(Dock d : dockList){
            if(d.getId() == id) return d;
        }
        return null;
    }
}
