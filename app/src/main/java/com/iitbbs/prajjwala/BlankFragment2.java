package com.iitbbs.prajjwala;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BlankFragment2 extends Fragment {
    TextView consumerName, consumerID, consumerDob, consumerPhone, consumerAadhar, consumerCity, consumerState, consumerUsername, consumerRetailer;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_fragment2,container,false);
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        consumerName = (TextView) view.findViewById(R.id.consumer_name);
        consumerID = (TextView) view.findViewById(R.id.consumer_id);
        consumerDob = (TextView) view.findViewById(R.id.consumer_dob);
        consumerPhone = (TextView) view.findViewById(R.id.consumer_ph_number);
        consumerAadhar = (TextView) view.findViewById(R.id.consumer_aadhar_number);
        consumerCity = (TextView) view.findViewById(R.id.consumer_city);
        consumerState = (TextView) view.findViewById(R.id.consumer_state);
        consumerUsername = (TextView) view.findViewById(R.id.consumer_user_name);
        consumerRetailer = (TextView) view.findViewById(R.id.consumer_retailer_id);
        consumerName.setText(preferences.getString("consumerName", "consumer"));
        consumerID.setText(preferences.getString("consumerID", "consumerID"));
        consumerDob.setText(preferences.getString("consumerDob", "Date of Birth"));
        consumerPhone.setText(preferences.getString("consumerPhone", "Phone Number"));
        consumerAadhar.setText(preferences.getString("consumerAadhar", "Aadhar"));
        consumerCity.setText(preferences.getString("consumerCity", "Bhubaneswar"));
        consumerState.setText(preferences.getString("consumerState", "Odisha"));
        consumerUsername.setText(preferences.getString("UserName", "user name"));
        consumerRetailer.setText(preferences.getString("consumerRetailer", "retailer id"));
        return view;
    }
}
