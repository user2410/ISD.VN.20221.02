package edu.hust.vn.screen.dock_card;

import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.FXMLScreenHandler;
import edu.hust.vn.screen.dock.DockScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DockCardHandler extends FXMLScreenHandler {

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

    private static ObservableMap<Dock, DockCardHandler> dockCards = FXCollections.observableHashMap();
    private Dock dock;

    public BooleanProperty show = new SimpleBooleanProperty();

    public DockCardHandler(Dock dock, EventHandler selectDockBtnHandler) throws IOException {
        super(Configs.DOCK_CARD_PATH);
        this.dock = dock;
        show.set(true);
        setDockInfo();
        selectDockBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, selectDockBtnHandler);
    }

    private void setDockInfo() {
        dockName.textProperty().bind(dock.nameProperty());
        dockAddress.textProperty().bind(dock.addressProperty());
        dockArea.textProperty().bind(dock.areaProperty().asString());
        dockCapacity.textProperty().bind(dock.capacityProperty().asString());
        dockAvailableLots.setText(String.valueOf(dock.getAvailableLots()));
        dockAvailableBikes.setText(String.valueOf(dock.getAvailableBikes()));
        dock.getLocks().forEach(lock -> {
            lock.stateProperty().addListener((observable -> {
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
