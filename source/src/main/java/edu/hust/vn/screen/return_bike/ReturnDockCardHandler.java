package edu.hust.vn.screen.return_bike;

import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.FXMLScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.payment.PaymentFormHandler;
import edu.hust.vn.utils.Configs;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ReturnDockCardHandler extends FXMLScreenHandler {

    @FXML
    private Label dockName;

    @FXML
    private Label dockAddress;

    @FXML
    private Label dockArea;

    @FXML
    private Label dockCapacity;

    @FXML
    private Label dockAvailableLots;

    @FXML
    private Label dockAvailableBikes;

    @FXML
    private Button selectDockBtn;

    private static ObservableMap<Dock, ReturnDockCardHandler> dockCards = FXCollections.observableHashMap();
    private Dock dock;

    public BooleanProperty show = new SimpleBooleanProperty();

    private ReturnDockCardHandler(Dock dock) throws IOException {
        super(Configs.DOCK_CARD_PATH);
        this.dock = dock;
        show.set(true);
        setDockInfo();
        selectDockBtn.setText("Select");
        selectDockBtn.setOnMouseClicked(e -> {
            try {
                ReturnDockHandler.getInstance(this.dock).show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static ReturnDockCardHandler getInstance(Dock dock) throws IOException {
        if(!dockCards.containsKey(dock)){
            synchronized (ReturnDockCardHandler.class){
                if(!dockCards.containsKey(dock)){
                    dockCards.put(dock, new ReturnDockCardHandler(dock));
                }
            }
        }
        return dockCards.get(dock);
    }

    private void setDockInfo() {
        dockName.textProperty().bind(dock.nameProperty());
        dockAddress.textProperty().bind(dock.addressProperty());
        dockArea.textProperty().bind(dock.areaProperty().asString());
        dockCapacity.textProperty().bind(dock.capacityProperty().asString());
        dockAvailableLots.setText(String.valueOf(dock.getAvailableLots()));
        dockAvailableBikes.setText(String.valueOf(dock.getAvailableBikes()));
        dock.getLocks().forEach(lock -> {
            lock.bikeProperty().addListener((observable -> {
                dockAvailableLots.setText(String.valueOf(dock.getAvailableLots()));
                dockAvailableBikes.setText(String.valueOf(dock.getAvailableBikes()));
            }));
        });
    }

    public int getDockID(){
        return dock.getId();
    }

    public boolean getShow(){
        return show.get();
    }

    public void setShow(boolean val){
        show.set(val);
    }
}
