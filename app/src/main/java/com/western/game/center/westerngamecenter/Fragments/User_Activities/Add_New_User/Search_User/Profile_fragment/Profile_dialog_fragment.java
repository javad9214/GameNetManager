package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Profile_fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;

import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;


public class Profile_dialog_fragment extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "=====>";

    User user ;

    Button btn_done ;
    TextView textView_name , textView_phone , textView_date , textView_totalmoney , textView_totaltime , textView_left_money  , textView_lastname ;


    public Profile_dialog_fragment() {
        // Required empty public constructor
    }

    public static Profile_dialog_fragment newInstance (int id){
        Profile_dialog_fragment profile_dialog_fragment = new Profile_dialog_fragment();
        Bundle args = new Bundle();
        args.putInt("ID" , id);
        profile_dialog_fragment.setArguments(args);
        return profile_dialog_fragment;
    }

    private void init (View view){

        btn_done = view.findViewById(R.id.btn_done);
        textView_name = view.findViewById(R.id.first_name);
        textView_phone = view.findViewById(R.id.phone_pro);
        textView_date = view.findViewById(R.id.date_pro);
        textView_totalmoney = view.findViewById(R.id.total_money_pro);
        textView_left_money = view.findViewById(R.id.left_money_pro);
        textView_totaltime = view.findViewById(R.id.play_time_pro);
        textView_lastname = view.findViewById(R.id.last_name_pro);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_dialog , container , false);

        init(rootView);

        user = new User();
        DataBase_Operation db = App.getDataBaseOperation();
        user = db.Search_User(getArguments().getInt("ID"));


        setTextView(user);

        btn_done .setOnClickListener(this);


        return rootView;
    }


    private void setTextView (User user){

        textView_name.setText(user.Name );
        textView_date.setText(user.Date);
        textView_phone.setText( user.Phone);
        textView_left_money.setText(stringParserCommafy(String.valueOf(user.LeftMoney)) + " IRT");
        textView_totalmoney.setText(stringParserCommafy(String.valueOf(user.TotalMoney)) + " IRT");
        Convert convert = new Convert( user.TotalMoney , 1 , true);

        textView_totaltime.setText(minToHour(convert.result_time()));
        textView_lastname.setText(user.LastName);
    }

    private String minToHour(long min ){

        int  hour = (int) min / 60 ;
        int  minute = (int) min % 60 ;

        return  hour + " hr   " + minute  + " min" ;
    }

    private static String stringParserCommafy(String inputNum) {

        String commafiedNum="";
        Character firstChar= inputNum.charAt(0);

        //If there is a positive or negative number sign,
        //then put the number sign to the commafiedNum and remove the sign from inputNum.
        if(firstChar=='+' || firstChar=='-')
        {
            commafiedNum = commafiedNum + Character.toString(firstChar);
            inputNum=inputNum.replaceAll("[-\\+]", "");
        }

        //If the input number has decimal places,
        //then split it into two, save the first part to inputNum
        //and save the second part to decimalNum which will be appended to the final result at the end.
        String [] splittedNum = inputNum.split("\\.");
        String decimalNum="";
        if(splittedNum.length==2)
        {
            inputNum=splittedNum[0];
            decimalNum="."+splittedNum[1];
        }

        //The main logic for adding commas to the number.
        int numLength = inputNum.length();
        for (int i=0; i<numLength; i++) {
            if ((numLength-i)%3 == 0 && i != 0) {
                commafiedNum += ",";
            }
            commafiedNum += inputNum.charAt(i);
        }

        return commafiedNum+decimalNum;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }


}
