package edu.hust.vn.screen.return_bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.dock.DockScreenHandler;
import edu.hust.vn.screen.dock_card.DockCardHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.payment.PaymentFormHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.screen.return_form.ReturnFormHandler;
import edu.hust.vn.utils.Configs;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReturnScreenHandler extends BaseScreenHandler {

    @FXML
    private ImageView ebrImage;

    @FXML
    private TextField searchInput;

    @FXML
    private ImageView rentedBikeImg;

    @FXML
    private Label rentedBikeLabel;

    @FXML
    private FlowPane docksView;

    @FXML
    private Label titleLabel;

    private ObservableList<DockCardHandler> homeItems;
    private static ReturnScreenHandler instance;

    private ReturnScreenHandler() throws IOException {
        super(Configs.RETURN_SCREEN_PATH);

        homeItems = FXCollections.observableArrayList();

        ObservableList<Dock> dockList = DataStore.getInstance().dockList;

        try{
            for(Dock dock : dockList){
                DockCardHandler returnDockCardHandler = new DockCardHandler(dock, new EventHandler<>() {
                    @Override
                    public void handle(Event event) {

                        try {
                            Rental currentRental = DataStore.getInstance().currentRental;
                            ReturnFormHandler returnFormHandler = new ReturnFormHandler();
                            returnFormHandler.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                this.homeItems.add(returnDockCardHandler);
                docksView.getChildren().add(returnDockCardHandler.getContent());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        ebrImage.setOnMouseClicked(e -> {
            try {
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        rentedBikeImg.setOnMouseClicked(e -> {
            try {
                BikeScreenHandler.getInstance(DataStore.getInstance().currentRental.getBike()).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static ReturnScreenHandler getInstance() throws IOException {
        if(instance == null){
            synchronized (ReturnScreenHandler.class){
                if(instance == null){
                    instance = new ReturnScreenHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public void onShow() {
        docksView.requestFocus();
    }

    public void selectLock(String barCode){
        ReturnController ctl = (ReturnController) getBaseController();
        try{
            try{
                Rental currentRental =  DataStore.getInstance().currentRental;
                currentRental.setEndTime(LocalDateTime.now());
//                currentRental.setActive(false);

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