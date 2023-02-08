package edu.hust.vn.screen.home;

import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.FXMLScreenHandler;
import edu.hust.vn.screen.dock.DockScreenHandler;
import edu.hust.vn.screen.factory.DockScreenFactory;
import edu.hust.vn.utils.Configs;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.beans.binding.Bindings;
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

    private Dock dock;

    public BooleanProperty show = new SimpleBooleanProperty();

    public DockCardHandler(Dock dock) throws IOException {
        super(Configs.DOCK_CARD_PATH);
        this.dock = dock;
        show.set(true);
        setDockInfo();
        selectDockBtn.setOnMouseClicked(e -> {
            try {
                DockScreenHandler dockScreen = DockScreenFactory.getInstance(this.dock);
                dockScreen.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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
