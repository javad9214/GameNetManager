package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.western.game.center.westerngamecenter.R;

public class Fragment3 extends Fragment {
    public Fragment3() {

    }


    public static Fragment3 createInstance(){
        Fragment3 fragment3 = new Fragment3();
        return fragment3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sample_fragmet3 , container , false);
    }
}
