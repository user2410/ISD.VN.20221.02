package edu.hust.vn.screen.payment;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.BaseController;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.model.payment.PaymentCreditCard;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.invoice.RentalInvoiceScreenHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.utils.Configs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaymentFormHandler extends BaseScreenHandler {

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cardOwner;

    @FXML
    private TextField expDate;

    @FXML
    private TextField cardCvv;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;

    public PaymentFormHandler() throws IOException {
        super(Configs.PAYMENT_FORM_PATH);

        setScreenTitle("Payment screen");

        cancelBtn.setOnMouseClicked(e->{
            getPrevScreenHandler().show();
        });

        confirmBtn.setOnMouseClicked(e->{
            Map<String, String> paymentInfo = new HashMap<>();
            paymentInfo.put("cardOwner", cardOwner.getText().trim());
            paymentInfo.put("cardCode", cardNumber.getText().trim());
            paymentInfo.put("expDate", expDate.getText().trim());
            paymentInfo.put("cvvCode", cardCvv.getText().trim());

            try{
                PaymentCreditCard paymentCreditCard = new PaymentCreditCard();
                paymentCreditCard.createPaymentEntity(paymentInfo);
                getBaseController().setPaymentMethod(paymentCreditCard);
                RentalInvoiceScreenHandler rentalInvoiceScreen = new RentalInvoiceScreenHandler(getBaseController());
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

    @Override
    public RentBikeController getBaseController(){
        return (RentBikeController)super.getBaseController();
    }
}
