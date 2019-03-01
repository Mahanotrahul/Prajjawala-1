package com.iitbbs.prajjwala;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BlankFragment extends Fragment {
    private int progressStatus = 100;
    private Handler handler = new Handler();
    private  Handler handlerr = new Handler();
    private double weight = 15.30;
    private  double weightchanged = 0.0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank,container,false);
        final ProgressBar pg = (ProgressBar) view.findViewById(R.id.pb);
        super.onStart();
        progressStatus = 100;

        final TextView textView = (TextView) view. findViewById(R.id.tv5);
        final TextView tv1 = (TextView) view. findViewById(R.id.tv11);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus <= 100) {
                    // Update the progress status
                    progressStatus -= 1;
                    if(progressStatus == 0)
                    {
                        progressStatus = 100;
                    }

                    // Try to sleep the thread for 100 milliseconds
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String perecent = Integer.toString(progressStatus)+ "%";
                            pg.setProgress(progressStatus);
                            textView.setText(perecent);
                        }
                    });// Start the operation
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (weight <= 15.30) {
                    weight -= 0.1;
                    if (weight <= 0.5) {
                        weight = 15.30;
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handlerr.post(new Runnable() {
                        @Override
                        public void run() {
                            weightchanged = weight;
                            String s = String.format("%.2f",weightchanged);
                            /*NumberFormat formati = new DecimalFormat("#0");
                            formati.format(weightchanged);*/
                            final String weightc = s + " Kg";
                            tv1.setText(weightc);
                        }
                    });// Start the operation
                }
            }
        }).start();

        return view;
    }

}





