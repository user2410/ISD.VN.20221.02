package edu.hust.vn.screen.dock;

import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.BikeNotAvailableException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.controller.ViewDockController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenFactory;
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
    private TableView<DockBike> bikeTbl;

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

        TableColumn<DockBike, String> licensePlateCol = new TableColumn<>("License Plate");
        licensePlateCol.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        TableColumn<DockBike, String> barCodeCol = new TableColumn<>("Bar code");
        barCodeCol.setCellValueFactory(new PropertyValueFactory<>("barCode"));
        TableColumn<DockBike, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<DockBike, Button> rentBikeCol = new TableColumn<>("View this bike");
        rentBikeCol.setCellValueFactory(new PropertyValueFactory<>("selectBike"));
        bikeTbl.getColumns().addAll(licensePlateCol, barCodeCol, typeCol, rentBikeCol);

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

    @Override
    public void onShow() {
        availableLots.setText("Available lots: "+dock.getAvailableLots());
        availableBikes.setText("Available bikes: "+dock.getAvailableBikes());
        ObservableList<DockBike> list = bikeTbl.getItems();
        list.clear();
        for(Lock lock : dock.getLocks()){
            if(lock.getState() == Lock.LOCK_STATE.RELEASED){
                for(DockBike dockBike : list){
                    if(dockBike.getLock().getId() == dock.getId()){
                        list.remove(dockBike);
                        break;
                    }
                }
            }else{
                list.add(new DockBike(this, lock));
            }
        }
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
            } catch (BikeNotAvailableException e){
                MessagePopup.getInstance().show("There is no bike attached to lock "+barCode, false);
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
