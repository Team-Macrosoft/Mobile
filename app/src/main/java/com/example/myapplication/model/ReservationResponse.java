package com.example.myapplication.model;


public class ReservationResponse {
    private String message;
    private Reservation data;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Reservation getData() {
        return data;
    }

    public void setData(Reservation data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
