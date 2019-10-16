package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.western.game.center.westerngamecenter.R;

public class Fragment1 extends Fragment {

    public Fragment1() {
    }

    public static Fragment1 createInstance(){
        Fragment1 fragment1 = new Fragment1();
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment1, container , false);

        TextView textView = (TextView) view.findViewById(R.id.first_name);


        return view ;

    }
}
