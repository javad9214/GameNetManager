package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.Calendar;

import github.chenupt.springindicator.SpringIndicator;


public class Custom_dialog extends DialogFragment implements View.OnClickListener  , View.OnKeyListener  {

    ViewPager viewPager ;

    boolean flag_fullName  = false ;

    private  long   endtime , EndTimeMin ;

    private  int  EndTimeHour ;

    String finalEndHour ;

    Switch switch_timer , switch_money ;

    EditText editText_timer , editText_money;
    long time , money ;

    TextView textView_money , textView_Timer  , textView_FullName ;

    ImageView imageView_timer , imageView_money ;

    //page 2 , game pads choose :
    ImageView image_game1, image_game2, image_game3, image_game4;
    ImageView image_tv1  , image_tv2 , image_tv3 , image_tv4 ,image_tv5 ,image_tv6 ,image_tv7 ,image_tv8 ,image_tv9 ,image_tv10 ;

    EditText editText_gamePad , editText_tv_num ;
    int gamePad , tv_num = 1 ;

    RelativeLayout line_timer , line_money ;

    public int GamePad = 1 ;

    ActiveUser activeUser ;

    User user ;

    SpringIndicator indicator ;

    Button btn_next , btn_previous , btn_cancel ;

    Fragment1 fragment1 ;
    Fragment2 tv_fragment;
    Fragment3 fragment3 ;
    chooseGamePad_fragment gamePad_fragment ;

    public static final String TAG = "=====>";

    public static Custom_dialog newInstance (int id){
        Custom_dialog custom_dialog = new Custom_dialog();
        Bundle args = new Bundle();
        args.putInt("ID" , id);
        custom_dialog.setArguments(args);
        return custom_dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_sample , container , false);

        init(rootView);

        Custom_Adapter adapter = new Custom_Adapter(getChildFragmentManager());
        fragment1 = new Fragment1() ;
        tv_fragment = new Fragment2();
        fragment3 = new Fragment3();
        gamePad_fragment = new chooseGamePad_fragment();
        adapter.addFragment("1" , fragment1 );
        adapter.addFragment("2" , gamePad_fragment);
        adapter.addFragment("3" , tv_fragment);
        adapter.addFragment("4" , fragment3 );




        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        viewPager.setPageTransformer(true , new ZoomOutPageTransformer());

        btn_previous.setVisibility(View.INVISIBLE);

        user = new User();
        DataBase_Operation db = App.getDataBaseOperation();
        user = db.Search_User(getArguments().getInt("ID"));



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                switch (position){
                    case 0 :
                        if (!flag_fullName) {
                            textView_FullName = (TextView) viewPager.getRootView().findViewById(R.id.first_name);
                            textView_FullName.setText(user.Name+ "\n" + user.LastName);
                            flag_fullName = true ;
                        }
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {
               switch (position){

                   case 0 :
                       btn_previous.setVisibility(View.INVISIBLE);
                       btn_next.setText("NEXT");
                       textView_FullName = (TextView) viewPager.getRootView().findViewById(R.id.first_name);
                       textView_FullName.setText(user.Name + "\n" + user.LastName);

                       break;

                   case 1 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("NEXT");

                       image_game1 = viewPager.getRootView().findViewById(R.id.gamepad1);
                       image_game2 = viewPager.getRootView().findViewById(R.id.gamepad2);
                       image_game3 = viewPager.getRootView().findViewById(R.id.gamepad3);
                       image_game4 = viewPager.getRootView().findViewById(R.id.gamepad4);

                       image_game1.setOnClickListener(Custom_dialog.this);
                       image_game2.setOnClickListener(Custom_dialog.this);
                       image_game3.setOnClickListener(Custom_dialog.this);
                       image_game4.setOnClickListener(Custom_dialog.this);


                       break;


                   case 2 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("NEXT");

                       image_tv1 = viewPager.getRootView().findViewById(R.id.tv1);
                       image_tv2 = viewPager.getRootView().findViewById(R.id.tv2);
                       image_tv3 = viewPager.getRootView().findViewById(R.id.tv3);
                       image_tv4 = viewPager.getRootView().findViewById(R.id.tv4);
                       image_tv5 = viewPager.getRootView().findViewById(R.id.tv5);
                       image_tv6 = viewPager.getRootView().findViewById(R.id.tv6);
                       image_tv7 = viewPager.getRootView().findViewById(R.id.tv7);
                       image_tv8 = viewPager.getRootView().findViewById(R.id.tv8);
                       image_tv9 = viewPager.getRootView().findViewById(R.id.tv9);
                       image_tv10 = viewPager.getRootView().findViewById(R.id.tv10);


                       image_tv1.setOnClickListener(Custom_dialog.this);
                       image_tv2.setOnClickListener(Custom_dialog.this);
                       image_tv3.setOnClickListener(Custom_dialog.this);
                       image_tv4.setOnClickListener(Custom_dialog.this);
                       image_tv5.setOnClickListener(Custom_dialog.this);
                       image_tv6.setOnClickListener(Custom_dialog.this);
                       image_tv7.setOnClickListener(Custom_dialog.this);
                       image_tv8.setOnClickListener(Custom_dialog.this);
                       image_tv9.setOnClickListener(Custom_dialog.this);
                       image_tv10.setOnClickListener(Custom_dialog.this);


                       break;

                   case 3 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("Active");

                       switch_timer = (Switch) viewPager.getRootView().findViewById(R.id.switch_timer);
                       switch_money = (Switch) viewPager.getRootView().findViewById(R.id.switch_money);
                       editText_money = (EditText) viewPager.getRootView().findViewById(R.id.edit_money);
                       editText_timer = (EditText) viewPager.getRootView().findViewById(R.id.timer_edittext);
                       textView_money = (TextView) viewPager.getRootView().findViewById(R.id.text_money);
                       textView_Timer = (TextView) viewPager.getRootView().findViewById(R.id.timer_text);
                       imageView_money = (ImageView) viewPager.getRootView().findViewById(R.id.img_money);
                       imageView_timer = (ImageView) viewPager.getRootView().findViewById(R.id.timer_img);
                       line_money = (RelativeLayout) viewPager.getRootView().findViewById(R.id.line_money);
                       line_timer = (RelativeLayout) viewPager.getRootView().findViewById(R.id.line_timer);

                       editText_timer.setEnabled(false);

                       editText_money.setOnKeyListener(Custom_dialog.this);
                       editText_timer.setOnKeyListener(Custom_dialog.this);



                       switch_timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                               if (switch_timer.isChecked()){
                                   switch_money.setChecked(false);
                                   imageView_money.setImageResource(R.drawable.ic_money_off_black_40dp);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_black_40dp);
                                   line_money.setVisibility(View.VISIBLE);
                                   line_timer.setVisibility(View.INVISIBLE);
                                   editText_money.setEnabled(false);
                                   editText_timer.setEnabled(true);


                               }else {
                                   switch_money.setChecked(true);
                                   imageView_money.setImageResource(R.drawable.ic_attach_money_black_40dp);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_off_black_40dp);
                                   line_money.setVisibility(View.INVISIBLE);
                                   line_timer.setVisibility(View.VISIBLE);
                                   editText_timer.setEnabled(false);

                                   editText_money.setEnabled(true);


                               }
                           }
                       });



                       switch_money.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                               if (switch_money.isChecked()){
                                   switch_timer.setChecked(false);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_off_black_40dp);
                                   imageView_money.setImageResource(R.drawable.ic_attach_money_black_40dp);
                                   line_money.setVisibility(View.INVISIBLE);
                                   line_timer.setVisibility(View.VISIBLE);
                                   editText_timer.setEnabled(false);

                                   editText_money.setEnabled(true);


                               }else {
                                   switch_timer.setChecked(true);
                                   imageView_money.setImageResource(R.drawable.ic_money_off_black_40dp);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_black_40dp);
                                   line_money.setVisibility(View.VISIBLE);
                                   line_timer.setVisibility(View.INVISIBLE);
                                   editText_money.setEnabled(false);

                                   editText_timer.setEnabled(true);

                               }
                           }
                       });
                       break;
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


        return rootView ;
    }

    private  void  init (View view){

        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_previous = (Button) view.findViewById(R.id.btn_previous);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        indicator = (SpringIndicator) view.findViewById(R.id.indicator);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_next :

                switch (viewPager.getCurrentItem()){

                    case 0 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        break;

                    case 1 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        break;

                    case 2 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        break;

                    case 3 :

                        if (editText_money.getText().toString().trim().equals("") || editText_timer.getText().toString().trim().equals("")) {
                            viewPager.setCurrentItem(2);
                            editText_money.setError("Required");
                        }else {
                            dismiss();
                            SendDataUser(time, money, GamePad);

                        }


                        break;

                }

                break;

            case R.id.btn_previous :

                switch (viewPager.getCurrentItem()){


                    case 1 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()- 1);
                        break;

                    case 2 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()- 1);
                        break;

                    case 3 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()- 1);
                        break;


                }

                break;


            case R.id.btn_cancel :
                dismiss();
                break;

            case R.id.gamepad1 :
                image_game1.setImageResource(R.drawable.ic_looks_one_green_24dp);
                image_game2.setImageResource(R.drawable.ic_looks_two_red_24dp);
                image_game3.setImageResource(R.drawable.ic_looks_3_red_24dp);
                image_game4.setImageResource(R.drawable.ic_looks_4_red_24dp);

                gamePad  = 1 ;
                break;

            case R.id.gamepad2 :
                image_game1.setImageResource(R.drawable.ic_looks_one_red_24dp);
                image_game2.setImageResource(R.drawable.ic_looks_two_green_24dp);
                image_game3.setImageResource(R.drawable.ic_looks_3_red_24dp);
                image_game4.setImageResource(R.drawable.ic_looks_4_red_24dp);

                gamePad  = 2 ;
                break;

            case R.id.gamepad3 :
                image_game1.setImageResource(R.drawable.ic_looks_one_red_24dp);
                image_game2.setImageResource(R.drawable.ic_looks_two_red_24dp);
                image_game3.setImageResource(R.drawable.ic_looks_3_green_24dp);
                image_game4.setImageResource(R.drawable.ic_looks_4_red_24dp);
                gamePad  = 3 ;
                break;

            case R.id.gamepad4 :
                image_game1.setImageResource(R.drawable.ic_looks_one_red_24dp);
                image_game2.setImageResource(R.drawable.ic_looks_two_red_24dp);
                image_game3.setImageResource(R.drawable.ic_looks_3_red_24dp);
                image_game4.setImageResource(R.drawable.ic_looks_4_green_24dp);
                gamePad  = 4 ;
                break;


            case R.id.tv1 :
                image_tv1.setImageResource(R.drawable.ic_one);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 1 ;

                break;

            case R.id.tv2 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 2 ;
                break;

            case R.id.tv3 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 3 ;
                break;

            case R.id.tv4 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 4 ;
                break;

            case R.id.tv5 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 5 ;
                break;

            case R.id.tv6 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 6 ;
                break;

            case R.id.tv7 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 7 ;
                break;

            case R.id.tv8 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 8 ;
                break;

            case R.id.tv9 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine);
                image_tv10.setImageResource(R.drawable.ic_ten);
                tv_num = 9 ;
                break;

            case R.id.tv10 :
                image_tv1.setImageResource(R.drawable.ic_one_red);
                image_tv2.setImageResource(R.drawable.ic_two_red);
                image_tv3.setImageResource(R.drawable.ic_three_red);
                image_tv4.setImageResource(R.drawable.ic_four_red);
                image_tv5.setImageResource(R.drawable.ic_five_red);
                image_tv6.setImageResource(R.drawable.ic_six_red);
                image_tv7.setImageResource(R.drawable.ic_seven_red);
                image_tv8.setImageResource(R.drawable.ic_eight_red);
                image_tv9.setImageResource(R.drawable.ic_nine_red);
                image_tv10.setImageResource(R.drawable.ic_ten_green);
                tv_num = 10 ;
                break;

        }


    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void SendDataUser(long time1 , long money , int num){

        activeUser  = new ActiveUser();

        activeUser.Remaining_Time = time1 *60 * 1000 ;
        Calendar calendar = Calendar.getInstance();
        activeUser.startTime = String.valueOf(calendar.get(calendar.HOUR_OF_DAY)) + " : " +
                String.valueOf(calendar.get(calendar.MINUTE));

        activeUser.endTime = calcEndTime(calendar , time1);
        activeUser.money = money ;
        user.TotalMoney  = money ;
        DataBase_Operation db = App.getDataBaseOperation();
        db.Update_User(user , 2 ) ;

        if (user.LeftMoney > money){

            user.LeftMoney = user.LeftMoney - money ;
            db.Update_User(user ,3 );
        }else {
            user.LeftMoney = 0 ;
            db.Update_User(user , 3) ;
        }
        activeUser.Username_id = user.UID ;
        activeUser.NumJoyStick = num ;
        activeUser.NAME = user.Name ;
        activeUser.LastName = user.LastName ;
        activeUser.Tv_Num = tv_num ;
        activeUser.isRunning = false ;
        activeUser.null_flag = true ;


        db.addActiveUser(activeUser);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
        Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(true);
        fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private String calcEndTime(Calendar calendar , long leftTime){

        endtime = calendar.get(calendar.MINUTE) + Math.round(leftTime) ;
        EndTimeHour = (int) endtime / 60 ;

        EndTimeMin = Math.round(endtime % 60 );
        if (calendar.get(calendar.HOUR_OF_DAY)+EndTimeHour > 24){
            EndTimeHour = EndTimeHour - (24 - calendar.get(calendar.HOUR_OF_DAY));
        }
        finalEndHour = String.valueOf(Math.round((calendar.get(calendar.HOUR_OF_DAY))+EndTimeHour))+ " : " +
                String.valueOf(Math.round(EndTimeMin));
        return finalEndHour;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {



        switch (v.getId()){

            case R.id.edit_money :
                if (editText_money.getText().length() > 3){
                    money = Long.parseLong(editText_money.getText().toString()) ;
                    Convert convert = new Convert( money , GamePad , true);
                    time = convert.result_time();
                    editText_timer.setText(String.valueOf(time));
                }

                if (editText_money.getText().length() < 4){
                    editText_timer.setText("");
                }
                break;

            case R.id.timer_edittext :


                if (editText_timer.getText().length() > 1){
                    time = Long.parseLong(editText_timer.getText().toString()) ;
                    Convert convert = new Convert( time , GamePad , false);
                     money = convert.result_money();
                    editText_money.setText(String.valueOf(money));
                }

                if (editText_timer.getText().length() < 2){
                    editText_money.setText("");
                }
                break;

        }

        return false;
    }



}
