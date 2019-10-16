package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment.Custom_dialog;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.List;

public class SearchUser_Adapter  extends RecyclerView.Adapter<SearchUser_Adapter.SearchUser_viewHolder>
        implements View.OnClickListener  {

    List<User> dataList ;
    public static final String TAG = "===>" ;
    LayoutInflater inflater ;
    ViewGroup viewGroup ;
    private View viewFinal ;
    private Context context  ;
    FragmentActivity activity ;

    Fragment fragment  ;


    User user ;
    SearchUser_viewHolder holder2 ;

    private EditText editText_money_dialog  ;
    private   RadioGroup radioGroup_set_num_joystick_dialog ;
    private   RadioButton  radioButton_numJoy1_dialog  ;



    public SearchUser_Adapter(List<User> dataList, Context context , View view , FragmentActivity activity) {
        this.dataList = dataList ;
        this.context = context;
        this.viewFinal = view ;
        this.activity = activity ;
    }

    @Override
    public SearchUser_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresult_recycler_content  , parent , false);
        this.viewGroup = parent ;
        SearchUser_viewHolder searchUser_viewHolder = new SearchUser_viewHolder(view);
        return searchUser_viewHolder ;
    }

    @Override
    public void onBindViewHolder(SearchUser_viewHolder holder, int position) {

        SearchUser_viewHolder holder1 = (SearchUser_viewHolder) holder ;
        this.holder2 = holder1;
        this.user = dataList.get(position);
        holder1.tx_password.setText(String.valueOf(user.LastName));
        holder1.tx_name.setText(user.Name);
        holder1.tx_phone.setText("+98" + " " + String.valueOf(user.Phone));
        holder1.tx_leftMoney.setText(String.valueOf(user.LeftMoney));
        holder1.tx_totalMoney.setText(String.valueOf(user.TotalMoney));
        holder1.tx_DateJoin.setText(user.Date);

        holder1.name.setVisibility(View.GONE);
        holder1.phone.setVisibility(View.GONE);
        holder1.line.setVisibility(View.GONE);
        holder1.line1.setVisibility(View.GONE);
        holder1.line2.setVisibility(View.GONE);
        holder1.line3.setVisibility(View.GONE);
        holder1.join.setVisibility(View.GONE);
        holder1.totalMoney.setVisibility(View.GONE);

        holder1.im_first.setOnClickListener(this);
        holder1.im_sec.setOnClickListener(this);
        holder1.btn_active.setOnClickListener(this);



    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drop_down_btn_first :

                if ( holder2.flag){
                    holder2.name.setVisibility(View.VISIBLE);
                    holder2.phone.setVisibility(View.VISIBLE);
                    holder2.line.setVisibility(View.VISIBLE);
                    holder2.line1.setVisibility(View.VISIBLE);
                    holder2.im_first.setImageResource(R.drawable.ic_keyboard_arrow_up_black_20dp);
                    holder2.flag = false ;
                }else {
                    holder2. name.setVisibility(View.GONE);
                    holder2.phone.setVisibility(View.GONE);
                    holder2.line.setVisibility(View.GONE);
                    holder2.line1.setVisibility(View.GONE);
                    holder2.im_first.setImageResource(R.drawable.ic_keyboard_arrow_down_black_20dp);
                    holder2.flag = true ;
                }
                break;

            case R.id.drop_down_btn_second :

                if ( holder2.flag2){

                    holder2.line2.setVisibility(View.VISIBLE);
                    holder2.line3.setVisibility(View.VISIBLE);
                    holder2.join.setVisibility(View.VISIBLE);
                    holder2.totalMoney.setVisibility(View.VISIBLE);
                    holder2.im_sec.setImageResource(R.drawable.ic_keyboard_arrow_up_black_20dp);
                    holder2.flag2 = false ;
                }else {
                    holder2.line2.setVisibility(View.GONE);
                    holder2.line3.setVisibility(View.GONE);
                    holder2.join.setVisibility(View.GONE);
                    holder2.totalMoney.setVisibility(View.GONE);
                    holder2.im_sec.setImageResource(R.drawable.ic_keyboard_arrow_down_black_20dp);
                    holder2.flag2 = true ;
                }
                break;

            case R.id.btn_active_user :


                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
                transaction.addToBackStack(null);
                Custom_dialog custom_dialog = Custom_dialog.newInstance(user.UID);
                custom_dialog.show(transaction , "dialog");

                break;
        }
    }



    public static class SearchUser_viewHolder extends RecyclerView.ViewHolder {

        TextView tx_username , tx_password , tx_name  , tx_phone , tx_leftMoney , tx_totalMoney , tx_DateJoin ;
        LinearLayout name , phone , line1 , line , totalMoney , join , line2 , line3 ;
        boolean flag = true  , flag2 = true ;
        ImageView im_first , im_sec  ;
        Button btn_active ;

        public SearchUser_viewHolder(View itemView) {
            super(itemView);

            tx_username  = (TextView) itemView.findViewById(R.id.text_username);
            tx_password  = (TextView) itemView.findViewById(R.id.text_password);
            tx_name  = (TextView) itemView.findViewById(R.id.text_name);
            tx_phone  = (TextView) itemView.findViewById(R.id.text_phone);
            tx_leftMoney  = (TextView) itemView.findViewById(R.id.text_left_money);
            tx_totalMoney  = (TextView) itemView.findViewById(R.id.text_total_money);
            tx_DateJoin  = (TextView) itemView.findViewById(R.id.text_Join_Time);

            im_first = (ImageView) itemView.findViewById(R.id.drop_down_btn_first);
            im_sec = (ImageView) itemView.findViewById(R.id.drop_down_btn_second);

            name = (LinearLayout) itemView.findViewById(R.id.linear_name);
            phone = (LinearLayout)  itemView.findViewById(R.id.linear_phone);
            line = (LinearLayout)  itemView.findViewById(R.id.linear_line);
            line1 = (LinearLayout)  itemView.findViewById(R.id.linear_line1);
            line2 = (LinearLayout)  itemView.findViewById(R.id.linear_line2);
            line3 = (LinearLayout)  itemView.findViewById(R.id.linear_line3);
            join = (LinearLayout)  itemView.findViewById(R.id.linear_join);
            totalMoney = (LinearLayout)  itemView.findViewById(R.id.linear_total_money);

            btn_active = (Button) itemView.findViewById(R.id.btn_active_user);

        }

    }





}
