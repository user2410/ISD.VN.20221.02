package edu.hust.vn.common.exception;

public class LockNotFreeException extends EBRException{
    public LockNotFreeException(String barCode){
        super("The lock " + barCode + " is already occupied");
    }
}
