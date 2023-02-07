package edu.hust.vn.screen.dock;

import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

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
    public DockScreenHandler(String screenPath, Dock dock) throws IOException {
        super(screenPath);

        setScreenTitle("Dock Screen");

        this.dock = dock;
        ebrImage.setOnMouseClicked(e -> {
            try {
                HomeScreenHandler.getHomeScreenHandler().show();
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
            switch (bike.getType()){
                case STANDARD_BIKE:
                    standardBikeTbl.getItems().add(new DockBike(bike, lock));
                    break;
                case TWIN_BIKE:
                    standardTBikeTbl.getItems().add(new DockBike(bike, lock));
                    break;
                case STANDARD_EBIKE:
                    standardEBikeTbl.getItems().add(new DockEBike((StandardEBike) bike, lock));
                    break;
            }
        }
    }

    private void initTable(TableView tbl) {
        TableColumn<DockBike, String> licensePlateCol = new TableColumn<>("License Plate");
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        TableColumn<DockBike, String> barCodeCol = new TableColumn<>("Bar code");
        barCodeCol.setCellValueFactory(new PropertyValueFactory<>("barCode"));
        TableColumn<DockBike, Button> rentBikeCol = new TableColumn<>("Rent this bike");
        rentBikeCol.setCellValueFactory(new PropertyValueFactory<>("rentBike"));

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

    void requestToRentBike() throws SQLException, IOException {
        try {

        }catch (Exception e){

        }
    }

    @Override
    public void onShow() {
        availableLots.setText("Available lots: "+dock.getAvailableLots());
        availableBikes.setText("Available bikes: "+dock.getAvailableBikes());
    }
}
