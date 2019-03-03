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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class usageFragment extends Fragment {

    String conId;
    GraphView graphView;
    ArrayList<Integer> gasQuant;
    ArrayList<String> timeStamp;
    ArrayList<Integer> rechAmount;
    ArrayList<String> rechTimeStamp;
    GraphView rechGraph;
    TextView rechHeader;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usage,container,false);
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        gasQuant = new ArrayList<>();
        timeStamp = new ArrayList<>();
        rechTimeStamp = new ArrayList<>();
        rechAmount = new ArrayList<>();
        rechHeader = (TextView) view.findViewById(R.id.rech_header);
        conId = preferences.getString("consumerID", "");
        rechGraph = (GraphView) view.findViewById(R.id.graph_rech_history);
        graphView = (GraphView) view.findViewById(R.id.graph_gas_usage);
        Log.d("checkCon", conId);
        try {
            String results = new ConnectionthreadUsage().execute().get();
            Log.d("connthread", results);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(rechAmount.size()==0){
            rechHeader.setText("No Recharge History");
        }else{
            rechGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                @Override
                public String formatLabel(double value, boolean isValueX){
                    if(isValueX) {
                        return rechTimeStamp.get((int) value);
                    }else{
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
            LineGraphSeries<DataPoint> rechSeries = new LineGraphSeries<>();
            for(int i=0;i<rechAmount.size();i++){
                rechSeries.appendData(new DataPoint(i, rechAmount.get(i)), false, rechAmount.size());
            }
            rechGraph.getViewport().setYAxisBoundsManual(true);
            rechGraph.getViewport().setMinY(0);
            rechGraph.getViewport().setMaxY(600);

            rechGraph.getViewport().setXAxisBoundsManual(true);
            rechGraph.getViewport().setMinX(0);
            rechGraph.getViewport().setMaxX(rechAmount.size()-1);

            // enable scaling and scrolling
            rechGraph.getViewport().setScalable(true);
            rechGraph.getViewport().setScalableY(true);

            rechSeries.setDrawDataPoints(true);
            rechGraph.addSeries(rechSeries);
        }

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX){
                if(isValueX) {
                    return timeStamp.get((int) value);
                }else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0;i<5;i++){
            series.appendData(new DataPoint(i, gasQuant.get(i)), false, 5);
        }

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(10000);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(4);

        // enable scaling and scrolling
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        series.setDrawDataPoints(true);
        graphView.addSeries(series);
        return view;
    }

    private class ConnectionthreadUsage extends AsyncTask<String, Void, String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            try {

                String link = "https://vasitars.com/old/prajjawala/Consumer_Portal/APP/Get_all_data_wifi.php";
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                String dataStream = URLEncoder.encode("CONSUMER_ID", "UTF-8")
                        + "=" + URLEncoder.encode(conId, "UTF-8");
                wr.write(dataStream);
                wr.flush();
                reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));


                String line = reader.readLine();

                Log.i("huhu", line);

                try {
                   String[] data = line.split(" ");
                   for(int i=0;i<5;i++){
                       JSONObject obj = new JSONObject(data[i]);
                       gasQuant.add(Integer.valueOf(obj.getString("Gas_Left")));
                       timeStamp.add(obj.getString("Time").substring(0,5));
                       Log.d("timeSt", obj.getString("Time"));
                   }
                }
                catch (Exception e) {

                }
                String linkRech = "https://vasitars.com/old/prajjawala/Consumer_Portal/APP/Get_Recharge_data.php";
                URL urlRech = new URL(linkRech);
                HttpURLConnection connRech = (HttpURLConnection) urlRech.openConnection();

                connRech.setRequestMethod("POST");
                OutputStreamWriter wrRech = new OutputStreamWriter(connRech.getOutputStream());
                String dataStreamRech = URLEncoder.encode("CONSUMER_ID", "UTF-8")
                        + "=" + URLEncoder.encode(conId, "UTF-8");
                wrRech.write(dataStreamRech);
                wrRech.flush();
                reader = new BufferedReader(new
                        InputStreamReader(connRech.getInputStream()));


                String lineRech = reader.readLine();

                Log.i("huhuRech", lineRech);

                try {
                    String[] dataRech = lineRech.split(" ");
                    for(int i=0;i<dataRech.length;i++){
                        JSONObject obj = new JSONObject(dataRech[i]);
                        rechAmount.add(Integer.valueOf(obj.getString("Recharge_Amount")));
                        rechTimeStamp.add(obj.getString("Time").substring(0,5));
                        Log.d("timeSt", obj.getString("Time"));
                    }
                }
                catch (Exception e) {

                }



            }
            catch(Exception e) {
                e.printStackTrace();
                return e.toString();
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
}