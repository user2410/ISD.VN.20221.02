package edu.hust.vn.model.dock;

import edu.hust.vn.model.bike.Bike;
import javafx.beans.property.*;

public class Lock {
    private int id;
    private StringProperty barCode = new SimpleStringProperty();
    private ObjectProperty<Bike> bike = new SimpleObjectProperty<>();
    private int dockId;
    private Dock dock;
    public enum LOCK_STATE{
        RELEASED, LOCKED
    }
    private LOCK_STATE state;

    public Lock(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringProperty barCodeProperty() {
        return barCode;
    }

    public String getBarCode() {
        return barCode.get();
    }

    public void setBarCode(String barCode) {
        this.barCode.set(barCode);
    }

    public int getDockId() {
        return dockId;
    }

    public Dock getDock() {
        return dock;
    }

    void setDock(Dock dock) {
        this.dock = dock;
    }

    public void setDockId(int dock) {
        this.dockId = dock;
    }

    public ObjectProperty<Bike> bikeProperty() {
        return bike;
    }

    public Bike getBike() {
        return bike.get();
    }

    public void setBike(Bike bike) {
        this.bike.set(bike);
        if(bike == null){
            this.state = LOCK_STATE.RELEASED;
        }else{
            this.state = LOCK_STATE.LOCKED;
        }
    }

    public LOCK_STATE getState() {
        return state;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Lock){
            if(((Lock)o).getId() == this.id){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "id = " + id + ", barCode = " + barCode + ", dockId = " + dockId + ", status = " + state;
    }
}
