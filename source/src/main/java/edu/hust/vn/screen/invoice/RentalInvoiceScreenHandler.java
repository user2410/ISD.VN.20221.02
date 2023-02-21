package edu.hust.vn.screen.invoice;

import edu.hust.vn.DataStore;
import edu.hust.vn.controller.PaymentController;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.bike.StandardBike;
import edu.hust.vn.model.invoice.Invoice;
import edu.hust.vn.model.payment.CreditCard;
import edu.hust.vn.model.payment.PaymentEntity;
import edu.hust.vn.model.payment.PaymentMethod;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.bike.BikeScreenHandler;
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
import java.util.Map;

public class RentalInvoiceScreenHandler extends BaseScreenHandler {

    @FXML
    private ImageView bikeImg;

    @FXML
    private Label bikeLicensePlate;

    @FXML
    private Label bikeType;

    @FXML
    private Label bikeDeposit;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cardOwner;

    @FXML
    private TextField cardExpDate;

    @FXML
    private TextField cardCvv;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button changeBtn;

    public RentalInvoiceScreenHandler(RentBikeController ctl) throws IOException {
        super(Configs.RENTAL_INVOICE_SCREEN_PATH);

        setBaseController(ctl);

        Bike bike = ctl.getSelectedBike();

        bikeImg.setImage(DataStore.getInstance().bikeImages.get(bike.typeAsString()));

        bikeLicensePlate.setText(bike.getLicensePlate());
        bikeType.setText(bike.typeAsString());
        bikeDeposit.setText(DataStore.getInstance().depositCalculatingStrategy.getDeposit(bike.getPrice()) + Configs.CURRENCY);

        PaymentEntity paymentEntity = getBaseController().getPaymentMethod().getPaymentEntity();
        if(paymentEntity instanceof  CreditCard){
            CreditCard card = (CreditCard) getBaseController().getPaymentMethod().getPaymentEntity();
            cardNumber.setText(card.getCardCode());
            cardOwner.setText(card.getOwner());
            cardExpDate.setText(card.getExpDate());
            cardCvv.setText(card.getCvvCode());
        }

        cancelBtn.setOnMouseClicked(e -> {
            try {
                BikeScreenHandler.getInstance(bike).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        changeBtn.setOnMouseClicked(e -> {
            getPrevScreenHandler().show();
        });
    }

    @Override
    public void onShow() {

    }

    @FXML
    private void handleConfirmInvoice() {
        RentBikeController rentBikeController = ((RentBikeController)getBaseController());

        try{
            rentBikeController.rentBike();
            HomeScreenHandler.getInstance().show();
            MessagePopup.getInstance().show("Rental success", false);
        }catch (Exception e){
            e.printStackTrace();
            try {
                MessagePopup.getInstance().show("Rental failed: "+e.getMessage(), false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public RentBikeController getBaseController(){
        return (RentBikeController)super.getBaseController();
    }
}
