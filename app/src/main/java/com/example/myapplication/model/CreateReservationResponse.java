package com.example.myapplication.model;

import com.example.myapplication.helper.ArrayUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateReservationResponse {
    private String message;
    private ArrayList<Reservation> reservationArrayList;
    private boolean success;

    public static CreateReservationResponse convertFromJSONToMyClass(JSONObject json) throws JSONException {
        if (json == null) {
            return null;
        }
        CreateReservationResponse result = new CreateReservationResponse();
        result.message = (String) json.get("message");
        ArrayList<Reservation> reservations = ArrayUtil.convertReservation((JSONArray) json.get("data"));
        result.reservationArrayList = reservations;
        result.success = (boolean) json.get("success");
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Reservation> getReservationArrayList() {
        return reservationArrayList;
    }

    public void setReservationArrayList(ArrayList<Reservation> reservationArrayList) {
        this.reservationArrayList = reservationArrayList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
