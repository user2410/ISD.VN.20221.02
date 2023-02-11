package edu.hust.vn.screen.dock;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class DockBike {
    protected Bike bike;
    protected Lock lock;
    protected ObjectProperty<Button> selectBike = new SimpleObjectProperty<>();

    public DockBike(DockScreenHandler dockScreen, Lock lock) {
        this.bike = lock.getBike();
        this.lock = lock;
        this.selectBike.set(new Button("Select"));
        this.selectBike.get().setOnMouseClicked(e->{
            dockScreen.selectBike(lock.getBarCode());
        });
    }

    public StringProperty licensePlateProperty(){
        return bike.licensePlateProperty();
    }

    public String getLicensePlate() {
        return bike.getLicensePlate();
    }

    public void setLicensePlate(String licensePlate) {
        this.bike.setLicensePlate(licensePlate);
    }

    public StringProperty barCodeProperty(){
        return lock.barCodeProperty();
    }

    public String getBarCode() {
        return lock.getBarCode();
    }

    public void setBarCode(String barCode){
        this.lock.setBarCode(barCode);
    }

    public StringProperty typeProperty(){ return new SimpleStringProperty(bike.typeAsString());}

    public ObjectProperty<Button> selectBikeProperty(){
        return selectBike;
    }

    public Button getSelectBike() {
        return selectBike.get();
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
