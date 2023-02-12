package edu.hust.vn.screen.payment;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.PaymentInfoReceiverController;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.model.bike.StandardBike;
import edu.hust.vn.model.bike.StandardEBike;
import edu.hust.vn.model.bike.TwinBike;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
import edu.hust.vn.screen.invoice.RentalInvoiceScreenHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.utils.Configs;
import edu.hust.vn.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.text.html.ImageView;
import java.awt.dnd.DropTarget;
import java.io.IOException;

public class PaymentFormHandler extends BaseScreenHandler {

    @FXML
    private ImageView bikeImg;

    @FXML
    private TextField cardCvv;

    @FXML
    private Label rentalFee;

    @FXML
    private Label bikeType;

    @FXML
    private TextField cardOwner;

    @FXML
    private TextField cardExpDate;

    @FXML
    private Button confirmBtn;

    @FXML
    private Label bikeLicensePlate;

    @FXML
    private TextField cardNumber;

    @FXML
    private Label totalTime;

    @FXML
    private TextField expDate;

    @FXML
    private Button cancelBtn;


    public PaymentFormHandler() throws IOException {
        super(Configs.PAYMENT_FORM_PATH);

        setScreenTitle("Payment screen");

        cancelBtn.setOnMouseClicked(e->{
            getPrevScreenHandler().show();
        });

        Rental currentRental = DataStore.getInstance().currentRental;
        bikeLicensePlate.setText(currentRental.getBike().getLicensePlate());
        if ( currentRental.getBike() instanceof StandardBike){
            bikeType.setText("Standard Bike");
        } else if (currentRental.getBike() instanceof StandardEBike) {
            bikeType.setText("StandardEBike");
        } else if (currentRental.getBike() instanceof TwinBike) {
            bikeType.setText("TwinBike");
        }
        RentBikeController rentBikeController = (RentBikeController) BikeScreenHandler.getInstance(currentRental.getBike()).getBaseController();
        rentalFee.setText(String.valueOf(rentBikeController.getPricing().getPricing( (int) currentRental.getTotalTime() )));
        totalTime.setText(Utils.convertSecondsToTimeFormat(currentRental.getTotalTime()));

        confirmBtn.setOnMouseClicked(e->{
            PaymentInfoReceiverController ctl = (PaymentInfoReceiverController) getBaseController();
            ctl.setPaymentInfo("cardOwner", cardOwner.getText());
            ctl.setPaymentInfo("cardNumber", cardNumber.getText());
            ctl.setPaymentInfo("expDate", expDate.getText());
            ctl.setPaymentInfo("cvvCode", cardCvv.getText());

            try{
                ctl.validatePaymentInfo();
                RentalInvoiceScreenHandler rentalInvoiceScreen = new RentalInvoiceScreenHandler((RentBikeController) ctl);
                rentalInvoiceScreen.setPrevScreenHandler(this);
                rentalInvoiceScreen.show();
            }catch ( InvalidPaymentInfoException | IOException ex){
                try {
                    MessagePopup.getInstance().show(ex.getMessage(), true);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onShow() {
    }
}