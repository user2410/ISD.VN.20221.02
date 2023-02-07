package edu.hust.vn.screen.home;

import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.screen.FXMLScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    public DockCardHandler(String screenPath, Dock dock) throws IOException {
        super(screenPath);
        this.dock = dock;
        setDockInfo();
        selectDockBtn.setOnMouseClicked(e -> {

        });
    }

    private void setDockInfo() {
        dockName.setText(dock.getName());
        dockAddress.setText(dock.getAddress());
        dockArea.setText(String.valueOf(dock.getArea()));
        dockCapacity.setText(String.valueOf(dock.getCapacity()));
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
}
