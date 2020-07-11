package com.javagreen.controller;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
