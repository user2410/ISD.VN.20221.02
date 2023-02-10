package edu.hust.vn.model.return_bike;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.rental.Rental;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.print.Doc;
import java.time.LocalDateTime;

public class Return {
    private static Return instance;

    private int id;
    private ObjectProperty<Dock> dock;

    private ObjectProperty<Lock> lock;

    private ObjectProperty<Rental> rental;
    private ObjectProperty<Bike> bike;

    private ObjectProperty<LocalDateTime> returnTime;

    private Return(){
        clear();
    }

    public static Return getInstance(){
        if(instance == null){
            synchronized (Rental.class){
                instance = new Return();
            }
        }
        return instance;
    }

    public void clear(){
        bike = new SimpleObjectProperty<>(null);
        rental = new SimpleObjectProperty<>(null);
        returnTime = new SimpleObjectProperty<>(null);
        lock = new SimpleObjectProperty<>(null);
        dock = new SimpleObjectProperty<>(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dock getDock() {
        return dock.get();
    }

    public ObjectProperty<Dock> dockProperty() {
        return dock;
    }

    public void setDock(Dock dock) {
        this.dock.set(dock);
    }

    public Lock getLock() {
        return lock.get();
    }

    public ObjectProperty<Lock> lockProperty() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock.set(lock);
    }

    public Rental getRental() {
        return rental.get();
    }

    public ObjectProperty<Rental> rentalProperty() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental.set(rental);
    }

    public Bike getBike() {
        return bike.get();
    }

    public ObjectProperty<Bike> bikeProperty() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike.set(bike);
    }

    public LocalDateTime getReturnTime() {
        return returnTime.get();
    }

    public ObjectProperty<LocalDateTime> returnTimeProperty() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime.set(returnTime);
    }
}
