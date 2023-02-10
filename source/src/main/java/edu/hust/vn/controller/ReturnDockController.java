package edu.hust.vn.controller;

import edu.hust.vn.common.exception.BarCodeNotFoundException;
import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;

import java.util.UUID;

public class ReturnDockController extends BaseController{
    public Lock validateBarCode(Dock dock, String barCode) throws InvalidBarcodeException {
        if(!UUID.fromString(barCode).toString().equals(barCode)){
            throw new InvalidBarcodeException();
        }
        Lock lock = dock.getLockByBarCode(barCode);
        if(lock == null){
            throw new BarCodeNotFoundException();
        }
        return lock;
    }
}
