package com.western.game.center.westerngamecenter.ActiveUsers;


import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

import java.util.ArrayList;

public class Updating_List extends DiffUtil.Callback {

    private final ArrayList<ActiveUser> mNewList;

    private final ArrayList<ActiveUser> mOldList;

    public Updating_List(ArrayList<ActiveUser> mNewList, ArrayList<ActiveUser> mOldList) {
        this.mNewList = mNewList;
        this.mOldList = mOldList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return mOldList.get(oldItemPosition).Username_id == mNewList.get(newItemPosition).Username_id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ActiveUser oldActiveUser = mOldList.get(oldItemPosition);
        final ActiveUser newActiveUser = mNewList.get(newItemPosition);

        return oldActiveUser.Remaining_Time == newActiveUser.Remaining_Time;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
