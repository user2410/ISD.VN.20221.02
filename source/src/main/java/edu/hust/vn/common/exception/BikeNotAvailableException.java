package edu.hust.vn.common.exception;

public class BikeNotAvailableException extends EBRException{
    public BikeNotAvailableException(String barCode){
        super("There is no bike attached to lock "+barCode);
    }
}
