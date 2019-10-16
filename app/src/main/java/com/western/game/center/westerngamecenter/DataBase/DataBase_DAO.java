package com.western.game.center.westerngamecenter.DataBase;


import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;
import java.util.List;

public interface DataBase_DAO {


    long addUser(User user) ;

    int Update_User(User user , int UpdateMode);

    int Delete_User(User user) ;

    User Search_User(User user , int SearchMode);

    User Search_User(int id);

    List<User> Search_User(String name);

    ActiveUser Search_ActiveUser(int id , int Mode);


    long addActiveUser(ActiveUser activeUser);

    int Update_Active_User(ActiveUser activeUser , int updateMode);

    int Delete_ActiveUser(ActiveUser activeUser );

    ArrayList<ActiveUser> Show_Active_user ();

    ArrayList<User> show_user();

}
