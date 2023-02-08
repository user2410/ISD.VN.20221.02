package edu.hust.vn.screen.payment;

import edu.hust.vn.screen.BaseScreenHandler;
import edu.hust.vn.utils.Configs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    }

    @Override
    public void onShow() {
    }
}
