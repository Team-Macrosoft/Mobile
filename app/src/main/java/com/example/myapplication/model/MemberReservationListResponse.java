package com.example.myapplication.model;


import java.util.ArrayList;
import java.util.List;

public class MemberReservationListResponse {
    private String message;
    private ArrayList<Reservation> data;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Reservation> getReservationList() {
        return data;
    }

    public void setReservationList(ArrayList<Reservation> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
