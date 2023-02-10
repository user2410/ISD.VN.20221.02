package edu.hust.vn.screen.return_bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.sql.SQLException;
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

    private static ReturnScreenHandler instance;
    private ObservableList<ReturnDockCardHandler> homeItems;

    private ReturnScreenHandler() throws IOException {
        super(Configs.HOME_SCREEN_PATH);
        titleLabel.setText("Return Bike");

        homeItems = FXCollections.observableArrayList();

        ReturnController returnctl = new ReturnController();
        setBaseController(returnctl);

        ObservableList<Dock> dockList = DataStore.getInstance().dockList;

        try{
            for(Dock dock : dockList){
                ReturnDockCardHandler returnDockCardHandler = ReturnDockCardHandler.getInstance(dock);
                this.homeItems.add(returnDockCardHandler);
                docksView.getChildren().add(returnDockCardHandler.getContent());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        ebrImage.setOnMouseClicked(e -> {
            try {
                getBaseController().updateData();
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        ObjectProperty<Bike> rentedBike = Rental.getInstance().bikeProperty();
        rentedBikeImg.visibleProperty().bind(Bindings.createBooleanBinding(() -> (rentedBike.get() != null), rentedBike));
        rentedBikeLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> (rentedBike.get() != null), rentedBike));

        rentedBikeImg.setOnMouseClicked(e -> {
            try {
                BikeScreenHandler.getInstance(Rental.getInstance().getBike()).show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() == 0){
                // reset dock view
                for(int i=0; i<homeItems.size(); i++){
                    ReturnDockCardHandler dc = homeItems.get(i);
                    boolean old = dc.getShow();
                    dc.setShow(true);
                    if(!old) docksView.getChildren().add(i, dc.getContent());
                }
                return;
            }
            ArrayList<Dock> res = ((ReturnController)this.getBaseController()).searchDockList(newValue);
            docksView.getChildren().clear();
            for(ReturnDockCardHandler dc : homeItems){
                boolean inRes = false;
                for(Dock d : res){
                    if(d.getId() == dc.getDockID()){
                        docksView.getChildren().add(dc.getContent());
                        inRes = true;
                        break;
                    }
                }
                dc.setShow(inRes);
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
}

