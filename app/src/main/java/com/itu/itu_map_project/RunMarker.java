package com.itu.itu_map_project;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;

import java.util.TimerTask;



class MyRunnable implements Runnable {
    GoogleMap param;
    String myImei;
    Context ctx;
    public MyRunnable(GoogleMap param, String myImei, Context ctx) {
        this.param = param;
        this.myImei = myImei;
        this.ctx = ctx;
    }
    @SuppressWarnings("unchecked")
    public void run() {
    }
}
class RunMarker extends TimerTask {
    final Handler handler = new Handler();
    GoogleMap param;
    String myImei;
    Context ctx;
    public RunMarker(GoogleMap param, String myImei, Context ctx) {
        this.param = param;
        this.myImei = myImei;
        this.ctx = ctx;
    }
    @Override
    public void run() {
        handler.post(new MyRunnable(this.param, this.myImei, this.ctx) {
            @SuppressWarnings("unchecked")
            public void run() {
                try {
                    new DBTransaction.AsyncGetMarks(this.param, this.myImei,this.ctx).execute();
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }
}
