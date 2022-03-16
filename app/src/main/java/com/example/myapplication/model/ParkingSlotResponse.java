package com.example.myapplication.model;

import com.example.myapplication.helper.ArrayUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingSlotResponse {
    private String message;
    private ArrayList<ParkingSpot> parkingSpotList;
    private boolean success;

    public static ParkingSlotResponse convertFromJSONToMyClass(JSONObject json) throws JSONException {
        if (json == null) {
            return null;
        }
        ParkingSlotResponse result = new ParkingSlotResponse();
        result.message = (String) json.get("message");
        ArrayList<ParkingSpot> parkingSpotArrayList = ArrayUtil.convertParkingSpot((JSONArray) json.get("data"));
        result.parkingSpotList = parkingSpotArrayList;
        result.success = (boolean) json.get("success");
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ParkingSpot> getParkingSlotList() {
        return parkingSpotList;
    }

    public void setParkingSlotList(ArrayList<ParkingSpot> parkingSpotList) {
        this.parkingSpotList = parkingSpotList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
