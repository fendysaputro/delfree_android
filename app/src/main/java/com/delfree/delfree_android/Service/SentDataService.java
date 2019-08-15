package com.delfree.delfree_android.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by phephen on 6/8/19.
 */

public class SentDataService extends Service {

    public int counter=0;

    public SentDataService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SentDataService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");


        Intent broadcastIntent = new Intent(this, RestartService.class);

        sendBroadcast(broadcastIntent);

        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
            Log.i("ini stop", "stop");
        } else {
            timer.cancel();
            Log.i("ini stop", "stop lagi");
        }
    }
}
