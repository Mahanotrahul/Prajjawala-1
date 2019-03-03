package com.iitbbs.prajjwala;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BlankFragment extends Fragment {

    private int progressStatus = 100;
    private Handler handler = new Handler();
    private  Handler handlerr = new Handler();
    private double weight = 15.30;
    private  double weightchanged = 0.0;
    String finalWt;
    TextView gasLeft, gasUsed, conId, conName;
    FloatingActionButton callButton;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {Manifest.permission.CALL_PHONE };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank,container,false);
        final ProgressBar pg = (ProgressBar) view.findViewById(R.id.pb);
        super.onStart();
        progressStatus = 100;

        final TextView textView = (TextView) view. findViewById(R.id.tv5);
        gasLeft = (TextView) view. findViewById(R.id.tv11);
        gasUsed = (TextView) view.findViewById(R.id.tv15);
        conId = (TextView) view.findViewById(R.id.tv19);
        conName = (TextView) view.findViewById(R.id.tv20);
        callButton = (FloatingActionButton) view.findViewById(R.id.fab4);


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9583962928"));
                    startActivity(intent);
                }
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        conId.setText(preferences.getString("consumerID", ""));
        conName.setText(preferences.getString("consumerName", ""));
        try {
            String results = new ConnectionthreadUsage().execute().get();
            //Log.d("huhu", results);
            finalWt = results;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        double d = (Double.parseDouble(finalWt)/(15.2*1000))*100.0;
        DecimalFormat f = new DecimalFormat("##.00");
        textView.setText(f.format(d)+"%");
        pg.setProgress((int)d);
        gasLeft.setText(Double.toString(Double.parseDouble(finalWt)/1000.0)+" Kg");
        gasUsed.setText(Double.toString(14.00-Double.parseDouble(finalWt)/1000.0)+" Kg");
        return view;
    }

    private class ConnectionthreadUsage extends AsyncTask<String, Void, String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            try {

                String link = "https://vasitars.com/old/prajjawala/Consumer_Portal/APP/Get_latest_data_wifi.php";
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                String dataStream = URLEncoder.encode("CONSUMER_ID", "UTF-8")
                        + "=" + URLEncoder.encode(conId.getText().toString(), "UTF-8");
                wr.write(dataStream);
                wr.flush();
                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));


                String line = reader.readLine();

                Log.i("huhu", line);

                try {
                    JSONObject returned_values = new JSONObject(line);
                    String latestWt = returned_values.getString("Gas_Left");
                    return  latestWt;
                }
                catch (Exception e) {

                }



            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return "0";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != "incomplete!") {
            }
        }
    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        return;
                    }
                }
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9583962928")));
                break;
        }
    }



}





