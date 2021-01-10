package com.reactive;

public class CustomException extends Throwable {

    private String message;

    public CustomException() {
    }

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(Throwable e) {
        this.message=e.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
