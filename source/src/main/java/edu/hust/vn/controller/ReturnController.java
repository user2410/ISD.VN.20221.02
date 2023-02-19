package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.common.exception.LockNotFreeException;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.payment.PaymentMethod;
import edu.hust.vn.model.rental.Rental;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReturnController extends BaseController{
    private PaymentMethod paymentMethod;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Lock validateBarCode(Dock dock, String barCode) throws
        InvalidBarcodeException, BarCodeNotFoundException, LockNotFreeException, IllegalArgumentException {
        if(!UUID.fromString(barCode).toString().equals(barCode)){
            throw new InvalidBarcodeException();
        }
        Lock lock = dock.getLockByBarCode(barCode);
        if(lock == null){
            throw new BarCodeNotFoundException();
        }
        if(lock.getState() == Lock.LOCK_STATE.LOCKED){
            throw new LockNotFreeException(lock.getBarCode());
        }
        return lock;
    }

    public void attachBikeToLock(Lock lock) throws SQLException {
        Bike rentedBike = DataStore.getInstance().currentRental.getBike();
        // put the bike back to the selected dock
        // - local
        lock.setBike(rentedBike);
        rentedBike.setLock(lock);
        // - database
        DataStore.getInstance().lockDAO.attachBike(lock.getId(), rentedBike.getId());
    }

    public void returnBike(){
        Rental currentRental = DataStore.getInstance().currentRental;
        Bike rentedBike = currentRental.getBike();
        int deposit = DataStore.getInstance().depositCalculatingStrategy.getDeposit(rentedBike.getPrice());
        int rentalFee = DataStore.getInstance().rentalFeeCalculatingStrategy.getPricing((int)currentRental.getTotalTime());
        int amount = rentalFee - deposit;
        PaymentController paymentController = new PaymentController(paymentMethod.getPaymentEntity());
        if(amount >= 0){
            paymentController.payRental(amount);
        }else{
            paymentController.refund(-amount);
        }
        // clear current rental
        currentRental.clear();

    }
}