package com.greenjava.validationexception.excepton;

import java.util.Date;

public class ExceptionResponse {

    private String message;
    private Date date;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
