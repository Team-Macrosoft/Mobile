package com.example.myapplication.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ParkingSpot {
    private int id;
    private Double price;
    private int coordinate;

    public static ParkingSpot convertFromJSONToMyClass(JSONObject json) throws JSONException {
        if (json == null) {
            return null;
        }
        ParkingSpot result = new ParkingSpot();
        result.id = (int) json.get("parkingSpotId");
        result.price = (Double) json.get("price");
        result.coordinate = (int) json.get("coordinate");
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }
}
