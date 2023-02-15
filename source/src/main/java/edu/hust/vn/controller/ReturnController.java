package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.common.exception.LockNotFreeException;
import edu.hust.vn.common.exception.invalid_payment_info.InvalidPaymentInfoException;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.model.rental.Rental;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReturnController extends BaseController implements PaymentInfoReceiverController{
    private HashMap<String, String> paymentInfo = new HashMap<>();

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
        DataStore.getInstance().paymentInfoValidationStrategy.validate(this.paymentInfo);
    }

    public void attachBikeToLock(Lock lock){
        Bike rentedBike = DataStore.getInstance().currentRental.getBike();
        lock.setBike(rentedBike);
        rentedBike.setLock(lock);
    }

    public void returnBike(){
        Rental currentRental = DataStore.getInstance().currentRental;
        Bike rentedBike = currentRental.getBike();
        int deposit = DataStore.getInstance().depositCalculatingStrategy.getDeposit(rentedBike.getPrice());
        int rentalFee = DataStore.getInstance().rentalFeeCalculatingStrategy.getPricing((int)currentRental.getTotalTime());
        int amount = rentalFee - deposit;
        PaymentController paymentController = new PaymentController(paymentInfo);
        if(amount >= 0){
            paymentController.payRental(amount);
        }else{
            paymentController.refund(-amount);
        }
        // clear current rental
        currentRental.clear();
        // put the bike back to the selected dock
        // - in db
//        DataStore.getInstance()
    }
}