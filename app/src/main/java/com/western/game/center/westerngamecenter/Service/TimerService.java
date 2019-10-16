package com.western.game.center.westerngamecenter.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Time.ExampleTimer;
import com.western.game.center.westerngamecenter.Time.Timer;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.R;

public class TimerService extends Service {


    public static final String TAG = "===/===>";

    private BroadcastReceiver receiver ;

    private static final String KEY_TEXT_REPLY = "key_text_reply";

    Timer timer []  ;
    ActiveUser   activeUser ;

    NotificationManager mNotificationManager ;

    DataBase_Operation db ;

    int tag_id ;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer[40] ;
        tag_id = -1 ;
        db = App.getDataBaseOperation();


        receive();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        switch (intent.getIntExtra("mode" , 0 )){

            case 0 : // start new one

                activeUser = new ActiveUser();
                activeUser = db.Search_ActiveUser(intent.getIntExtra("id" , 0 ) , 1);
                start_new_active(activeUser);

                break;

            case 1 : // pause

                activeUser = new ActiveUser();
                activeUser = db.Search_ActiveUser(intent.getIntExtra("id" , 0 ) , 1);
                onPause(activeUser.Tag_Num);
                break;

            case 2 : // resume

                activeUser = new ActiveUser();
                activeUser = db.Search_ActiveUser(intent.getIntExtra("id" , 0 ) , 1);
                onResume(activeUser.Tag_Num);
                break;



        }






        return START_STICKY;
    }

    private void start_new_active (final ActiveUser activeUser){

        tag_id++ ;

        if (activeUser == null) {

            tag_id -- ;
        }else {

            activeUser.isRunning = true ;
            db.Update_Active_User(activeUser , 2);
            activeUser.Tag_Num = tag_id ;
            db.Update_Active_User(activeUser , 3);
            activeUser.isPause = false ;
            activeUser.isResuming = true ;
            db.Update_Active_User(activeUser , 4);
            db.Update_Active_User(activeUser , 5);
            timer[tag_id] = new ExampleTimer(1000, activeUser.Remaining_Time) {
                @Override
                protected void onTick() {
                    super.onTick();

                   // Log.i(TAG, "onTick:  "  + tag_id + "  " + timer[tag_id].isRunning() + "   " + timer[tag_id].getElapsedTime() + "  " + timer[tag_id].getRemainingTime());

                }

                @Override
                protected void onFinish() {
                    super.onFinish();
                    //Log.i(TAG, "onFinish: " );
                    activeUser.isRunning = false ;
                    db.Update_Active_User(activeUser , 2);
                    onExtraTimeStart(activeUser);
                    onFinish_notification(activeUser);

                }
            };
            timer[tag_id].start();

        }
    }

    public void onPause (int tagId){
        Log.i(TAG, "onPause:  "  + tag_id + "  " + timer[tag_id].isRunning() + "   " + timer[tag_id].getElapsedTime() + "  " + timer[tag_id].getRemainingTime());
            activeUser.isPause = true ;
            activeUser.isResuming = false ;
         db.Update_Active_User(activeUser , 4);
         db.Update_Active_User(activeUser , 5);
            timer[tagId].pause();
        }

    public void onResume(int tagId){
        activeUser.isPause = false ;
        activeUser.isResuming = true ;
        db.Update_Active_User(activeUser , 4);
        db.Update_Active_User(activeUser , 5);
        timer[tagId].resume();
    }

    private void onFinish_notification (ActiveUser activeUser2){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(icon);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round , 5);
        mBuilder.setContentTitle("Times Up !!!!");
        mBuilder.setOngoing(false);
        // mBuilder.setLights(Color.RED, 1000, 1000);
        long[] pattern2 = {500,500,500,500,500} ;
        // Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        // mBuilder.setSound(notification , RingtoneManager.TYPE_ALARM );
        mBuilder.setContentText("TV Number " + activeUser2.Tv_Num  + "Is Finished ... !");
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setCategory(NotificationCompat.CATEGORY_ALARM);
        //mBuilder.mShowWhen.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR ;
        // mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        //mBuilder.setStyle(new NotificationCompat.InboxStyle());
        // mBuilder.setVibrate(pattern2);

        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY).setLabel("answer me ").build();

        //PendingIntent that restarts the current activity instance.
        // Intent resultIntent = new Intent(this, Active_User_Base.class);
        // resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent resultPendingIntent = PendingIntent.getActivity(this , 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
        //  android.R.drawable.sym_action_chat, "REPLY", resultPendingIntent)
        // .addRemoteInput(remoteInput)
        //.setAllowGeneratedReplies(true)
        //  .build();


        // mBuilder.addAction(replyAction);




        Intent intent = new Intent("Stop_User");
        intent.putExtra("notificationId", 2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getApplicationContext() , 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.ic_stop_black_34dp, "Stop User", dismissIntent);
        mBuilder.setContentIntent(dismissIntent);

        Intent intent2 = new Intent("Extra_Time");
        intent2.putExtra("notificationId", 1);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent extra_time = PendingIntent.getBroadcast(getApplicationContext() , 150 , intent2 , 0);
        mBuilder.addAction(R.drawable.ic_fast_forward_black_34dp , "Extra Time", extra_time);


        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        // mNotificationManager.cancel(this.getIntent().getIntExtra("notificationId" , 1 ));

        receive();
        getApplicationContext().registerReceiver(receiver , new IntentFilter("Stop_User") );
        getApplicationContext().registerReceiver(receiver , new IntentFilter("Extra_Time") );
    }

    private void onExtraTimeStart (ActiveUser activeUser1){
        Intent service = new Intent( this , StopWatchService.class);
        service.putExtra("id" , activeUser1.Username_id) ;
        service.putExtra("mode" , 0 ) ;
        startService(service);
    }

    private void receive (){

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                switch (intent.getAction()){

                    case "onTime_Action" :

                        for (int j = 0; j <= tag_id ; j ++  ){
                            activeUser = new ActiveUser();
                            activeUser = db.Search_ActiveUser(j , 0);
                            Log.i(TAG, "onReceive: all active users  :  " + tag_id);
                            if (activeUser == null){

                                Log.i(TAG, "onReceive:  active user is null " + j);

                            }else {
                                Log.i(TAG, "onReceive: " + activeUser.NAME + "  " + j);
                                activeUser.Remaining_Time = (timer[j].getRemainingTime());
                                Log.i(TAG, "onReceive: " + timer[j].getRemainingTime());
                                db.Update_Active_User(activeUser, 0);
                                activeUser.Elapsed_time = (timer[j].getElapsedTime()) ;
                                db.Update_Active_User(activeUser, 1);

                                Log.i(TAG, "onReceive: " +  db.Search_ActiveUser(activeUser.Username_id , 1).Remaining_Time);

                            }



                        }break;


                    case "Stop_User" :
                        Log.i(TAG, "onReceive:  " + "stop user" );
                        getApplicationContext().unregisterReceiver(receiver);
                        mNotificationManager.cancel(1);
                        break;

                    case "Extra_Time" :
                        Log.i(TAG, "onReceive: " + "extra time ");
                        getApplicationContext().unregisterReceiver(receiver);
                        mNotificationManager.cancel(1);
                        break;


                }






            }
        };

      //  LocalBroadcastManager.getInstance(TimerService.this).registerReceiver(receiver , new IntentFilter("onTime_Action"));
    }


}
