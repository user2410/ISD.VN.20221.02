package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.invoice.Invoice;
import edu.hust.vn.model.payment.PaymentMethod;
import edu.hust.vn.model.rental.Rental;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RentBikeController extends BaseController{
    private Bike selectedBike;
    private Lock currentLock;
    private PaymentMethod paymentMethod;

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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    
    public void rentBike() throws Exception{
        // set current rental instance
        Rental rental = new Rental();
        rental.setLock(currentLock);
        rental.setBike(selectedBike);
        rental.setStartTime(LocalDateTime.now());

        // execute transaction
        PaymentController paymentController = new PaymentController(paymentMethod.getPaymentEntity());
        int deposit = DataStore.getInstance().depositCalculatingStrategy.getDeposit(this.selectedBike.getPrice());
        paymentController.payDeposit(deposit);

        // save rental session to database
        rental.setId(DataStore.getInstance().rentalDAO.saveRental(rental));

        // save invoice to database
        Invoice invoice = new Invoice();
        invoice.setRental(rental);
        invoice.setAmount(DataStore.getInstance().depositCalculatingStrategy.getDeposit(this.selectedBike.getPrice()));
        invoice.setType(Invoice.TYPE.RENTAL);
        DataStore.getInstance().invoiceDAO.saveInvoice(invoice);

        // remove bike from lock from
        // - local instance
        currentLock.setBike(null);
        selectedBike.setLock(null);
        // - database
        DataStore.getInstance().lockDAO.takeBike(currentLock.getId());

        // start rental session at this point
        rental.startRenting();
        DataStore.getInstance().currentRental.assign(rental);
    }
}
