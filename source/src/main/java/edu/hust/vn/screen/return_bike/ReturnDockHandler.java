package edu.hust.vn.screen.return_bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.controller.PaymentController;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.controller.ViewDockController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.dock.DockBike;
import edu.hust.vn.screen.dock.DockScreenHandler;
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
import java.time.LocalDateTime;

public class ReturnDockHandler extends BaseScreenHandler {

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
    private TableView<LockHandler> barcodeTable;

    private static ObservableMap<Dock, ReturnDockHandler> dockScreens = FXCollections.observableHashMap();
    private Dock dock;

    private ReturnDockHandler(Dock dock) throws IOException {
        super(Configs.RETURN_SCREEN_PATH);

        setScreenTitle("Dock Screen");

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

        initTable(barcodeTable);

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
        TableColumn<LockHandler, String> barCode = new TableColumn<>("Bar Code");
        barCode.setCellValueFactory(new PropertyValueFactory<>("barCode"));
        TableColumn<LockHandler, Button> returnBikeCol = new TableColumn<>("Return");
        returnBikeCol.setCellValueFactory(new PropertyValueFactory<>("selectLock"));
        tbl.getColumns().addAll(barCode, returnBikeCol);

    }

    @Override
    public void onShow() {
        availableLots.setText("Available lots: "+dock.getAvailableLots());
        availableBikes.setText("Available bikes: "+dock.getAvailableBikes());

        ObservableList<LockHandler> list = barcodeTable.getItems();
        list.clear();
        for(Lock lock : dock.getLocks()){
            if(lock.getState() == Lock.LOCK_STATE.LOCKED){
                for(LockHandler lockHandler : list){
                    if(lockHandler.getLock().getId() == dock.getId()){
                        list.remove(lockHandler);
                        break;
                    }
                }
            }else{
                list.add(new LockHandler(this, lock));
            }
        }
    }

    public void selectLock(String barCode){
        ReturnController ctl = (ReturnController) getBaseController();
        try{
            try{
//                Lock lock = ctl.validateBarCode(this.dock, barCode);
                Rental currentRental =  DataStore.getInstance().currentRental;
                currentRental.setEndTime(LocalDateTime.now());
                currentRental.setActive(false);

                PaymentFormHandler paymentFormHandler = new PaymentFormHandler();
                paymentFormHandler.setPrevScreenHandler(BikeScreenHandler.getInstance(currentRental.getBike()));
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