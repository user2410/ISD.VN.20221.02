package edu.hust.vn.screen.dock;

import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.controller.ViewDockController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardBike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.bike.TwinBike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.factory.HomeScreenFactory;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.utils.Configs;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class DockScreenHandler extends BaseScreenHandler {

    @FXML
    private ImageView ebrImage;

    @FXML
    private Label dockTitle;

    @FXML
    private Label availableLots;

    @FXML
    private Label availableBikes;

    @FXML
    private Label address;

    @FXML
    private TextField barCodeInput;

    @FXML
    private Button barCodeSubmitBtn;

    @FXML
    private TableView<DockBike> standardBikeTbl;

    @FXML
    private TableView<DockEBike> standardEBikeTbl;

    @FXML
    private TableView<DockBike> standardTBikeTbl;

    private Dock dock;

    public DockScreenHandler(Dock dock) throws IOException {
        super(Configs.DOCK_SCREEN_PATH);

        setScreenTitle("Dock Screen");

        setBaseController(new ViewDockController());

        this.dock = dock;
        ebrImage.setOnMouseClicked(e -> {
            try {
                HomeScreenFactory.getInstance().show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        dockTitle.textProperty().bind(dock.nameProperty());
        address.textProperty().bind(dock.addressProperty());

        initTable(standardBikeTbl);
        initTable(standardEBikeTbl);
        initTable(standardTBikeTbl);

        ObservableList<Lock> locks = dock.getLocks();
        for(Lock lock : locks){
            if(lock.getState() == Lock.LOCK_STATE.RELEASED){
                continue;
            }
            Bike bike = lock.getBike();
            if(bike instanceof StandardBike){
                standardBikeTbl.getItems().add(new DockBike(this, bike, lock));
            } else if (bike instanceof StandardEBike) {
                standardEBikeTbl.getItems().add(new DockEBike(this, (StandardEBike) bike, lock));
            } else if (bike instanceof TwinBike) {
                standardTBikeTbl.getItems().add(new DockBike(this, bike, lock));
            }
        }

        barCodeSubmitBtn.setOnMouseClicked(e -> selectBike(barCodeInput.getText()));
    }

    private void initTable(TableView tbl) {
        TableColumn<DockBike, String> licensePlateCol = new TableColumn<>("License Plate");
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        TableColumn<DockBike, String> barCodeCol = new TableColumn<>("Bar code");
        barCodeCol.setCellValueFactory(new PropertyValueFactory<>("barCode"));
        TableColumn<DockBike, Button> rentBikeCol = new TableColumn<>("View this bike");
        rentBikeCol.setCellValueFactory(new PropertyValueFactory<>("selectBike"));

        if(tbl == standardEBikeTbl){
            TableColumn<DockEBike, Float> batteryLifeCol = new TableColumn<>("Battery life");
            batteryLifeCol.setCellValueFactory(new PropertyValueFactory<>("batteryLife"));
            TableColumn<DockEBike, Float> remainingBatteryLifeCol = new TableColumn<>("Remaining battery");
            remainingBatteryLifeCol.setCellValueFactory(new PropertyValueFactory<>("remainingBatteryLife"));
            tbl.getColumns().addAll(licensePlateCol, barCodeCol, batteryLifeCol, remainingBatteryLifeCol, rentBikeCol);

        }else{
            tbl.getColumns().addAll(licensePlateCol, barCodeCol, rentBikeCol);
        }
    }

    @Override
    public void onShow() {
        availableLots.setText("Available lots: "+dock.getAvailableLots());
        availableBikes.setText("Available bikes: "+dock.getAvailableBikes());
    }

    public void selectBike(String barCode){
        ViewDockController ctl = (ViewDockController) getBaseController();
        try{
            try{
                Lock lock = ctl.validateBarCode(this.dock, barCode);
                Bike bike = lock.getBike();
                if(bike == null){
                    new MessagePopup("No bike attached to Lock with bar code "+barCode, false).show();
                }else{
                    new BikeScreenHandler(bike).show();
                }
            } catch (InvalidBarcodeException e) {
                new MessagePopup("Invalid bar code: "+barCode, true).show();
            }
        } catch (IOException e){
            e.printStackTrace();
            try {
                new MessagePopup("System error "+e.getMessage(), true).show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
