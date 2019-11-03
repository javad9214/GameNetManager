package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.internal.view.SupportMenuItem;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment.Custom_dialog;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.EditFragment.EditFragment;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Profile_fragment.Profile_dialog_fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Tools.OnSwipeTouchListener;
import com.western.game.center.westerngamecenter.Tools.RecyclerItemClickListener;
import com.western.game.center.westerngamecenter.Tools.TypefaceSpan;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;
import java.util.List;


public class Search_User_Fragment extends Fragment {

    LinearLayout linearLayout_empty_search_page ;

    DataBase_Operation db ;

    List<User> userList ;

    Toolbar toolbar ;
    DrawerLayout drawer ;
    NavigationView navigationView ;

    Intent intent ;

    int positionUser = 0 ; // position of user that long clicked ...

    ImageView  imageView_selected_user ;

    RecyclerView.OnItemTouchListener onItemTouchListener_all , onItemTouchListener_custom ;

    TextView text_no_results ;

    SearchingView_Adapter adapter;

    View view ;

    boolean isLongClicked = false ;
    boolean isSearchList = false ; // when list is show content of searching on search bar ...

    public static final String TAG2  = "===/===";

    RecyclerView recyclerView ;

    List<User> userArrayList;

    public static final String TAG = "===>" ;

    boolean edit_menu_flag = false ;

    LayoutInflater layoutInflater ;

    ImageView imageView_close_toolbar ;

    public Search_User_Fragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.intent = getActivity().getIntent() ;
        db = App.getDataBaseOperation() ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.searching_user_drwable, container, false);

        View view1 = view.findViewById(R.id.recycler_searchView);
        init(view);


        this.view = view ;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("Western Game Center");
        s.setSpan(new TypefaceSpan(getContext(), "Durwent.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);

        userArrayList = new ArrayList<>();

        view1.setOnTouchListener(new OnSwipeTouchListener(getContext()){

            public void onSwipeRight() {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(true);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left , R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        show_all_users();


        return view ;
    }

    private void init(View view){
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        text_no_results = (TextView) view.findViewById(R.id.text_no_results);

        linearLayout_empty_search_page = (LinearLayout) view.findViewById(R.id.empty_search_page_background) ;

        imageView_close_toolbar = (ImageView) view.findViewById(R.id.image_close_toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_searchView);


    }

    private void show_all_users(){

        text_no_results.setVisibility(View.INVISIBLE);
        userArrayList.clear();
        userArrayList = db.show_user();
        isSearchList = false ;

        if (!userArrayList.get(0).NullFlag) {
            linearLayout_empty_search_page.setVisibility(View.INVISIBLE);
            adapter = new SearchingView_Adapter(userArrayList, getContext(), getActivity());
            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
            manager.setOrientation(LinearLayoutManager.VERTICAL);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);



            onItemTouchListener_all =  new RecyclerItemClickListener(getContext() , recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onItemClick(View view, int position) {

                    User user3 = new User();

                    user3 = db.Search_User(userArrayList.get(position).UID);

                    Log.i(TAG2, "onItemClick: " + "NAME : " + user3.Name);
                    Log.i(TAG2, "onItemClick: " + "LAST NAME : " +  user3.LastName);
                    Log.i(TAG2, "onItemClick: " + "TOTAL MONEY : " + user3.TotalMoney);
                    Log.i(TAG2, "onItemClick: " + "LEFT MONEY : "  + user3.LeftMoney);
                    Log.i(TAG2, "onItemClick: " + "PHONE : "  + "+98" + user3.Phone);
                    Log.i(TAG2, "onItemClick: " + "DATE : " + user3.Date);
                    Convert convert = new Convert( user3.TotalMoney , 1 , true);
                    Log.i(TAG2, "onItemClick: " + "PLAY TIME : " + convert.result_time());

                    if (!isLongClicked){
                        if (db.Search_ActiveUser(db.show_user().get(position).UID , 1) != null){


                            AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("User was Activated ...");
                            builder.setMessage("You Cant Active a User More than One times ...");
                            builder.setPositiveButton("OK" , null);
                            builder.show();


                        }else {
                            CardView imageView  = view.findViewById(R.id.card_transition);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.addSharedElement(imageView , "transition_photo");
                            Custom_dialog custom_dialog = Custom_dialog.newInstance(userArrayList.get(position).UID);
                            custom_dialog.show(transaction, "dialog");
                        }
                    }else {
                        imageView_selected_user = (ImageView) view.findViewById(R.id.image_contact);


                            if (positionUser == position){

                                    imageView_selected_user.setImageResource(R.mipmap.ic_launcher);
                                    isLongClicked = false;
                                    change_toolbar(false);
                            }

                    }

                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLongItemClick(View view, int position) {

                    positionUser = position ;
                    isLongClicked = true ;
                    change_toolbar(true);
                    Log.i(TAG, "onLongItemClick: ");
                    imageView_selected_user = (ImageView) view.findViewById(R.id.image_contact);
                    imageView_selected_user.setImageResource(R.drawable.ic_done_black_50dp);
                }
            }) ;
            recyclerView.addOnItemTouchListener(onItemTouchListener_all);


        }else {
            linearLayout_empty_search_page.setVisibility(View.VISIBLE);


        }



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (edit_menu_flag){
            inflater.inflate(R.menu.menu_second_toolbar, menu);
        }else {

            inflater.inflate(R.menu.option_menu , menu);
            MenuItem searchItem = menu.findItem(R.id.search);
            SupportMenuItem supportMenuItem = (SupportMenuItem) menu.findItem(R.id.search);

//            supportMenuItem.setSupportOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//                @Override
//                public boolean onMenuItemActionExpand(MenuItem menuItem) {
//                    return true;
//                }
//
//                @Override
//                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//                    show_all_users();
//                    return true;
//                }
//            });

//            MenuItemCompat.setOnActionExpandListener(searchItem , new MenuItemCompat.OnActionExpandListener() {
//                @Override
//                public boolean onMenuItemActionExpand(MenuItem item) {
//                    Log.i(TAG, "onMenuItemActionExpand: ");
//                    return true;
//                }
//
//                @Override
//                public boolean onMenuItemActionCollapse(MenuItem item) {
//                    Log.i(TAG, "onMenuItemActionCollapse: ");
//                    show_all_users();
//                    return true;
//                }
//            });









            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            if (searchManager != null) {
                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getActivity().getComponentName()));
            }


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                   Log.i(TAG, "onQueryTextSubmit: ");
                   handleIntent(intent , query);
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String newText) {
                   if (!newText.isEmpty()) {
                       Log.i(TAG, "onQueryTextChange: " + newText);
                       handleIntent(intent , newText);

                   }
                   return false;
               }
           });

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();



        switch (id){

            case android.R.id.home :
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(true);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left , R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true ;

            case R.id.action_user_profile :

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    Profile_dialog_fragment profile_dialog_fragment = Profile_dialog_fragment.newInstance(userArrayList.get( positionUser).UID);
                    profile_dialog_fragment.show(transaction, "dialog");

                break;


            case R.id.action_delete :

                db.Delete_User(userArrayList.get(positionUser));
                userArrayList.remove(positionUser);
                adapter.notifyItemRemoved(positionUser);
                imageView_selected_user.setImageResource(R.mipmap.ic_launcher);
                isLongClicked = false;
                change_toolbar(false);

                break;

            case R.id.action_edit :


                    FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction2.addToBackStack(null);
                    EditFragment editFragment = EditFragment.newInstance(userArrayList.get( positionUser).UID);
                    editFragment.show(transaction2, "dialog");


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void change_toolbar(boolean change) {

        final Window window = getActivity().getWindow() ;

        if (change){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.second_toolbar , null);
            edit_menu_flag = true ;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true) ;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.CYAN));

            window.setStatusBarColor(Color.rgb(3 , 194 ,194));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (v != null) {
                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
            getActivity(). invalidateOptionsMenu();
            imageView_close_toolbar = (ImageView) view.findViewById(R.id.image_close_toolbar);
            imageView_close_toolbar.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    isLongClicked = false ;
                    recyclerView.invalidate();
                    recyclerView.removeOnItemTouchListener(onItemTouchListener_all);
                    recyclerView.removeOnItemTouchListener(onItemTouchListener_custom);
                    show_all_users();
                    unChange_Toolbar();
                }
            });


        }else {

            unChange_Toolbar();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void unChange_Toolbar (){
        final Window window = getActivity().getWindow() ;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false) ;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(null);
        window.setStatusBarColor(Color.rgb(129 , 6 , 6));
        edit_menu_flag = false ;

        getActivity().invalidateOptionsMenu();
    }

    private void handleIntent(Intent intent , String query) {

            Log.i(TAG, "handleIntent: " + query);
            userList = new ArrayList<>();
            userList = db.Search_User(query);
            if (userList == null ){
                recyclerView.removeAllViewsInLayout();
                text_no_results.setVisibility(View.VISIBLE);

            }else {

                display_search_result(userList);
            }

    }

    private void display_search_result (final List<User> list){

        isSearchList = true ;
        userArrayList.clear();
        userArrayList = list ;
        text_no_results.setVisibility(View.INVISIBLE);
        recyclerView.invalidate();
        adapter = new SearchingView_Adapter(list , getContext(), getActivity());
        adapter.notifyItemRangeChanged(0 , list.size());
        adapter.notifyDataSetChanged();
        recyclerView.removeOnItemTouchListener(onItemTouchListener_all);
        recyclerView.removeOnItemTouchListener(onItemTouchListener_custom);
        recyclerView.swapAdapter(adapter , false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        onItemTouchListener_custom =  new RecyclerItemClickListener(getContext() , recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(View view, int position) {

                if (!isLongClicked){
                    if (db.Search_ActiveUser(db.show_user().get(position).UID , 1) != null){


                        AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("User was Activated ...");
                        builder.setMessage("You Cant Active a User More than One times ...");
                        builder.setPositiveButton("OK" , null);
                        builder.show();


                    }else {
                        CardView imageView  = view.findViewById(R.id.card_transition);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.addSharedElement(imageView , "transition_photo");
                        Custom_dialog custom_dialog = Custom_dialog.newInstance(userArrayList.get(position).UID);
                        custom_dialog.show(transaction, "dialog");
                    }
                }else {
                    imageView_selected_user = (ImageView) view.findViewById(R.id.image_contact);


                    if (positionUser == position){

                        imageView_selected_user.setImageResource(R.mipmap.ic_launcher);
                        isLongClicked = false;
                        change_toolbar(false);
                    }

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLongItemClick(View view, int position) {

                positionUser = position ;
                isLongClicked = true ;
                change_toolbar(true);
                imageView_selected_user = (ImageView) view.findViewById(R.id.image_contact);
                imageView_selected_user.setImageResource(R.drawable.ic_done_black_50dp);
            }
        }) ;

        recyclerView.addOnItemTouchListener(onItemTouchListener_custom);


    }



}

