package edu.hust.vn.screen.dock;

import edu.hust.vn.common.exception.BarCodeNotFoundException;
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
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.utils.Configs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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

    private static ObservableMap<Dock, DockScreenHandler> dockScreens = FXCollections.observableHashMap();
    private Dock dock;

    private DockScreenHandler(Dock dock) throws IOException {
        super(Configs.DOCK_SCREEN_PATH);

        setScreenTitle("Dock Screen");

        setBaseController(new ViewDockController());

        this.dock = dock;
        ebrImage.setOnMouseClicked(e -> {
            try {
                HomeScreenHandler.getInstance().show();
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

    public static DockScreenHandler getInstance(Dock dock) throws IOException {
        if(!dockScreens.containsKey(dock)){
            synchronized (DockScreenHandler.class){
                if(!dockScreens.containsKey(dock)){
                    dockScreens.put(dock, new DockScreenHandler(dock));
                }
            }
        }
        return dockScreens.get(dock);
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
                BikeScreenHandler.getInstance(bike).show();
            } catch (InvalidBarcodeException | IllegalArgumentException e) {
                MessagePopup.getInstance().show("Invalid bar code: "+barCode, true);
            } catch (BarCodeNotFoundException e){
                MessagePopup.getInstance().show("Bar code not found: "+barCode, false);
            }
        } catch (IOException e){
            e.printStackTrace();
            try {
                MessagePopup.getInstance().show("System error "+e.getMessage(), true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
