package edu.hust.vn.screen.return_form;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.ReturnController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.payment.PaymentCreditCard;
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
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private Label bikeType;

    @FXML
    private TextField cardExpDate;

    @FXML
    private Label bikeLicensePlate;

    @FXML
    private Label bikeTotalTime;

    @FXML
    private Label bikeRentalFee;

    @FXML
    private Label bikeDeposit;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label subTotal;


    public ReturnFormHandler() throws IOException {
        super(Configs.RETURN_INVOICE_SCREEN_PATH);

        Rental currentRental =  DataStore.getInstance().currentRental;
        Bike bike = currentRental.getBike();

        bikeImg.setImage(DataStore.getInstance().bikeImages.get(bike.typeAsString()));

        bikeLicensePlate.setText(bike.getLicensePlate());
        bikeType.setText(bike.typeAsString());
        bikeTotalTime.setText(Utils.convertSecondsToTimeFormat(currentRental.getTotalTime()));

        bikeRentalFee.setText(DataStore.getInstance().rentalFeeCalculatingStrategy.getPricing( (int) currentRental.getTotalTime() ) + Configs.CURRENCY);
        int deposit = DataStore.getInstance().depositCalculatingStrategy.getDeposit(bike.getPrice());
        bikeDeposit.setText(deposit + Configs.CURRENCY);
        int rentalFee = DataStore.getInstance().rentalFeeCalculatingStrategy.getPricing((int)currentRental.getTotalTime());
        int amount = rentalFee - deposit;
        if(amount >= 0){
            subTotalLabel.setText("Payment:");
            subTotalLabel.setTextFill(Paint.valueOf("RED"));
            subTotal.setText(amount + Configs.CURRENCY);
            subTotal.setTextFill(Paint.valueOf("RED"));
        }else{
            subTotalLabel.setText("Refund:");
            subTotalLabel.setTextFill(Paint.valueOf("GREEN"));
            subTotal.setText((-amount) + Configs.CURRENCY);
            subTotal.setTextFill(Paint.valueOf("GREEN"));
        }

        confirmBtn.setOnMouseClicked( e->{
            Map<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("cardOwner", cardOwner.getText().trim());
            paymentInfo.put("cardCode", cardNumber.getText().trim());
            paymentInfo.put("expDate", cardExpDate.getText().trim());
            paymentInfo.put("cvvCode", cardCvv.getText().trim());

            try {
                PaymentCreditCard paymentCreditCard = new PaymentCreditCard();
                paymentCreditCard.createPaymentEntity(paymentInfo);
                getBaseController().setPaymentMethod(paymentCreditCard);
                getBaseController().returnBike();

                HomeScreenHandler.getInstance().show();
                MessagePopup.getInstance().show("Return success", false);
            } catch (Exception ex) {
                try {
                    MessagePopup.getInstance().show("Return failed: " + ex.getMessage(), true);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
    }

    @Override
    public void onShow() {
    }

    @Override
    public ReturnController getBaseController(){
        return (ReturnController)super.getBaseController();
    }
}
