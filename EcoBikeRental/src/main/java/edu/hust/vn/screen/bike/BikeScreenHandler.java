package edu.hust.vn.screen.bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.bike.TwinBike;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.payment.PaymentFormHandler;
import edu.hust.vn.utils.Configs;
import edu.hust.vn.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;

public class BikeScreenHandler extends BaseScreenHandler {

    @FXML
    private ImageView ebrImage;

    @FXML
    private ImageView bikeImage;

    @FXML
    private Label licensePlate;

    @FXML
    private Label type;

    @FXML
    private Label batteryLifeLabel;

    @FXML
    private Label batteryLife;

    @FXML
    private Label batteryPercentageLabel;

    @FXML
    private Label batteryPercentage;

    @FXML
    private Label price;

    @FXML
    private Label deposit;

    @FXML
    private Label rentalFeeLabel;

    @FXML
    private Label rentalFee;

    @FXML
    private ImageView stopBtn;

    @FXML
    private Label rentTimeBtn;

    @FXML
    private Label nPedals;

    @FXML
    private Label nSaddles;

    @FXML
    private Label nRearSeats;

    @FXML
    private HBox timeCtl;

    @FXML
    private Button actionBtn;

    private static ObservableMap<Bike, BikeScreenHandler> bikeScreens = FXCollections.observableHashMap();

    private Bike bike;

    private BikeScreenHandler(Bike bike) throws IOException {
        super(Configs.BIKE_SCREEN_PATH);
        this.bike = bike;

        setScreenTitle("Bike screen");

        ebrImage.setOnMouseClicked(e->{
            try {
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

//        System.out.println(pathname);
        File file = new File(Configs.IMAGE_PATH + "/bike/"+bike.typeAsString()+".png");
        Image img = new Image(file.toURI().toString());
        bikeImage.setImage(img);

        licensePlate.textProperty().bind(bike.licensePlateProperty());
        type.setText(bike.typeAsString());
        price.textProperty().bind(Bindings.createStringBinding(() -> bike.priceProperty().get() +Configs.CURRENCY, bike.priceProperty()));
        deposit.textProperty().bind(Bindings.createStringBinding(() -> Utils.round(bike.priceProperty().get() * 0.4, 2) +Configs.CURRENCY, bike.priceProperty()));
        if(bike instanceof StandardEBike){
            StandardEBike ebike = (StandardEBike)bike;
            batteryLifeLabel.setVisible(true);
            batteryPercentageLabel.setVisible(true);
            batteryLife.textProperty().bind(ebike.batteryLifeProperty().asString());
            batteryPercentage.textProperty().bind(Bindings.createStringBinding(() -> Utils.round(ebike.remainingBatteryLifeProperty().get() / ebike.batteryLifeProperty().get() * 100.0, 2) +" %",
                ebike.batteryLifeProperty(), ebike.remainingBatteryLifeProperty()));
        }else{
            batteryLifeLabel.setVisible(false);
            batteryPercentageLabel.setVisible(false);
        }
        nSaddles.setText(String.valueOf(bike.getnSaddles()));
        nPedals.setText(String.valueOf(bike.getnPedals()));
        if(bike instanceof TwinBike){
            nRearSeats.setText(String.valueOf(((TwinBike)bike).getnRearSeats()));
        }

        ObjectProperty<Bike> rentedBike = DataStore.getInstance().rentedBike;
        rentalFeeLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> rentedBike.get() != null, rentedBike));
        timeCtl.visibleProperty().bind(Bindings.createBooleanBinding(() -> rentedBike.get() != null, rentedBike));
        actionBtn.textProperty().bind(Bindings.createStringBinding(() -> {
            if(rentedBike.get() == this.bike){
                actionBtn.setStyle("-fx-background-color: red");
                actionBtn.setStyle("-fx-text-fill: white");
                actionBtn.setOnAction(null);
                actionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToRenturnBike);
                return "Renturn this bike";
            }
            actionBtn.setStyle("-fx-background-color: white");
            actionBtn.setStyle("-fx-text-fill: black");
            actionBtn.setOnAction(null);
            actionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToRentBike);
            actionBtn.setDisable(rentedBike.get() != null);
            return "Rent this bike";
        }, rentedBike));
    }

    public static BikeScreenHandler getInstance(Bike bike) throws IOException {
        if(!bikeScreens.containsKey(bike)){
            synchronized (BikeScreenHandler.class){
                if(!bikeScreens.containsKey(bike)){
                    bikeScreens.put(bike, new BikeScreenHandler(bike));
                }
            }
        }
        return bikeScreens.get(bike);
    }

    @Override
    public void onShow() {}

    private EventHandler requestToRentBike = new EventHandler() {
        @Override
        public void handle(Event event) {
            try{
//                RentBikeController rentBikeController = new RentBikeController();
                System.out.println("rent bike");
                
                Rental rental = Rental.getInstance();
                rental.setBike(bike);
                rental.setDock(bike.getLock().getDock());

                PaymentFormHandler paymentFormHandler = new PaymentFormHandler();
                paymentFormHandler.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    };

    private EventHandler requestToRenturnBike = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.out.println("renturn bike");
        }
    };
}
