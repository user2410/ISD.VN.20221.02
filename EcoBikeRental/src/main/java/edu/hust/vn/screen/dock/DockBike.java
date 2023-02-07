package edu.hust.vn.screen.dock;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class DockBike {
    protected Bike bike;
    protected Lock lock;
    protected ObjectProperty<Button> rentBike = new SimpleObjectProperty<>();

    public DockBike(Bike bike, Lock lock) {
        this.bike = bike;
        this.lock = lock;
        this.rentBike.set(new Button("Rent"));
        this.rentBike.get().setOnMouseClicked(e->{
            // Start rent bike sequence
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

    public ObjectProperty<Button> rentBikeProperty(){
        return rentBike;
    }

    public Button getRentBike() {
        return rentBike.get();
    }
}
