package edu.hust.vn.model.dock;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.screen.return_bike.ReturnDockHandler;
import javafx.beans.property.*;
import javafx.scene.control.Button;

import java.io.IOException;

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
    protected ObjectProperty<Button> selectLock = new SimpleObjectProperty<>();
    public Lock(){ }

    public Button getSelectLock() {
        return selectLock.get();
    }

    public ObjectProperty<Button> selectLockProperty() {
        this.selectLock.set(new Button("Select"));
        this.selectLock.get().setOnMouseClicked( e->{
            try {
                ReturnDockHandler.getInstance(dock).selectLock(barCode.get());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return selectLock;
    }

    public void setSelectLock(Button selectLock) {
        this.selectLock.set(selectLock);
    }

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
            this.state = LOCK_STATE.RELEASED;
        }else{
            this.state = LOCK_STATE.LOCKED;
        }
        // khong no chay vao property ben dockcardhander truoc hien thi sai
        this.bike.set(bike);
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

    public void setState(LOCK_STATE state) {
        this.state = state;
    }

    @Override
    public String toString(){
        return "id = " + id + ", barCode = " + barCode + ", dockId = " + dockId + ", status = " + state;
    }
}
