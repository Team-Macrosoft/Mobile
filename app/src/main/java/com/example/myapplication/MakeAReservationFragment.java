package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MakeAReservationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      // View view = inflater.inflate(R.layout.fragment_make_a_reservation,container,false);
        String tutorials[]
                = { "Algorithms", "Data Structures",
                "Languages", "Interview Corner",
                "GATE", "ISRO CS",
                "UGC NET CS", "CS Subjects",
                "Web Technologies" ,
                "Languages", "Interview Corner",
                "GATE", "ISRO CS",
                "UGC NET CS", "CS Subjects",
                "Web Technologies",
                "Languages", "Interview Corner",
                "GATE", "ISRO CS",
                "UGC NET CS", "CS Subjects",
                "Web Technologies"};

//        ListView listView = (ListView) view.findViewById(R.id.list);
//        ArrayAdapter<String>  arr = new ArrayAdapter<String>(getActivity(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item ,tutorials);
//        listView.setAdapter(arr);

        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}