package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class UserInformationFragment extends Fragment {




   private TextView firstname,surname,email,returnFirstname,returnSurname,returnEmail,username,returnUsername;
   private LocalDataManager manager = LocalDataManager.getInstance();



    public UserInformationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstname = (TextView) view.findViewById(R.id.fragmentUserInformationFirstName);
        surname = (TextView) view.findViewById(R.id.fragmentUserInformationSurname);
        email = (TextView) view.findViewById(R.id.fragmentUserInformationEmail);
        username = (TextView) view.findViewById(R.id.fragmentUserInformationUsername);
        returnFirstname = (TextView) view.findViewById(R.id.txtReturnFirstname);
        returnSurname =(TextView) view.findViewById(R.id.txtReturnSurname);
        returnEmail = (TextView) view.findViewById(R.id.txtReturnEmail);
        returnUsername = (TextView) view.findViewById(R.id.txtReturnUsername);


        String txtfirstname = manager.getSharedPreference(getContext(),"firstName","");
        returnFirstname.setText(txtfirstname);

        String txtsurname = manager.getSharedPreference(getContext(),"surName","");
        returnSurname.setText(txtsurname);

        String email = manager.getSharedPreference(getContext(),"email","");
        returnEmail.setText(email);

        String username = manager.getSharedPreference(getContext(),"userName","");
        returnUsername.setText(username);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}