package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.helper.Constant;
import com.example.myapplication.model.MemberReservationListResponse;
import com.example.myapplication.model.Reservation;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserReservationFragment extends Fragment {

    private LocalDataManager manager = LocalDataManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_reservation, container, false);

        ArrayList<String> reservationStringList = new ArrayList<>();

        List<List<String>> listOfLists = new ArrayList<List<String>>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Constant.url + "/api/reservations";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                MemberReservationListResponse reservationResponse = gson.fromJson(response.toString(), MemberReservationListResponse.class);

                ArrayList<Reservation> reservationArrayList = reservationResponse.getReservationList();

                List<Map<String, String>> data = new ArrayList<Map<String, String>>();



                if (reservationArrayList != null && reservationArrayList.size()!=0){

                    for (Reservation reservation : reservationArrayList
                    ) {

                        String startDateFromArray = reservation.getStartDate()[0]
                                + "-" + reservation.getStartDate()[1]
                                + "-" + reservation.getStartDate()[2]
                                + " " + reservation.getStartDate()[3]
                                + ":0" + reservation.getStartDate()[4];

                        String endDateFromArray = reservation.getEndDate()[0]
                                + "-" + reservation.getEndDate()[1]
                                + "-" + reservation.getEndDate()[2]
                                + " " + reservation.getEndDate()[3]
                                + ":0" + reservation.getEndDate()[4];

                        String reservationStringLine1 = "";
                        String reservationStringLine2 = "";


                        reservationStringLine1 +=  "Parking spot: " +reservation.getParkingSpot().getId()+" "
                                + "Total Price: "+reservation.getTotalPrice();

                        reservationStringLine2 += "Start Date: " +startDateFromArray+" / "
                                +"End Date: " +endDateFromArray;

                        Map<String, String> datum = new HashMap<String, String>(2);
                        datum.put("First Line", reservationStringLine1);
                        datum.put("Second Line",reservationStringLine2);
                        data.add(datum);



                    }

                    SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                            android.R.layout.simple_list_item_2,
                            new String[] {"First Line", "Second Line" },
                            new int[] {android.R.id.text1, android.R.id.text2 });

                    ListView listView = (ListView) view.findViewById(R.id.userReservationList);
                    listView.setAdapter(adapter);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = manager.getSharedPreference(getContext(),"token","");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);


        return view;
    }
}