package edu.hust.vn.screen.bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.controller.ViewBikeController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.bike.TwinBike;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.payment.PaymentFormHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.screen.return_bike.ReturnScreenHandler;
import edu.hust.vn.utils.Configs;
import edu.hust.vn.utils.Utils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
    private Label rentTime;

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

    private static final Image pauseImage;
    private static final Image resumeImage;
    static {
        File file = new File(Configs.IMAGE_PATH+"/icons/pause_circle_icon.png");
        pauseImage = new Image(file.toURI().toString());
        file = new File(Configs.IMAGE_PATH+"/icons/play_circle_icon.png");
        resumeImage = new Image(file.toURI().toString());
    }

    private static Timeline timeline;
    static{
        timeline = new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                    System.out.println("time event");
                    Rental rental = DataStore.getInstance().currentRental;
                    if(rental.getBike() == null || !rental.isActive()) return;
                    rental.setTotalTime(rental.getTotalTime()+1);
                })
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }

    private BikeScreenHandler(Bike bike) throws IOException {
        super(Configs.BIKE_SCREEN_PATH);
        this.bike = bike;

        ViewBikeController ctl = new ViewBikeController();
        setBaseController(ctl);

        setScreenTitle("Bike screen");

        ebrImage.setOnMouseClicked(e->{
            try {
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bikeImage.setImage(DataStore.getInstance().bikeImages.get(bike.typeAsString()));

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

        Rental currentRental = DataStore.getInstance().currentRental;
        ObjectProperty<Bike> rentedBikeProp = currentRental.bikeProperty();

        rentalFeeLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> rentedBikeProp.get() == this.bike, rentedBikeProp));

        rentalFee.visibleProperty().bind(Bindings.createBooleanBinding(() -> rentedBikeProp.get() == this.bike, rentedBikeProp));
        rentalFee.textProperty().bind(Bindings.createStringBinding(
            ()->String.valueOf(DataStore.getInstance().priceCalculatingStrategy.getPricing((int) currentRental.getTotalTime())),
            currentRental.totalTimeProperty()));

        rentTime.visibleProperty().bind(Bindings.createBooleanBinding(() -> rentedBikeProp.get() == this.bike, rentedBikeProp));
        rentTime.textProperty().bind(Bindings.createStringBinding(()-> Utils.convertSecondsToTimeFormat(currentRental.getTotalTime()), currentRental.totalTimeProperty()));

        stopBtn.imageProperty().bind(Bindings.createObjectBinding(() -> (currentRental.isActive() ? pauseImage : resumeImage), currentRental.activeProperty()));
        stopBtn.setOnMouseClicked(e->{
            currentRental.setActive(!currentRental.isActive());
        });

        timeCtl.visibleProperty().bind(Bindings.createBooleanBinding(() -> rentedBikeProp.get() == this.bike, rentedBikeProp));
        actionBtn.textProperty().bind(Bindings.createStringBinding(() -> {
            if(rentedBikeProp.get() == this.bike){
                actionBtn.setStyle("-fx-background-color: red");
                actionBtn.setStyle("-fx-text-fill: white");
                actionBtn.setOnAction(null);
                actionBtn.removeEventHandler(MouseEvent.MOUSE_CLICKED, requestToRentBike);
                actionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToReturnBike);
                return "Return this bike";
            }
            actionBtn.setStyle("-fx-background-color: white");
            actionBtn.setStyle("-fx-text-fill: black");
            actionBtn.setOnAction(null);
            actionBtn.removeEventHandler(MouseEvent.MOUSE_CLICKED, requestToReturnBike);
            actionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToRentBike);
            actionBtn.setDisable(rentedBikeProp.get() != null);
            return "Rent this bike";
        }, rentedBikeProp));
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
                RentBikeController rentBikeController = new RentBikeController();
                rentBikeController.setSelectedBike(bike);
                rentBikeController.setCurrentLock(bike.getLock());

                PaymentFormHandler paymentFormHandler = new PaymentFormHandler();
                paymentFormHandler.setPrevScreenHandler(getInstance(bike));
                paymentFormHandler.setBaseController(rentBikeController);
                paymentFormHandler.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    };

    private EventHandler requestToReturnBike = new EventHandler() {
        @Override
        public void handle(Event event) {
            Rental currentRental = DataStore.getInstance().currentRental;
            currentRental.setActive(false);
            currentRental.setEndTime(LocalDateTime.now());
            try{
                ReturnController rc = new ReturnController();
                ReturnScreenHandler returnScreenHandler = ReturnScreenHandler.getInstance();
                returnScreenHandler.setBaseController(rc);
                returnScreenHandler.setPrevScreenHandler(BikeScreenHandler.getInstance(currentRental.getBike()));
                returnScreenHandler.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    };
}
