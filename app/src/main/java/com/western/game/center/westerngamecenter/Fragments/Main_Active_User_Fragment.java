package com.western.game.center.westerngamecenter.Fragments;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.geniusforapp.achievementunlocked.AchievementUnlocked;
import com.geniusforapp.achievementunlocked.entity.AchievementData;
import com.geniusforapp.achievementunlocked.viewes.AchievementIconView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.western.game.center.westerngamecenter.ActiveUsers.ActiveUsers_Recycler_Adapter;
import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Add_new_User.Add_user_dialog;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Search_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Service.TimerService;
import com.western.game.center.westerngamecenter.Tools.OnSwipeTouchListener;
import com.western.game.center.westerngamecenter.Tools.TypefaceSpan;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

import java.util.ArrayList;


public class Main_Active_User_Fragment extends Fragment implements  View.OnClickListener , RadioGroup.OnCheckedChangeListener{


    private static final String KEY_TEXT_REPLY = "key_text_reply";

    public static final String search_frag_tag = "search_frag_tag" ;

    public static final String TAG = "===>" ;

    NotificationManager mNotificationManager ;

    private BroadcastReceiver receiver ;

    FloatingActionButton fab  , fab2 ;
    Toolbar toolbar ;
    DrawerLayout drawer ;
    NavigationView navigationView ;
    Context context ;

    View view1  , view2  ;
    Animation a1 , a2 , a3 ;

    ArrayList<ActiveUser> list  = new ArrayList<>();
    ActiveUsers_Recycler_Adapter adapter ;
    RecyclerView recyclerView ;

    LinearLayout  linearLayout_main_page_empty ;



    private static final String Flag = "param7";


    ActiveUser activeUser  = new ActiveUser();



    public Main_Active_User_Fragment() {
        // Required empty public constructor
    }

    public static Main_Active_User_Fragment newInstance(Boolean flag) {
        Main_Active_User_Fragment fragment = new Main_Active_User_Fragment();
        Bundle args = new Bundle();
        args.putBoolean(Flag , flag);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main__active__user_, container, false);

        this.view2 = view ;
        checkDrawOverlayPermission();
        init(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //navigationView.setNavigationItemSelectedListener(this);
        //ActionBarDrawerToggle();
        SpannableString s = new SpannableString("Western Game Center");
        s.setSpan(new TypefaceSpan(getContext(), "Durwent.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
        //Window window = getActivity().getWindow() ;
        //window.setStatusBarColor(Color.rgb(129 , 6 , 6));

       // drawer.setStatusBarBackgroundColor(Color.TRANSPARENT);

        Window window = getActivity().getWindow() ;
        window.setStatusBarColor(Color.rgb(183 , 28 , 28));


        fab.setOnClickListener(this);

        view1 = view.findViewById(R.id.fab_add_active_user);

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()){

            public void onSwipeLeft() {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right , R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.placeholder ,  new Search_User_Fragment() , search_frag_tag);
                fragmentTransaction.addToBackStack("search user");
                fragmentTransaction.commit();
            }

        });

        setActive();


        return view ;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main , menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_new_user :
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                Add_user_dialog addUserDialog = new Add_user_dialog();
                addUserDialog.show(transaction, "add_user_dialog");
                
                break;

            case R.id.action_settings :

                remove(0);
                onFinish_notification();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100) {
//            if (Settings.canDrawOverlays(getContext())) {
//                showAchievement();
//            }
//        }
    }

    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(getContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, 100);
            return false;
        } else {
            return true;
        }
    }

    private void showAchievement() {
        AchievementUnlocked test = new AchievementUnlocked(getContext()).setReadingDelay(2000).setDismissible(false);
        AchievementData achievementData = new AchievementData();
        achievementData.setTitle("     New User Saved Successfully");
        achievementData.setState(AchievementIconView.AchievementIconViewStates.FADE_DRAWABLE);
        achievementData.setBackgroundColor(Color.parseColor("#ffffff"));
        achievementData.setIcon(getResources().getDrawable(R.drawable.ic_logo_western_web));
        achievementData.setTextColor(getResources().getColor(android.R.color.black));
        test.show(achievementData);
    }

    public void setActive (){
        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();

        if (list.get(0).null_flag){

            linearLayout_main_page_empty.setVisibility(View.VISIBLE);

        }else if (isMyServiceRunning(TimerService.class)){

            Log.i(TAG, "setActive:  username :" + list.get(0).Username_id + "  tag num :"  + list.get(0).Tag_Num);
            Ask_time();

        }else {

            showActive();
        }
    }

    public void showActive(){


        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();
        linearLayout_main_page_empty.setVisibility(View.INVISIBLE);
        adapter = new ActiveUsers_Recycler_Adapter(list, getContext(), view1, getActivity() , adapter );

        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }

    public void setActive (View viewParent , Context context , Activity activity){

        RecyclerView recyclerView ;
        ActiveUsers_Recycler_Adapter adapter ;
        ArrayList<ActiveUser> list  = new ArrayList<>();

        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();

        recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_active_user);

        //adapter = new ActiveUsers_Recycler_Adapter(list ,context, viewParent , activity );

       // recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context  , 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE){
                    fab.hide();
                }else if (dy < 0 && fab.getVisibility() != View.VISIBLE){
                    fab.show();
                }
            }
        });
    }

    public void init(View view){
        fab = (FloatingActionButton) view.findViewById(R.id.fab_add_active_user);

        linearLayout_main_page_empty = (LinearLayout) view.findViewById(R.id.empty_main_page_background);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_active_user);
    }

    public void ActionBarDrawerToggle(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity() , drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right , R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.placeholder , new Search_User_Fragment(), search_frag_tag);
        fragmentTransaction.addToBackStack("search user");
        fragmentTransaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    public void Ask_time () {

        Intent intent = new Intent("onTime_Action");

        LocalBroadcastManager.getInstance(getContext()).sendBroadcastSync(intent);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showActive();
            }
        }, 1000);


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void onFinish_notification (){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.ic_logo_western);
        mBuilder.setLargeIcon(icon);
        mBuilder.setSmallIcon(R.mipmap.ic_logo_western , 5);
        mBuilder.setContentTitle("Times Up !!!!");
        mBuilder.setOngoing(false);
       // mBuilder.setLights(Color.RED, 1000, 1000);
        long[] pattern2 = {500,500,500,500,500} ;
       // Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
       // mBuilder.setSound(notification , RingtoneManager.TYPE_ALARM );
        mBuilder.setContentText("TV Number " + " '5' " + "Is Finished ... !");
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setCategory(NotificationCompat.CATEGORY_ALARM);
        mBuilder.mNotification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR ;
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
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getContext() , 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.ic_stop_black_34dp, "Stop User", dismissIntent);
        mBuilder.setContentIntent(dismissIntent);

        Intent intent2 = new Intent("Extra_Time");
        intent2.putExtra("notificationId", 1);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent extra_time = PendingIntent.getBroadcast(getContext() , 150 , intent2 , 0);
        mBuilder.addAction(R.drawable.ic_fast_forward_black_34dp , "Extra Time", extra_time);


        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        // mNotificationManager.cancel(this.getIntent().getIntExtra("notificationId" , 1 ));

        receive();
        getActivity().registerReceiver(receiver , new IntentFilter("Stop_User") );
        getActivity().registerReceiver(receiver , new IntentFilter("Extra_Time") );
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(receiver , new IntentFilter("Stop_User") );
        getActivity().registerReceiver(receiver , new IntentFilter("Extra_Time") );

    }

    private void receive (){

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()){

                    case "Stop_User" :
                        Log.i(TAG, "onReceive:  " + "stop user" );
                        getActivity().unregisterReceiver(receiver);
                        mNotificationManager.cancel(1);
                        break;

                    case "Extra_Time" :
                        Log.i(TAG, "onReceive: " + "extra time ");
                        getActivity().unregisterReceiver(receiver);
                        mNotificationManager.cancel(1);
                        break;
                }

            }
        };

        //LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver , new IntentFilter("Stop_User"));
    }

    public void remove(int position) {
         list.remove(position);
         adapter.notifyItemRemoved(position);
    }












}
