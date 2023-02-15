package main.java.edu.hust.vn.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import main.java.edu.hust.vn.entity.Bike;
import main.java.edu.hust.vn.entity.Dock;
import main.java.edu.hust.vn.entity.Invoice;
import main.java.edu.hust.vn.entity.Rental;

import java.io.IOException;
import java.net.URL;
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
    Timeline timeLine;
    private int timeCounter = 0;
    private Bike bike;
    private Rental rental;
    private Invoice invoice;
    private ObservableList<Dock> dockList;

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
                System.out.println(dock.getName());
                dock.addBikeToDock(bike);
                // payment method
                // chuyen man hinh sang thanh toan hoa don kem du lieu bike
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Dock is not selected", ButtonType.YES);
            alert.show();
        }
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
                timeCounter++;
                int days = timeCounter / 86400;
                int hours = (timeCounter - days * 86400) / 3600;
                int minutes = (timeCounter - hours * 3600) % 60;
                timeLb.setText(String.valueOf(days) + "d:" + String.valueOf((hours)) + "h:" + String.valueOf((minutes)) + "m");

                minutes = timeCounter;
                if (minutes <= 10){
                    invoice.setTotal(0);
                } else if (minutes > 10 && minutes < 40 ) {
                    invoice.setTotal(10000);
                }else {
                    invoice.setTotal(10000 + (minutes - 40) / 15 * 3000 + 3000);
                }
                totalLb.setText(String.valueOf(invoice.getTotal()));
            }
        });
        timeLine.getKeyFrames().add(frame);
        timeLine.playFromStart();
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

    public ReturnController(Bike bike, Rental rental, Invoice invoice) {
        this.bike = bike;
        this.rental = rental;
        this.invoice = invoice;
        this.timeLine = new Timeline();
    }
}
