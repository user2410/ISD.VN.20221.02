package edu.hust.vn.screen.return_form;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.PaymentInfoReceiverController;
import edu.hust.vn.controller.RentBikeController;
import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.screen.invoice.RentalInvoiceScreenHandler;
import edu.hust.vn.screen.popup.MessagePopup;
import edu.hust.vn.utils.Configs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ReturnFormHandler extends BaseScreenHandler {

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

    public ReturnFormHandler() throws IOException {
        super(Configs.PAYMENT_FORM_PATH);

        setScreenTitle("Payment screen");

        cancelBtn.setOnMouseClicked(e->{
            getPrevScreenHandler().show();
        });

        confirmBtn.setOnMouseClicked(e->{
            PaymentInfoReceiverController ctl = (PaymentInfoReceiverController) getBaseController();
            ctl.setPaymentInfo("cardOwner", cardOwner.getText());
            ctl.setPaymentInfo("cardNumber", cardNumber.getText());
            ctl.setPaymentInfo("expDate", expDate.getText());
            ctl.setPaymentInfo("cvvCode", cardCvv.getText());

            try{
//                ctl.validatePaymentInfo();
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
