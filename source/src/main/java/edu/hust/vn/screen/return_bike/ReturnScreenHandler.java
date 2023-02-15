package edu.hust.vn.screen.return_bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.common.exception.LockNotFreeException;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.controller.ViewDockController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
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
import javafx.collections.ListChangeListener;
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
    private FlowPane docksView;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView cancelReturnImg;

    private ObservableList<DockCardHandler> homeItems;
    private static ReturnScreenHandler instance;

    private ReturnScreenHandler() throws IOException {
        super(Configs.RETURN_SCREEN_PATH);

        homeItems = FXCollections.observableArrayList();

        ObservableList<Dock> dockList = DataStore.getInstance().dockList;

        for(Dock dock : dockList){
            addNewDockCard(dock);
        }

        // sync changes between DataStore dockList and homeItems/docksView
        dockList.addListener((ListChangeListener.Change<? extends Dock> c) -> {
            while (c.next()){
                if(c.wasAdded()){
                    c.getAddedSubList().forEach(dock -> {
                        addNewDockCard(dock);
                    });
                }
                if(c.wasRemoved()){
                    c.getRemoved().forEach(dock -> {
                        for(DockCardHandler dc : homeItems){
                            if(dc.getDockID() == dock.getId()){
                                homeItems.remove(dc);
                                docksView.getChildren().remove(dc);
                                break;
                            }
                        }
                    });
                }
            }
        });

        ebrImage.setOnMouseClicked(e -> {
            try {
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelReturnImg.setOnMouseClicked(e->{
            DataStore.getInstance().currentRental.setActive(true);
            getPrevScreenHandler().show();
        });
    }

    private void addNewDockCard(Dock dock) {
        try{
            DockCardHandler dockCardHandler = new DockCardHandler(dock, new EventHandler() {
                @Override
                public void handle(Event event) {
                    if (searchInput.getText().length() == 0){
                        try {
                            MessagePopup.getInstance().show("please enter bar code", false);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        selectLock(dock, searchInput.getText());
                    }
                }
            });
            this.homeItems.add(dockCardHandler);
            this.docksView.getChildren().add(dockCardHandler.getContent());
        }catch (IOException e){
            e.printStackTrace();
        }
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

    public void selectLock(Dock dock, String barCode){
        ReturnController returnController = (ReturnController) getBaseController();
        try{
            try{
                Lock lock = returnController.validateBarCode(dock, barCode);
                returnController.attachBikeToLock(lock);

                ReturnFormHandler returnFormHandler = new ReturnFormHandler();
                returnFormHandler.setBaseController(getBaseController());
                returnFormHandler.show();
            } catch (InvalidBarcodeException | IllegalArgumentException e) {
                MessagePopup.getInstance().show("Invalid bar code: "+barCode, true);
            } catch (BarCodeNotFoundException e){
                MessagePopup.getInstance().show("Bar code not found: "+barCode, false);
            } catch (LockNotFreeException e){
                MessagePopup.getInstance().show("The lock is already occupied: "+barCode, false);
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