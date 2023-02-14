package edu.hust.vn.controller;

import edu.hust.vn.DataStore;
import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.BikeNotAvailableException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.common.exception.LockNotFreeException;
import edu.hust.vn.controller.strategy.pricing.IPricing;
import edu.hust.vn.controller.strategy.pricing.Pricing;
import edu.hust.vn.model.bike.Bike;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;

import java.util.ArrayList;
import java.util.UUID;

public class ReturnController extends BaseController {

    private IPricing priceCalculatingStrategy;
    private Bike returnedBike;
    private Dock returnedToDock;
    private Lock returnedToLock;

    public ReturnController(){
        priceCalculatingStrategy = new Pricing();
    }

    public IPricing getPriceCalculatingStrategy() {
        return priceCalculatingStrategy;
    }

    public void setPriceCalculatingStrategy(IPricing priceCalculatingStrategy) {
        this.priceCalculatingStrategy = priceCalculatingStrategy;
    }

    public Bike getReturnedBike() {
        return returnedBike;
    }

    public void setReturnedBike(Bike returnedBike) {
        this.returnedBike = returnedBike;
    }

    public Lock getReturnedToLock() {
        return returnedToLock;
    }

    public void setReturnedToLock(Lock returnedToLock) {
        this.returnedToLock = returnedToLock;
    }

    public Dock getReturnedToDock() {
        return returnedToDock;
    }

    public void setReturnedToDock(Dock returnedToDock) {
        this.returnedToDock = returnedToDock;
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
}