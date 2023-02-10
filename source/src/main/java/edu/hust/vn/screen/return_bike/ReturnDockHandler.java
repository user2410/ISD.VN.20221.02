package edu.hust.vn.screen.return_bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.controller.ReturnDockController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.dock.DockBike;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.payment.PaymentFormHandler;
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
import java.sql.SQLException;

public class ReturnDockHandler extends BaseScreenHandler {

    @FXML
    private ImageView ebrImage;

    @FXML
    private ImageView rentedBikeImg;

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
    private TableView<Lock> barcodeTable;

    private static ObservableMap<Dock, ReturnDockHandler> dockScreens = FXCollections.observableHashMap();
    private Dock dock;

    private ReturnDockHandler(Dock dock) throws IOException {
        super(Configs.RETURN_DOCK_SCREEN_PATH);

        setScreenTitle("Dock Screen");

        setBaseController(new ReturnDockController());

        this.dock = dock;
        ebrImage.setOnMouseClicked(e -> {
            try {
                getBaseController().updateData();
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        rentedBikeImg.setOnMouseClicked(e -> {
            try {
                BikeScreenHandler.getInstance(Rental.getInstance().getBike()).show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        dockTitle.textProperty().bind(dock.nameProperty());
        address.textProperty().bind(dock.addressProperty());

        initTable(barcodeTable);

        ObservableList<Lock> locks = dock.getLocks();
        for(Lock lock : locks){
            if(lock.getState() == Lock.LOCK_STATE.LOCKED){
                continue;
            }
            lock.selectLockProperty();
            barcodeTable.getItems().add(lock);
        }

        barCodeSubmitBtn.setOnMouseClicked(e -> selectLock(barCodeInput.getText()));
    }

    public static ReturnDockHandler getInstance(Dock dock) throws IOException {
        if(!dockScreens.containsKey(dock)){
            synchronized (ReturnDockHandler.class){
                if(!dockScreens.containsKey(dock)){
                    dockScreens.put(dock, new ReturnDockHandler(dock));
                }
            }
        }
        return dockScreens.get(dock);
    }

    private void initTable(TableView tbl) {
        TableColumn<Lock, String> barCode = new TableColumn<>("Bar Code");
        barCode.setCellValueFactory(new PropertyValueFactory<>("barCode"));
        TableColumn<Lock, Button> returnBikeCol = new TableColumn<>("Return with barcode lock");
        returnBikeCol.setCellValueFactory(new PropertyValueFactory<>("selectLock"));
        tbl.getColumns().addAll(barCode, returnBikeCol);

    }

    @Override
    public void onShow() {
        availableLots.setText("Available lots: "+dock.getAvailableLots());
        availableBikes.setText("Available bikes: "+dock.getAvailableBikes());
    }

    public void selectLock(String barCode){
        ReturnDockController ctl = (ReturnDockController) getBaseController();
        try{
            try{
                Lock lock = ctl.validateBarCode(this.dock, barCode);
                try {
                    DataStore.getInstance().addBike(lock.getId(), Rental.getInstance().getBike().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                PaymentFormHandler paymentFormHandler = new PaymentFormHandler();
                BikeScreenHandler.getInstance(Rental.getInstance().getBike()).getTimeLine().stop();
                paymentFormHandler.show();
            } catch (InvalidBarcodeException e) {
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
