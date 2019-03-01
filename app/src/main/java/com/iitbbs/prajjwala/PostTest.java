package com.iitbbs.prajjwala;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_test);

        new PostTest.JsonTaskPost().execute("http://168.87.87.213:8080/davc/m2m/HPE_IoT/9c65f9fffe218a69/DownlinkPayloadStatus/latest");
    }

    private class JsonTaskPost extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;


            try {
                /*URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("content-type", "application/vnd.onem2m-res+json;ty=4;");
                connection.setRequestProperty("x-m2m-origin", "C73773960-b9dff09a");
                connection.setRequestProperty("x-m2m-ri", "9900001");
                connection.setRequestProperty("accept", "application/vnd.onem2m-res+json;");
                connection.setRequestProperty("authorization", "Basic QzczNzczOTYwLWI5ZGZmMDlhOlRlc3RAMTIz");
                connection.setRequestProperty("cache-control", "no-cache");
                connection.setRequestProperty("postman-token", "9c8bf83a-3b9c-3b02-8ba7-8a9587d605fe");
                connection.setRequestMethod("POST");
                connection.connect();

                //int status = connection.getResponseCode();
                //Log.d("ResponseCode", Integer.toString(status));
                OutputStream stream = connection.getOutputStream();
                MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json;ty=4;");
                RequestBody body = RequestBody.create(mediaType, "{\r\n\r\n    \"m2m:cin\":\r\n\r\n    {\r\n\r\n      \"ty\":4,\r\n\r\n      \"cs\":300,\r\n\r\n      \"con\": \"{\\\"payload_dl\\\":{\\\"deveui\\\":\\\"9c65f9fffe2148d8\\\",\\\"port\\\":2,\\\"confirmed\\\":false,\\\"data\\\":\\\"gAA=\\\",\\\"on_busy\\\":\\\"fail\\\",\\\"tag\\\":\\\"9886151777dse\\\"}}\"\r\n\r\n    }\r\n\r\n} \r\n\r\n");
                stream.write(body); */

                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json;ty=4;");
                RequestBody body = RequestBody.create(mediaType, "{\r\n\r\n    \"m2m:cin\":\r\n\r\n    {\r\n\r\n      \"ty\":4,\r\n\r\n      \"cs\":300,\r\n\r\n      \"con\": \"{\\\"payload_dl\\\":{\\\"deveui\\\":\\\"9c65f9fffe218a69\\\",\\\"port\\\":2,\\\"confirmed\\\":false,\\\"data\\\":\\\"gAA=\\\",\\\"on_busy\\\":\\\"fail\\\",\\\"tag\\\":\\\"9886151777dse\\\"}}\"\r\n\r\n    }\r\n\r\n}");
                Request request = new Request.Builder()
                        .url("http://168.87.87.213:8080/davc/m2m/HPE_IoT/9c65f9fffe218a69/DownlinkPayload")
                        .post(body)
                        .addHeader("content-type", "application/vnd.onem2m-res+json;ty=4;")
                        .addHeader("x-m2m-origin", "C73773960-b9dff09a")
                        .addHeader("x-m2m-ri", "9900001")
                        .addHeader("accept", "application/vnd.onem2m-res+json;")
                        .addHeader("authorization", "Basic QzczNzczOTYwLWI5ZGZmMDlhOlRlc3RAMTIz")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "43000109-81b1-5cba-6934-2cf371cbc9b4")
                        .build();

                Response response = client.newCall(request).execute();

                Log.d("response: ",response.toString());

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
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}
