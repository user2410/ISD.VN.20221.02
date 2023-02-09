package edu.hust.vn.controller;

import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.strategy.paymentinfo_validation.CardValidationStrategy;
import edu.hust.vn.controller.strategy.paymentinfo_validation.PaymentInfoValidationStrategy;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;

import java.util.HashMap;

public class RentBikeController extends BaseController implements PaymentInfoReceiverController{
    private Bike selectedBike;
    private Dock currentDock;
    private PaymentInfoValidationStrategy paymentInfoValidationStrategy;
    private HashMap<String, String> paymentInfo = new HashMap<>();

    public RentBikeController(){
        this.paymentInfo = new HashMap<>();
        paymentInfoValidationStrategy = new CardValidationStrategy();
    }

    public Bike getSelectedBike() {
        return selectedBike;
    }

    public void setSelectedBike(Bike selectedBike) {
        this.selectedBike = selectedBike;
    }

    public Dock getCurrentDock() {
        return currentDock;
    }

    public void setCurrentDock(Dock currentDock) {
        this.currentDock = currentDock;
    }

    @Override
    public void setPaymentInfo(String key, String value) {
        paymentInfo.put(key, value);
    }

    @Override
    public void validatePaymentInfo() throws InvalidPaymentInfoException {
        paymentInfoValidationStrategy.validate(this.paymentInfo);
    }

    @Override
    public void submitPaymentInfo() {
        System.out.println("Submit payment info");
    }
}
