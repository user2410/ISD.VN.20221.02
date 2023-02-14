package edu.hust.vn.screen.return_bike;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.screen.dock.DockScreenHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class LockHandler {
    protected Lock lock;
    protected ObjectProperty<Button> selectLock = new SimpleObjectProperty<>();

    public LockHandler(ReturnDockHandler returnDockScreen, Lock lock) {
        this.lock = lock;
        this.selectLock.set(new Button("Return"));
        this.selectLock.get().setOnMouseClicked(e->{
            returnDockScreen.selectLock(lock.getBarCode());
        });
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

    public ObjectProperty<Button> selectBikeProperty(){
        return selectLock;
    }

    public Button getSelectBike() {
        return selectLock.get();
    }

    public Lock getLock() {
        return lock;
    }

    public Button getSelectLock() {
        return selectLock.get();
    }

    public ObjectProperty<Button> selectLockProperty() {
        return selectLock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
