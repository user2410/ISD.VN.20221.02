package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.controller.strategy.paymentinfo_validation.CardValidationStrategy;
import edu.hust.vn.controller.strategy.paymentinfo_validation.PaymentInfoValidationStrategy;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.invoice.Invoice;
import edu.hust.vn.model.rental.Rental;

import java.util.HashMap;
import java.util.Map;

public class RentBikeController extends BaseController implements PaymentInfoReceiverController{
    private Bike selectedBike;
    private Lock currentLock;
    private PaymentInfoValidationStrategy paymentInfoValidationStrategy;
    private HashMap<String, String> paymentInfo = new HashMap<>();

    public RentBikeController(){
        this.paymentInfo = new HashMap<>();
        paymentInfoValidationStrategy = new CardValidationStrategy();
    }

    public Invoice createInvoice(){
        Invoice invoice = new Invoice();
        Rental rental = new Rental();
        rental.setBike(this.selectedBike);
        rental.setLock(this.currentLock);
        invoice.setAmount((int) (this.selectedBike.getPrice()*0.4));
        return invoice;
    }

    public Bike getSelectedBike() {
        return selectedBike;
    }

    public void setSelectedBike(Bike selectedBike) {
        this.selectedBike = selectedBike;
    }

    public Lock getCurrentLock() {
        return currentLock;
    }

    public void setCurrentLock(Lock currentLock) {
        this.currentLock = currentLock;
    }

    @Override
    public Map<String, String> getPaymentInfo() {
        return paymentInfo;
    }

    @Override
    public void setPaymentInfo(String key, String value) {
        paymentInfo.put(key, value);
    }

    @Override
    public void validatePaymentInfo() throws InvalidPaymentInfoException {
        paymentInfoValidationStrategy.validate(this.paymentInfo);
    }
    
    public void rentBike() throws Exception{
        PaymentController paymentController = new PaymentController(paymentInfo);
        paymentController.payRental((int) (-selectedBike.getPrice()*0.4));
        // remove bike from lock from
        // - local instance
        currentLock.setBike(null);
        selectedBike.setLock(null);
        // - database
//        DataStore.getInstance().lockDAO.takeBike(currentLock.getId());

        // set current rental instance
        Rental rental = DataStore.getInstance().currentRental;
        rental.startRenting(currentLock, selectedBike);
    }
}
