package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.List;

public class SearchingView_Adapter extends  RecyclerView.Adapter <SearchingView_Adapter.SearchingView_viewHolder>{

    List<User> dataList ;
    public static final String TAG = "===>" ;
    private Context context  ;
    FragmentActivity activity ;
    User user ;

    public SearchingView_Adapter(List<User> dataList, Context context , FragmentActivity activity) {
        this.dataList = dataList;
        this.context = context;
        this.activity = activity ;
    }

    @Override
    public SearchingView_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_searchview  , parent , false);
        SearchingView_viewHolder searchingView_viewHolder = new SearchingView_viewHolder(view , dataList , activity);
        return searchingView_viewHolder ;
    }

    @Override
    public void onBindViewHolder(SearchingView_viewHolder holder, int position) {

        SearchingView_viewHolder holder1 = holder ;
        this.user = dataList.get(position);
        holder1.textView_FullName.setText(user.Name );
        holder1.textView_LastName.setText(user.LastName);
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }

    public static class SearchingView_viewHolder extends RecyclerView.ViewHolder {

        TextView textView_FullName , textView_LastName ;

        public SearchingView_viewHolder(final View itemView , final List<User> userList , final FragmentActivity activity ) {
            super(itemView);

            textView_FullName = (TextView) itemView.findViewById(R.id.first_name);
            textView_LastName = (TextView) itemView.findViewById(R.id.LastName);




        }
    }
}
