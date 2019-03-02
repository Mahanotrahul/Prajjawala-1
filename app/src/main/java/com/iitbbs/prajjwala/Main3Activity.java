package com.iitbbs.prajjwala;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Handler;

public class Main3Activity extends AppCompatActivity {



    public EditText username, password;
    public Button loginb;
    boolean success = false;
    public TextView forgot_password;
    CheckBox show_password;
    ImageView image;

    String retailerName = null;
    String retailerId = null;

    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = (EditText) findViewById(R.id.username_login);
        password = (EditText) findViewById(R.id.password_login);
        loginb = (Button) findViewById(R.id.login_button);
        show_password = (CheckBox) findViewById(R.id.show_hide_password);
        forgot_password = (TextView) findViewById(R.id.forgotPassword);
        image = (ImageView) findViewById(R.id.logo);
        Bitmap d = ((BitmapDrawable)image.getDrawable()).getBitmap();
        int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
        image.setImageBitmap(scaled);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPass();

            }
        });

        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputSanityCheck()) {
                    verifyUser();
                }
            }
        });

    }

    private boolean inputSanityCheck() {
        if(username.getText().length() == 0) {
            username.setError("This field cannot be empty");
            return false;
        }
        if(password.getText().length() < 6) {
            password.setError("Too few characters entered");
            return false;
        }
        return true;
    }

    private void verifyUser() {
        try {
                Log.d("Connected", "Yeah");
            String output = new Connectionthread().execute().get();
                if(!success) {
                    Toast.makeText(this,"Incorrect username or password!",Toast.LENGTH_LONG).show();
                }


        }
        catch(Exception e) {
            Log.i("",e.toString());
        }

    }

    public void forgotPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setHint("Email addresss");
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                try {
                    String returned = new ForgotPasswordConnection().execute().get();

                }
                catch(Exception e) {
                    Log.i("",e.toString());
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }





    private class ForgotPasswordConnection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            try {

                String link = "https://vasitars.com/old/prajjawala/APP/forgot_password.php";
                URL url = new URL(link);
                @SuppressLint("WrongThread") String user = username.getText().toString();
                @SuppressLint("WrongThread") String passcode = password.getText().toString();

                String emailId = m_Text.toString();

                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(m_Text, "UTF-8");


                Log.d("here", data);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));


                String line = reader.readLine();

                Log.i("huhu", line);
                try {

                    JSONObject returned_values = new JSONObject(line);
                    String result_code = returned_values.getString("Login_Successfull");
                    Log.i("huhu", result_code);
                    if (result_code.equals("1")) {
                        success = true;
                    }

                    String name_retailer = returned_values.getString("RETAILER_NAME");
                    return name_retailer;
                    //Log.i("huhu",name_retailer);
                    //Toast.makeText(getApplicationContext(),"you are "+name_retailer,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    line = reader.readLine();
                    Log.i("huhu", line);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return "incomplete!";

        }
    }





    private class Connectionthread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            try {

                String link = "https://vasitars.com/old/prajjawala/APP/connect.php";
                URL url = new URL(link);
                String user = username.getText().toString();
                String passcode = password.getText().toString();

                String data = URLEncoder.encode("Username", "UTF-8")
                        + "=" + URLEncoder.encode(user, "UTF-8");

                data += "&" + URLEncoder.encode("Password", "UTF-8")
                        + "=" + URLEncoder.encode(passcode, "UTF-8");

                Log.d("here", data);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));


                String line = reader.readLine();

                Log.i("huhu", line);
                try {

                    JSONObject returned_values = new JSONObject(line);
                    String result_code = returned_values.getString("Login_Successfull");
                    Log.i("huhu", result_code);
                    if (result_code.equals("1")) {
                        success = true;
                    }

                    String name_retailer = returned_values.getString("RETAILER_NAME");
                    return name_retailer;
                    //Log.i("huhu",name_retailer);
                    //Toast.makeText(getApplicationContext(),"you are "+name_retailer,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    line = reader.readLine();
                    Log.i("huhu", line);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return "incomplete!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(success) {
                success = false;
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("UserName", username.getText().toString());
                editor.putString("Password", password.getText().toString());
                editor.commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else {

            }
        }
    }
}
