package edu.hust.vn.controller;

import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.BikeNotAvailableException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;
import edu.hust.vn.utils.Utils;

import java.util.UUID;

public class ViewDockController extends BaseController{
    public Lock validateBarCode(Dock dock, String barCode) throws
        InvalidBarcodeException, BarCodeNotFoundException, BikeNotAvailableException {
        if(!Utils.validateBarcode(barCode)){
            throw new InvalidBarcodeException();
        }
        Lock lock = dock.getLockByBarCode(barCode);
        if(lock == null){
            throw new BarCodeNotFoundException();
        }
        if(lock.getState() == Lock.LOCK_STATE.RELEASED){
            throw new BikeNotAvailableException(lock.getBarCode());
        }
        return lock;
    }
}
