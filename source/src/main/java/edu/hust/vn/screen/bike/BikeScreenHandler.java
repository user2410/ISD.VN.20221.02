package edu.hust.vn.screen.bike;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.bike.TwinBike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.DockDAO;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.dock.DockScreenHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.payment.PaymentFormHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.screen.popup.Popup;
import edu.hust.vn.screen.return_bike.ReturnScreenHandler;
import edu.hust.vn.utils.Configs;
import edu.hust.vn.utils.Utils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

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
    @FXML
    private Label fee;

    private int timeCounter;
    private static ObservableMap<Bike, BikeScreenHandler> bikeScreens = FXCollections.observableHashMap();

    private Bike bike;

    private BikeScreenHandler(Bike bike) throws IOException {
        super(Configs.BIKE_SCREEN_PATH);
        this.bike = bike;

        setScreenTitle("Bike screen");

        ebrImage.setOnMouseClicked(e->{
            try {
                getBaseController().updateData();
                HomeScreenHandler.getInstance().show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
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

        ObjectProperty<Bike> rentedBike = Rental.getInstance().bikeProperty();
        rentalFeeLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> (rentedBike.get() != null && rentedBike.get().equals(this.bike)), rentedBike));
        timeCtl.visibleProperty().bind(Bindings.createBooleanBinding(() -> (rentedBike.get() != null && rentedBike.get().equals(this.bike)), rentedBike));
        actionBtn.textProperty().bind(Bindings.createStringBinding(() -> {
            if(rentedBike.get() != null && rentedBike.get().equals(this.bike)){
                actionBtn.setStyle("-fx-background-color: red");
                actionBtn.setStyle("-fx-text-fill: white");
                actionBtn.setOnAction(null);
                actionBtn.removeEventHandler(MouseEvent.MOUSE_CLICKED, requestToRentBike);
                actionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToReturnBike);
                return "Return this bike";
            }else {
                actionBtn.setStyle("-fx-background-color: white");
                actionBtn.setStyle("-fx-text-fill: black");
                actionBtn.setOnAction(null);
                actionBtn.removeEventHandler(MouseEvent.MOUSE_CLICKED, requestToReturnBike);
                actionBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToRentBike);
                actionBtn.setDisable(rentedBike.get() != null);
                return "Rent this bike";
            }
        }, rentedBike));

        timeCounter = 0;
        setBaseController(new ReturnController());
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
            setBaseController(new RentBikeController());

            Rental rental = Rental.getInstance();
            rental.setBike(bike);
            rental.setDock(bike.getLock().getDock());
            rental.setStartTime(LocalDateTime.now());

            Lock lock = bike.getLock();

//            lock.setBike(null);
//            bike.setLock(null);

            try {
                DataStore.getInstance().putBike(lock.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            timeLine = new Timeline();
            fee.setText("0");
            rentTime.setText("0:0:0:0");
            stopBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, requestToStop);

            doTime();
        }
    };

    private EventHandler requestToReturnBike = new EventHandler() {
        @Override
        public void handle(Event event) {

            if ( timeLine.getStatus() == Animation.Status.STOPPED ){
                try {
                    MessagePopup.getInstance().show("Is stopped, run to return bike", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try{
//                timeLine.stop();
                    ReturnScreenHandler.getInstance().show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    };

    private Timeline timeLine;

    public Timeline getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(Timeline timeLine) {
        this.timeLine = timeLine;
    }

    private void doTime(){
        timeLine.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RentBikeController rentBikeController = (RentBikeController) getBaseController();

//                LocalDateTime to = LocalDateTime.now();
//                LocalDateTime fromTemp = LocalDateTime.from(Rental.getInstance().getStartTime());
                timeCounter++;
                int days = timeCounter / 86400;
                int hours = timeCounter % 86400 / 3600;
                int minutes = timeCounter % 86400 % 3600 / 60;
                int second = timeCounter % 86400 % 3600 % 60;
                rentTime.setText(days + ":" + hours + ":" + minutes + ":" + second);
                fee.setText(String.valueOf( rentBikeController.getPricing().getPricing( timeCounter ) ) );
            }
        });
        timeLine.getKeyFrames().add(frame);
        timeLine.playFromStart();
    }

    private EventHandler requestToStop = new EventHandler() {
        @Override
        public void handle(Event event) {

            if ( timeLine.getStatus() == Animation.Status.STOPPED ){
                File file = new File(Configs.IMAGE_PATH + "/icons/"+"pause_circle_icon"+".png");
                Image img = new Image(file.toURI().toString());
                stopBtn.setImage(img);
                timeLine.play();
            }else{
                File file = new File(Configs.IMAGE_PATH + "/icons/"+"run"+".png");
                Image img = new Image(file.toURI().toString());
                stopBtn.setImage(img);
                timeLine.stop();
            }
        }
    };
}
