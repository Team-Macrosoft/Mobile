package com.example.myapplication.helper;

import com.example.myapplication.model.ParkingSpot;
import com.example.myapplication.model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArrayUtil
{
    public static ArrayList<ParkingSpot> convertParkingSpot(JSONArray jArr)
    {
        ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                ParkingSpot parkingSpot = ParkingSpot.convertFromJSONToMyClass((JSONObject) jArr.get(i));

                list.add(parkingSpot);
            }
        } catch (JSONException e) {}

        return list;
    }

    public static ArrayList<Reservation> convertReservation(JSONArray jArr)
    {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                Reservation reservation = Reservation.convertFromJSONToMyClass((JSONObject) jArr.get(i));

                reservations.add(reservation);
            }
        } catch (JSONException e) {}

        return reservations;
    }


    public static JSONArray convertParkingSpot(Collection<Object> list)
    {
        return new JSONArray(list);
    }

}