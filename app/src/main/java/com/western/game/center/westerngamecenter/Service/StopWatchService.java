package com.western.game.center.westerngamecenter.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.western.game.center.westerngamecenter.Tools.CountUpTimer;


public class StopWatchService extends Service {

    public static final String TAG = "===>" ;

    public static final String MY_PREFS_NAME = "stopwatch";
    private BroadcastReceiver receiver ;

    CountUpTimer countUpTimer [] ;
    int tag_id ;

    public StopWatchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        tag_id = -1 ;
        countUpTimer = new CountUpTimer[40] ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        switch (intent.getIntExtra("mode" , 0 )){

            case 0 : // start new one

                intent.getIntExtra("id" , 0);
                start_new();

                break;

            case 1 : // pause

                break;
            case 2 : // resume

                break;

            case 3 : // stop
                break;

        }


        return START_STICKY;
    }

    private void start_new(){

        tag_id ++ ;

        countUpTimer[tag_id] = new CountUpTimer(1000) {
            @Override
            public void onTick(long elapsedTime) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME , MODE_PRIVATE).edit();
                editor.putLong(String.valueOf(tag_id) , elapsedTime);
                editor.apply();
            }
        };

        countUpTimer[tag_id].start();




    }

    private void pause(){}

    private void resume(){}

    private void stop(){}

    private void receive (){

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };

        LocalBroadcastManager.getInstance(StopWatchService.this).registerReceiver(receiver , new IntentFilter("onTime_Action"));
    }



}
