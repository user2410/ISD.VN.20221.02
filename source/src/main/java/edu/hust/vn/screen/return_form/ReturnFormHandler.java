package edu.hust.vn.screen.return_form;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.model.rental.Rental;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.home.HomeScreenHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.utils.Configs;
import edu.hust.vn.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ReturnFormHandler extends BaseScreenHandler {

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cardOwner;

    @FXML
    private TextField cardCvv;

    @FXML
    private Button confirmBtn;

    @FXML
    private ImageView bikeImg;

    @FXML
    private Label bikeRentalFee;

    @FXML
    private Label bikeType;

    @FXML
    private TextField cardExpDate;

    @FXML
    private Label bikeLicensePlate;

    @FXML
    private Label bikeTotalTime;

    public ReturnFormHandler() throws IOException {
        super(Configs.INVOICE_SCREEN_PATH);

        Rental currentRental =  DataStore.getInstance().currentRental;

        bikeImg.setImage(DataStore.getInstance().bikeImages.get(currentRental.getBike().typeAsString()));

        bikeLicensePlate.setText(currentRental.getBike().getLicensePlate());
        bikeType.setText(currentRental.getBike().typeAsString());
        bikeRentalFee.setText(String.valueOf(DataStore.getInstance().rentalFeeCalculatingStrategy.getPricing( (int) currentRental.getTotalTime() )));

        bikeTotalTime.setText(Utils.convertSecondsToTimeFormat(currentRental.getTotalTime()));

        confirmBtn.setOnMouseClicked( e->{
            ReturnController ctl = (ReturnController) getBaseController();
            ctl.setPaymentInfo("cardOwner", cardOwner.getText());
            ctl.setPaymentInfo("cardNumber", cardNumber.getText());
            ctl.setPaymentInfo("expDate", cardExpDate.getText());
            ctl.setPaymentInfo("cvvCode", cardCvv.getText());

            try {
                ctl.validatePaymentInfo();
                ctl.returnBike();

                HomeScreenHandler.getInstance().show();
                MessagePopup.getInstance().show("Return success", false);
            } catch (InvalidPaymentInfoException | IOException ex) {
                try {
                    MessagePopup.getInstance().show(ex.getMessage(), true);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
    }

    @Override
    public void onShow() {
    }
}
