package edu.hust.vn.controller;


import edu.hust.vn.contract.IPricing;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import edu.hust.vn.entity.Bike;
import edu.hust.vn.entity.Dock;
import edu.hust.vn.entity.Invoice;
import edu.hust.vn.entity.Rental;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReturnController extends BaseController implements Initializable {

    @FXML
    private TableView<Dock> table;
    @FXML
    private TableColumn<Dock, String> nameCol;
    @FXML
    private TableColumn<Dock, Number> availableLotsCol;
    @FXML
    private TableColumn<Dock, Number> availableBikeCol;
    @FXML
    private TableColumn<Dock, Number> totalLotsCol;

    @FXML
    private TableColumn<Dock, String> addressCol;

    @FXML
    private Label licenseStateLb;

    @FXML
    private Label totalLb;

    @FXML
    private Label batteryLb;
    @FXML
    private Label timeLb;

    @FXML
    private Label typeLb;

    @FXML
    void returnBike(ActionEvent event) throws IOException {

        ObservableList<Dock> selected = table.getSelectionModel().getSelectedItems();
        if (!selected.isEmpty()){
            Dock dock = selected.get(0);
            if(dock.isFull()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Dock is full", ButtonType.YES);
                alert.show();
            }else {
                timeLine.stop();
                dock.addBikeToDock(this.bike);
                rental.setReturnTime(new Timestamp(System.currentTimeMillis()));
                int time = (int) (rental.getReturnTime().getTime() - rental.getStartTime().getTime()) / 1000;
                invoice.setTotal( pricing.getPricing(time) );
                // payment(Invoice invoice)
                // chuyen man hinh sang thanh toan hoa don kem du lieu bike
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Dock is not selected", ButtonType.YES);
            alert.show();
        }
    }

    private Timeline timeLine;
    private Bike bike;
    private Rental rental;
    private Invoice invoice;
    private ObservableList<Dock> dockList;
    private IPricing pricing;
    public ReturnController(Bike bike, Rental rental, Invoice invoice, IPricing pricing) {
        this.bike = bike;
        this.rental = rental;
        this.invoice = invoice;
        this.pricing = pricing;
        this.timeLine = new Timeline();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeLb.setText("Single");
        licenseStateLb.setText(bike.getLicensePlate());
        timeLb.setText(String.valueOf(0) + "d:" + String.valueOf((0)) + "h:" + String.valueOf((0)) + "m");
        totalLb.setText(String.valueOf(invoice.getTotal()));
        batteryLb.setText(String.valueOf(bike.getBattery()));

        dockList = FXCollections.observableArrayList(
                new Dock(1, "1 hoang mai ha noi", "So 1", 50),
                new Dock(2, "2 hoang mai ha noi", "So 2", 50),
                new Dock(3, "3 hoang mai ha noi", "S0 3", 50)
        );

        nameCol.setCellValueFactory(new PropertyValueFactory< Dock, String >("name"));
        availableBikeCol.setCellValueFactory( cellDataFeatures ->  cellDataFeatures.getValue().getAvailableBikeProperty());
        availableLotsCol.setCellValueFactory(cellDataFeatures ->  cellDataFeatures.getValue().getAvailableLotProperty());
        totalLotsCol.setCellValueFactory(new PropertyValueFactory< Dock, Number>( "capacity"));
        addressCol.setCellValueFactory(new PropertyValueFactory< Dock, String >("address"));
        table.setItems(dockList);

        doTime();
    }
    public Bike getBike() {
        return bike;
    }

    public Rental getRental() {
        return rental;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    private void doTime(){
        timeLine.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int time = (int) (System.currentTimeMillis() - rental.getStartTime().getTime()) / 1000;
                int days = time / 86400;
                int hours = time % 86400 / 3600;
                int minutes = time % 86400 % 3600 / 60;
                int second = time % 86400 % 3600 % 60;
                timeLb.setText( days + "d:" + hours + "h:" + minutes + "m:" + second + "s");
                totalLb.setText(String.valueOf( pricing.getPricing( time ) ) );
            }
        });
        timeLine.getKeyFrames().add(frame);
        timeLine.playFromStart();
    }
}
