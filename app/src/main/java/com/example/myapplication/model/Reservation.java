package com.example.myapplication.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Reservation {
    private long id;
    private double totalPrice;
    private int[] startDate;
    private int[] endDate;
    private ApplicationUser applicationUser;
    private ParkingSpot parkingSpot;

    public static Reservation convertFromJSONToMyClass(JSONObject json) throws JSONException {
        if (json == null) {
            return null;
        }
        Reservation result = new Reservation();
        result.id = (int) json.get("id");
        result.totalPrice = (Double) json.get("totalPrice");
        result.startDate = (int[]) json.get("startDate");
        result.endDate = (int[]) json.get("endDate");
        result.applicationUser = (ApplicationUser) json.get("applicationUser");
        result.parkingSpot = (ParkingSpot) json.get("parkingSpot");

        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int[] getStartDate() {
        return startDate;
    }

    public void setStartDate(int[] startDate) {
        this.startDate = startDate;
    }

    public int[] getEndDate() {
        return endDate;
    }

    public void setEndDate(int[] endDate) {
        this.endDate = endDate;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}
