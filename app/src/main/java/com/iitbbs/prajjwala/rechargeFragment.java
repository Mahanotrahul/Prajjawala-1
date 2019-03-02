package com.iitbbs.prajjwala;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class rechargeFragment extends Fragment {
    private EditText consumer_id;
    private EditText phone_no;
    private Spinner  recharge_amt;
    private EditText aadhaar_no;
    private EditText password_recharge;
    private Integer recharge_value;
    private CheckBox checkBox;

    private Button recharge_but, reset_but;

    private String username, password;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge,container,false);
        recharge_amt = (Spinner) view.findViewById(R.id.spinner);
        aadhaar_no = (EditText) view.findViewById(R.id.aadhar);
        password_recharge = (EditText) view.findViewById(R.id.password_recharge);
        recharge_but = (Button) view.findViewById(R.id.recharge_button);
        reset_but = (Button) view.findViewById(R.id.reset_button);
        checkBox = (CheckBox) view.findViewById(R.id.show_hide_password);

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        username = preferences.getString("UserName", "");
        password = preferences.getString("Password", "");
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        recharge_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sanitizeInput()){
                    recharge();
                }
            }
        });

        reset_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password_recharge.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password_recharge.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        return view;
    }

    private boolean sanitizeInput(){
        if(aadhaar_no.getText().length()!=12) {
            aadhaar_no.setError("This is not a valid Aadhaar number");
            return false;
        }
        if(password_recharge.getText().length() < 6) {
            password_recharge.setError("The password is invalid!");
            return false;
        }
        switch (recharge_amt.getSelectedItem().toString()) {
            case "10/-":
                recharge_value = 10;
                break;
            case "20/-":
                recharge_value = 20;
                break;
            case "50/-":
                recharge_value = 50;
                break;
            case "100/-":
                recharge_value = 100;
                break;
            case "500/-":
                recharge_value = 500;
                break;
            default:
                recharge_value = 10;
                break;

        }
        return true;
    }

    private class Connectionthread extends AsyncTask<String, Void, String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            try {

                String link = "https://vasitars.com/old/prajjawala/Consumer_Portal/APP/LPG_Recharge_App.php";
                URL url = new URL(link);

                Log.i("huhu","here!");


                String data  = URLEncoder.encode("Username", "UTF-8")
                        + "=" + URLEncoder.encode(username, "UTF-8");
                Log.d("userCheck", username);

                data += "&" + URLEncoder.encode("Password", "UTF-8")
                        + "=" + URLEncoder.encode(password, "UTF-8");
                Log.d("checkPass", password);


                data += "&" + URLEncoder.encode("RECHARGE_AMOUNT", "UTF-8")
                        + "=" + URLEncoder.encode(recharge_value.toString(), "UTF-8");

                data += "&" + URLEncoder.encode("SUBMIT_CONSUMER_AADHAAR_NUMBER", "UTF-8")
                        + "=" + URLEncoder.encode(aadhaar_no.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("CONSUMER_PASSWORD", "UTF-8")
                        + "=" + URLEncoder.encode(password_recharge.getText().toString(), "UTF-8");

                Log.i("huhu",data);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.i("BHAINA","I am here!");

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                Log.i("BHAINA","I am here!");
                wr.flush();
                wr.close();
                Log.i("huhu","line is 1");
                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                //Log.i("BHAINA","I am here!");
                Log.i("huhu","line is 2");
                String line = reader.readLine();

                //Toast.makeText(getContext(),line,Toast.LENGTH_LONG).show();
                Log.i("huhu","line is "+line);
                Log.i("huhu",line);
                /*try {

                    JSONObject returned_values = new JSONObject(line);
                    String result_code = returned_values.getString("Login_Successfull");
                    Log.i("huhu",result_code);
                    if(result_code.equals("0")) {
                        //Toast.makeText(getApplicationContext(),"There was some error in the filled in details!",Toast.LENGTH_LONG).show();
                        //Log.i("huhu",result_code);
                        return "error!";
                    }

                    String error_code = returned_values.getString("submit_status");
                    if(error_code.equals("0")) {
                        //Toast.makeText(getActivity().getApplicationContext(),"There was some error in the filled in details!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Toast.makeText(getActivity().getApplicationContext(),"There was no error in the filled in details!",Toast.LENGTH_LONG).show();
                        Log.i("huhu","yay");
                    }
                    //Log.i("huhu",name_retailer);
                    //Toast.makeText(getApplicationContext(),"you are "+name_retailer,Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    line = reader.readLine();
                    Log.i("huhu",e.toString());
                }*/



            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return "incomplete!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != "incomplete!") {
            }
        }
    }

    private void recharge() {
        try {

            String output = new rechargeFragment.Connectionthread().execute().get();

            //Log.i("BHAINA",status);
        }
        catch(Exception e) {
            Log.i("abra",e.toString());
        }


    }

    private void reset(){
        consumer_id.setText("");

        phone_no.setText("");

        aadhaar_no.setText("");

        password_recharge.setText("");

        recharge_amt.setSelection(0);

        consumer_id.requestFocus();
    }
}