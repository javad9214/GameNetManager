package com.western.game.center.westerngamecenter.ActiveUsers;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Search_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Tools.TypefaceSpan;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

import java.util.ArrayList;

public class Active_User_Base extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    Main_Active_User_Fragment main_active_user_fragment ;
    public static final String TagFragment = "main_frag_tag" ;

    Search_User_Fragment search_user_fragment ;
    public static final String search_frag_tag = "search_frag_tag" ;

    public static final String TAG = "===>" ;

    boolean doubleBackToExitPressedOnce = false;

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    ArrayList<ActiveUser> list ;
    ActiveUser activeUser ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active__user__base);

        checkPermission();

        final Window window = getWindow() ;
       // window.setStatusBarColor(Color.rgb(129 , 6 , 6));
        //window.setStatusBarColor(Color.TRANSPARENT);

        attachFragment();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // You don't have permission
                    checkPermission();
                }
            }

        }
    }

    public void attachFragment(){

        list = new ArrayList<>();
        activeUser = new ActiveUser();
        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();
        activeUser = list.get(0);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction() ;
        main_active_user_fragment = Main_Active_User_Fragment.newInstance(activeUser.null_flag);
        fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , TagFragment );
        fragmentTransaction.addToBackStack("t");
        fragmentTransaction.commit();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        search_user_fragment = (Search_User_Fragment) getSupportFragmentManager().findFragmentByTag(search_frag_tag);

        if (search_user_fragment != null && search_user_fragment.isVisible()){
            FragmentTransaction fragmentTransaction = search_user_fragment.getActivity().getSupportFragmentManager().beginTransaction() ;
            Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(true);
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left , R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, change_font("Press Back Again to Exit"), Toast.LENGTH_SHORT).show();
            //Toasting();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }

    private void Toasting (){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        //ImageView image = (ImageView) layout.findViewById(R.id.image);
        // image.setImageResource(R.drawable.ic_launcher);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(change_font("Press Back Again to Exit"));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 110);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private String change_font(String input){
        SpannableString s = new SpannableString(input);
        s.setSpan(new TypefaceSpan(this, "Durwent.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return s.toString() ;
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Log.i(TAG, "checkPermission: ");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }


}
