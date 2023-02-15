package edu.hust.vn.common.exception.subsystem.interbank;

public class UnrecognizedException extends RuntimeException {
    public UnrecognizedException() {
        super("ERROR: Something went wrong!");
    }
}
