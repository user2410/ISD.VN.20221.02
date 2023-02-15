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
    private ObjectProperty<LOCK_STATE> state = new SimpleObjectProperty<>();

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
        if(bike == null){
            this.state.set(LOCK_STATE.RELEASED);
        }else{
            this.state.set(LOCK_STATE.LOCKED);
        }
        this.bike.set(bike);
    }

    public ObjectProperty<LOCK_STATE> stateProperty() {
        return state;
    }

    public LOCK_STATE getState() {
        return state.get();
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
        return "id = " + id + ", barCode = " + barCode.get() + ", dockId = " + dockId + ", status = " + state.get();
    }
}
