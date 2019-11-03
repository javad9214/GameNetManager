package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment;


import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class Custom_Adapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragmentCollection = new ArrayList<>();
    List<String> mTitleCollection = new ArrayList<>();
    public static final String TAG = "=====>";


    public void addFragment( String title ,  Fragment fragment)
    {
        mFragmentCollection.add(fragment);
        mTitleCollection.add(title);
        Log.i(TAG, "addFragment: ");
    }

    public Custom_Adapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitleCollection.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentCollection.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentCollection.size();
    }
}
