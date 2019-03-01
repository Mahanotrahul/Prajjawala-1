package com.iitbbs.prajjwala;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestingConnection extends AppCompatActivity {
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_connection);

        new JsonTask().execute("http://168.87.87.213:8080/davc/m2m/HPE_IoT/9c65f9fffe218a69/DownlinkPayloadStatus/latest");


    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(TestingConnection.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("content-type", "application/vnd.onem2m-res+json;ty=4;");
                connection.setRequestProperty("x-m2m-origin", "C73773960-b9dff09a");
                connection.setRequestProperty("x-m2m-ri", "9900001");
                connection.setRequestProperty("accept", "application/vnd.onem2m-res+json;");
                connection.setRequestProperty("authorization", "Basic QzczNzczOTYwLWI5ZGZmMDlhOlRlc3RAMTIz");
                connection.setRequestProperty("cache-control", "no-cache");
                connection.setRequestProperty("postman-token", "a701a972-0a84-a82a-2310-9a242db71b30");
                connection.connect();

                int status = connection.getResponseCode();
                Log.d("ResponseCode", Integer.toString(status));
                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                if ((line = reader.readLine()) != null) {
                    Log.d("Checker", "> " + line);
                }
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("Error1","MalUrl");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Exception", "e");
            } finally {
                if (connection != null) {
                    connection.disconnect();
                    Log.d("nope", "do");
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
