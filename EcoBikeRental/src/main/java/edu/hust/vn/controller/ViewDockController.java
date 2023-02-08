package edu.hust.vn.controller;

import edu.hust.vn.common.exception.InvalidBarcodeException;
import edu.hust.vn.model.dock.Dock;
import edu.hust.vn.model.dock.Lock;

public class ViewDockController extends BaseController{
    public Lock validateBarCode(Dock dock, String barCode) throws InvalidBarcodeException {
        Lock lock = dock.getLockByBarCode(barCode);
        if(lock == null){
            throw new InvalidBarcodeException();
        }
        return lock;
    }
}
