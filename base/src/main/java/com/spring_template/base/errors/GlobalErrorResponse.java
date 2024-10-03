package com.spring_template.base.errors;

import org.springframework.http.HttpStatus;

public class GlobalErrorResponse {
    private int statusCode;
    private String message;
    private long timeStamp;

    public GlobalErrorResponse() {
    }

    public GlobalErrorResponse(int statusCode, String message, long timeStamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "GlobalErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
