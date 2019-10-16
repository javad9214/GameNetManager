package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Add_new_User;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusforapp.achievementunlocked.AchievementUnlocked;
import com.geniusforapp.achievementunlocked.entity.AchievementData;
import com.geniusforapp.achievementunlocked.viewes.AchievementIconView;
import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Time.CalendarTool;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;


public class Add_user_dialog extends DialogFragment implements View.OnClickListener , View.OnTouchListener   {

    public static final String TAG = "===>" ;

    View view ;

    Animation a ;

    Button btn_save , btn_cancel ;

    EditText editText_firstName , editText_LastName , editText_phone ;

    FrameLayout frameLayout_name , frameLayout_lastname , frameLayout_phone ;

    private User user ;
    ArrayList<ActiveUser> list ;
    ActiveUser activeUser ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_user_dialog, container, false);

        init(view);


        editText_firstName.setOnTouchListener(this);
        editText_LastName.setOnTouchListener(this);
        editText_phone.setOnTouchListener(this);



        editText_firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (editText_firstName.getText().length() > 2){
                    frameLayout_name.setBackgroundResource(R.drawable.cardview_border_green);
                }
            }
        });
        editText_LastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText_LastName.getText().length() >3){
                    frameLayout_lastname.setBackgroundResource(R.drawable.cardview_border_green);
                }
            }
        });
        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText_phone.getText().length() > 10){
                    frameLayout_phone.setBackgroundResource(R.drawable.cardview_border_green);
                }
            }
        });



        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        return view ;
    }

    private void init(View view){

        btn_save = (Button) view.findViewById(R.id.save_new_user);
        btn_cancel = (Button) view.findViewById(R.id.cancel_add_new_user);
        editText_firstName = (EditText) view.findViewById(R.id.edit_First_name);
        editText_LastName = (EditText) view.findViewById(R.id.edit_Last_name);
        editText_phone = (EditText) view.findViewById(R.id.edit_phone);

        frameLayout_name = view.findViewById(R.id.fram_name);
        frameLayout_lastname = view.findViewById(R.id.fram_lastname);
        frameLayout_phone = view.findViewById(R.id.fram_phone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.save_new_user :
                SaveToDB();
                break;

            case R.id.cancel_add_new_user :
                dismiss();
                break;

        }
    }


    private void SaveToDB(){
        user = new User();

        if (!editText_firstName.getText().toString().trim().equals("")){
            user.Name = editText_firstName.getText().toString();
            if (!editText_LastName.getText().toString().trim().equals("")){
                if (!editText_phone.getText().toString().trim().equals("")){
                    user.Phone =  editText_phone.getText().toString() ;
                }else {
                    user.Phone = "00" ;
                }
                user.LastName = editText_LastName.getText().toString();
                user.TotalMoney = 0 ;
                user.LeftMoney = 0 ;
                CalendarTool calendarTool = new CalendarTool();
                user.Date = calendarTool.getIranianDate();
                DataBase_Operation db = App.getDataBaseOperation();
                db.addUser(user);
                showAchievement();
                list = new ArrayList<>();
                activeUser = new ActiveUser();
                list = db.Show_Active_user();
                activeUser = list.get(0);
                dismiss();
            }else {
                editText_LastName.setError("Required");
            }

        }else {
            editText_firstName.setError("Required");
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()){

            case R.id.edit_First_name :
                frameLayout_name.setBackgroundResource(R.drawable.cardview_border_yellow);
                check_frames(1);
                break;


            case R.id.edit_Last_name :
                frameLayout_lastname.setBackgroundResource(R.drawable.cardview_border_yellow);
                check_frames(2);
                break;

            case R.id.edit_phone :
                frameLayout_phone.setBackgroundResource(R.drawable.cardview_border_yellow);
                check_frames(3);
                break;
        }

        return false;
    }

    private void check_frames (int Mode){
    // mode = 1 : check first name frame
    // mode = 2 : check last name frame
    // mode = 3 : check phone frame

        switch (Mode){


            case 1 :
                if (editText_LastName.getText().toString().trim().equals("")){
                    frameLayout_lastname.setBackgroundResource(R.drawable.cardview_border_red);
                }

                if (editText_phone.getText().toString().trim().equals("")){
                    frameLayout_phone.setBackgroundResource(R.drawable.cardview_border_red);
                }
                break;

            case 2 :
                if (editText_firstName.getText().toString().trim().equals("")){
                    frameLayout_name.setBackgroundResource(R.drawable.cardview_border_red);
                }

                if (editText_phone.getText().toString().trim().equals("")){
                    frameLayout_phone.setBackgroundResource(R.drawable.cardview_border_red);
                }

                break;

            case 3 :
                if (editText_firstName.getText().toString().trim().equals("")){
                    frameLayout_name.setBackgroundResource(R.drawable.cardview_border_red);
                }

                if (editText_LastName.getText().toString().trim().equals("")){
                    frameLayout_lastname.setBackgroundResource(R.drawable.cardview_border_red);
                }
                break;
        }



    }






}
