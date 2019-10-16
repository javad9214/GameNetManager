package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.EditFragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dd.morphingbutton.MorphingButton;
import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;


public class EditFragment extends DialogFragment implements View.OnClickListener {


    public static final String TAG = "=====>";

    User user ;
    DataBase_Operation db ;

    Button btn_done ;
    Button btn_cancel ;
    EditText ed_name, ed_phone, ed_lastName;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance (int id){
        EditFragment editFragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt("ID" , id);
        editFragment.setArguments(args);
        return editFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit , container , false);

        init(rootView);

        user = new User();
        db = App.getDataBaseOperation();
        user = db.Search_User(getArguments().getInt("ID"));

        setEditText(user);

        btn_cancel.setOnClickListener(this);
        btn_done.setOnClickListener(this);


        return rootView ;
    }

    private void init (View view){

        btn_done = view.findViewById(R.id.btn_done);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        ed_name = view.findViewById(R.id.first_name);
        ed_phone = view.findViewById(R.id.phone_pro);
        ed_lastName = view.findViewById(R.id.last_name_pro);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_cancel :
                dismiss();
                break;

            case R.id.btn_done :
                check_news();
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void setEditText (User user){

        ed_name.setText(user.Name );
        ed_phone.setText(user.Phone);
        ed_lastName.setText(user.LastName);
    }

    private void check_news (){

        if (!ed_name.getText().toString().trim().equals(user.Name)){
            Log.i(TAG, "check_news: ");
            user.Name = ed_name.getText().toString();
            db.Update_User(user , 4);

        }


        if (!ed_lastName.getText().toString().trim().equals(user.LastName)){
            Log.i(TAG, "check_news: ");
            user.LastName = ed_lastName.getText().toString();
            db.Update_User(user , 1 );
        }


        if (!ed_phone.getText().toString().trim().equals(String.valueOf(user.Phone))){
            Log.i(TAG, "check_news: ");
            user.Phone = ed_phone.getText().toString() ;
            db.Update_User(user , 5 );
        }
    }






}
