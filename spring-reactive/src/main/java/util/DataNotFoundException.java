package com.mintgenie.mintgeniecore.commons.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() { }
    public DataNotFoundException(String message) {
        super(message);
    }
}
