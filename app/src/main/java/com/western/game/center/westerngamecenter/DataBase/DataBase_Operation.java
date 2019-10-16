package com.western.game.center.westerngamecenter.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;
import java.util.List;

public class DataBase_Operation implements DataBase_DAO{


    private static final String TAG = "===>";
    private SQLiteDatabase db_user ;


    public DataBase_Operation(Context context) {

        DataBase_OpenHelper dataBase_openHelper = new DataBase_OpenHelper(context);
        db_user = dataBase_openHelper.getWritableDatabase();
    }

    @Override
    public long addUser(User user) {

        ContentValues values = new ContentValues();
        values.put(DB_Constants.UsersInfoTable.Last_Name , user.LastName);
        values.put(DB_Constants.UsersInfoTable.ToTAL_MONEY , user.TotalMoney);
        values.put(DB_Constants.UsersInfoTable.LEFT_MONEY , user.LeftMoney);
        values.put(DB_Constants.UsersInfoTable.NAME , user.Name);
        values.put(DB_Constants.UsersInfoTable.PHONE , user.Phone);
        values.put(DB_Constants.UsersInfoTable.DATE , user.Date);
        return db_user.insert(DB_Constants.UsersInfoTable.USER_TABLE_NAME ,null , values) ;

    }

    @Override
    public int Update_User(User user, int UpdateMode) {

        ContentValues values = new ContentValues();
        String where = "" ;
        String[] args = new String[1];

        User user1 = new User();
        String selections ;
        Cursor cursor ;
        selections = DB_Constants.UsersInfoTable.USER_UID + " like ?";
        String[] selectionArgs = {"%" + user.UID + "%"};
        cursor = db_user.query(DB_Constants.UsersInfoTable.USER_TABLE_NAME , null , selections , selectionArgs , null , null ,null);
        cursor.moveToFirst();
        while (true) {
            user1.TotalMoney = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY));
            user1.LeftMoney = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.LEFT_MONEY));

            cursor.moveToNext();
            if (cursor.isAfterLast()) {
                break;
            }
        }
        cursor.close();



        switch (UpdateMode){


            case 1 : // Last Name
                values.put(DB_Constants.UsersInfoTable.Last_Name, user.LastName);
                break;

            case 2 : // Total Money
                user.TotalMoney = user.TotalMoney +  user1.TotalMoney ;
                Log.i(TAG, "Update_User: " + user.TotalMoney);
                values.put(DB_Constants.UsersInfoTable.ToTAL_MONEY , user.TotalMoney);
                break;

            case 3 : //Left Money
                values.put(DB_Constants.UsersInfoTable.LEFT_MONEY , user.LeftMoney);
                break;

            case 4 : // Name
                values.put(DB_Constants.UsersInfoTable.NAME , user.Name);
                break;

            case 5 : //Phone
                values.put(DB_Constants.UsersInfoTable.PHONE , user.Phone);
                break;

            case 6 : // Date
                values.put(DB_Constants.UsersInfoTable.DATE , user.Date);
                break;

        }

        where = DB_Constants.UsersInfoTable.USER_UID + " = ?" ;
        args[0] = String.valueOf(user.UID);


        return db_user.update(DB_Constants.UsersInfoTable.USER_TABLE_NAME , values , where , args );
    }

    @Override
    public int Delete_User(User user) {
        String [] args = {String.valueOf(user.UID)};

        return db_user.delete(DB_Constants.UsersInfoTable.USER_TABLE_NAME ,
                DB_Constants.UsersInfoTable.USER_UID + " = ?"  , args );
    }

    @Override
    public User Search_User(User user, int SearchMode) {

        User user1 = new User();
        String selections ;
        Cursor cursor ;
        switch (SearchMode){

            case 0 : // search by username
                Log.i(TAG, "Search_User: username");
                selections = DB_Constants.UsersInfoTable.USER_UID + " like ?";
                String[] selectionArgs = {"%" + user.UID + "%"};
                cursor = db_user.query(DB_Constants.UsersInfoTable.USER_TABLE_NAME , null , selections , selectionArgs , null , null ,null);
                if (cursor.getCount() == 0 ){
                    user1.Name = "NO User Found" ;
                }else {
                    cursor.moveToFirst();
                    while (true) {
                        user1.UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.USER_UID));
                        user1.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.Last_Name));
                        user1.Phone = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.PHONE));
                        user1.TotalMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY));
                        Log.i(TAG, "Search_User: " +  cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY)));
                        user1.LeftMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.LEFT_MONEY));
                        user1.Name = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.NAME));
                        user1.Date = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.DATE));

                        cursor.moveToNext();
                        if (cursor.isAfterLast()) {
                            break;
                        }
                    }
                    cursor.close();
                    return user1;
                }



            case 1 : //search by phone
                Log.i(TAG, "Search_User: phone");
                selections = DB_Constants.UsersInfoTable.PHONE + " like ?";
                String[] selectionArgs_Pass = {"%" + user.Phone + "%"};
                cursor = db_user.query(DB_Constants.UsersInfoTable.USER_TABLE_NAME , null , selections , selectionArgs_Pass , null , null ,null);
                if (cursor.getCount() == 0 ){
                    user1.Name = "NO User Found" ;
                }else {
                    cursor.moveToFirst();
                    while (true){
                        user1.UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.USER_UID));
                        user1.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.Last_Name));
                        user1.Phone = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.PHONE));
                        user1.TotalMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY));
                        Log.i(TAG, "Search_User: " +  cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY)));
                        user1.LeftMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.LEFT_MONEY));
                        user1.Name = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.NAME));
                        user1.Date = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.DATE));

                        cursor.moveToNext();
                        if (cursor.isAfterLast()){
                            break;
                        }
                    }
                    cursor.close();
                    return user1 ;
                }

        }

        return user1 ;
    }

    @Override
    public User Search_User(int id) {

        User user1 = new User();
        String selections ;
        Cursor cursor ;

        selections = DB_Constants.UsersInfoTable.USER_UID + " like ?";
        String[] selectionArgs = {"%" + id + "%"};

        cursor = db_user.query(DB_Constants.UsersInfoTable.USER_TABLE_NAME , null , selections , selectionArgs , null , null ,null);

        cursor.moveToFirst();
        while (true) {
            user1.UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.USER_UID));
            user1.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.Last_Name));
            user1.Phone = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.PHONE));
            user1.TotalMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY));
            user1.LeftMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.LEFT_MONEY));
            user1.Name = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.NAME));
            user1.Date = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.DATE));

            cursor.moveToNext();
            if (cursor.isAfterLast()) {
                break;
            }
        }
        cursor.close();


        return user1;
    }

    @Override
    public List<User> Search_User(String name) {

        Cursor cursor ;
        User user1 ;
        List<User> userList ;

        String selections ;

        selections = DB_Constants.UsersInfoTable.NAME + " like ?";
        String[] selectionArgs = {"%" + name + "%"};

        cursor = db_user.query(DB_Constants.UsersInfoTable.USER_TABLE_NAME , null , selections , selectionArgs , null , null , DB_Constants.UsersInfoTable.NAME + " ASC");

        if (cursor == null) {
            return null;
        } else if (cursor.getCount() == 0 ){
            return null ;
        } else {
            cursor.moveToFirst();
            userList = new ArrayList<>();
            while (true) {
                user1 = new User();
                user1.UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.USER_UID));
                user1.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.Last_Name));
                user1.Phone = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.PHONE));
                user1.TotalMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY));
                user1.LeftMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.LEFT_MONEY));
                user1.Name = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.NAME));
                user1.Date = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.DATE));

                userList.add(user1);
                cursor.moveToNext();
                if (cursor.isAfterLast()) {
                    break;
                }
            }
            cursor.close();


            return userList;

        }

    }

    @Override
    public ActiveUser Search_ActiveUser(int id , int Mode) {
        Cursor cursor ;
        String selections ;
        ActiveUser activeUser = new ActiveUser();

        if (Mode == 1){
            selections = DB_Constants.ActiveUsersTable.USERNAME + " like ?";
        }else {
            selections = DB_Constants.ActiveUsersTable.TAG_NUM + " like ?";
        }



        String[] selectionArgs = {"%" + id + "%"};

        cursor = db_user.query(DB_Constants.ActiveUsersTable.ACTIVE_USER_TABLE_NAME, null , selections , selectionArgs , null , null , null );

        if (cursor == null) {
            return null;
        } else if (cursor.getCount() == 0 ){
            return null ;
        } else {
            cursor.moveToFirst();

            activeUser.Username_id = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.USERNAME));
            activeUser.active_UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.ACTIVE_USER_UID));
            activeUser.NumJoyStick = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.NUM_JOYSTICK));
            activeUser.Tag_Num = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.TAG_NUM));
            activeUser.Tv_Num = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.TV_NUM));
            activeUser.startTime = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.START_TIME));
            activeUser.NAME = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.NAME));
            activeUser.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.LAST_NAME));
            activeUser.endTime = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.END_TIME));
            activeUser.money = cursor.getLong(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.MONEY));
            activeUser.Remaining_Time = cursor.getLong(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.REMAINING_TIME));
            activeUser.Elapsed_time = cursor.getLong(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.ELAPSED_TIME));
            activeUser.null_flag = false ;
            if (cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.IS_RUNNING)) > 0 ){
                activeUser.isRunning = true ;
            }else {
                activeUser.isRunning = false ;
            }

            if (cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.IS_RUN_PAUSE)) > 0 ){
                activeUser.isPause = true ;
            }else {
                activeUser.isPause = false ;
            }

            if (cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.IS_RUN_RESUME)) > 0 ){
                activeUser.isResuming = true ;
            }else {
                activeUser.isResuming = false ;
            }


            cursor.close();
        }

        return activeUser ;
    }

    @Override
    public long addActiveUser(ActiveUser activeUser) {

        ContentValues values = new ContentValues();
        values.put(DB_Constants.ActiveUsersTable.USERNAME , activeUser.Username_id);
        values.put(DB_Constants.ActiveUsersTable.NAME , activeUser.NAME);
        values.put(DB_Constants.ActiveUsersTable.LAST_NAME , activeUser.LastName);
        values.put(DB_Constants.ActiveUsersTable.START_TIME , activeUser.startTime);
        values.put(DB_Constants.ActiveUsersTable.END_TIME , activeUser.endTime);
        values.put(DB_Constants.ActiveUsersTable.MONEY , activeUser.money);
        values.put(DB_Constants.ActiveUsersTable.NUM_JOYSTICK , activeUser.NumJoyStick);
        values.put(DB_Constants.ActiveUsersTable.TV_NUM , activeUser.Tv_Num);
        values.put(DB_Constants.ActiveUsersTable.IS_RUNNING , 0 );
        values.put(DB_Constants.ActiveUsersTable.IS_RUN_RESUME , 0 );
        values.put(DB_Constants.ActiveUsersTable.IS_RUN_PAUSE , 0 );
        values.put(DB_Constants.ActiveUsersTable.ELAPSED_TIME , 0 );
        values.put(DB_Constants.ActiveUsersTable.REMAINING_TIME , activeUser.Remaining_Time );
        values.put(DB_Constants.ActiveUsersTable.TAG_NUM , -1 );
        return db_user.insert(DB_Constants.ActiveUsersTable.ACTIVE_USER_TABLE_NAME,null , values) ;


    }

    @Override
    public int Update_Active_User(ActiveUser activeUser, int updateMode) {
        ContentValues values = new ContentValues();
        String where = "" ;
        String[] args = new String[1];


        switch (updateMode){

            case 0 : //remaining_time
                values.put(DB_Constants.ActiveUsersTable.REMAINING_TIME , activeUser.Remaining_Time);
                break;

            case 1 : //Elapsed time
                values.put(DB_Constants.ActiveUsersTable.ELAPSED_TIME , activeUser.Elapsed_time);
                break;

            case 2 : // is running
                if (activeUser.isRunning){
                    values.put(DB_Constants.ActiveUsersTable.IS_RUNNING , 1);
                }else {
                    values.put(DB_Constants.ActiveUsersTable.IS_RUNNING , 0);
                }
                break;

            case 3 : //Tag number
                values.put(DB_Constants.ActiveUsersTable.TAG_NUM , activeUser.Tag_Num);
                break;

            case 4 : //is pause !
                if (activeUser.isPause){
                    values.put(DB_Constants.ActiveUsersTable.IS_RUN_PAUSE , 1);
                }else {
                    values.put(DB_Constants.ActiveUsersTable.IS_RUN_RESUME , 0);
                }
                break;

            case 5 : // is resuming ...
                if (activeUser.isResuming){
                    values.put(DB_Constants.ActiveUsersTable.IS_RUN_RESUME , 1);
                }else {
                    values.put(DB_Constants.ActiveUsersTable.IS_RUN_RESUME , 0);
                }
                break;
        }

        where = DB_Constants.ActiveUsersTable.USERNAME + " = ?" ;
        args[0] = String.valueOf(activeUser.Username_id);


        return db_user.update(DB_Constants.ActiveUsersTable.ACTIVE_USER_TABLE_NAME, values , where , args );
    }

    @Override
    public int Delete_ActiveUser(ActiveUser activeUser) {
        String [] args = {String.valueOf(activeUser.Username_id)};

        return db_user.delete(DB_Constants.ActiveUsersTable.ACTIVE_USER_TABLE_NAME,
                DB_Constants.ActiveUsersTable.USERNAME + " = ?"  , args );
    }

    @Override
    public ArrayList<ActiveUser> Show_Active_user() {

        ArrayList<ActiveUser> list ;
        ActiveUser activeUser;
        Cursor cursor ;
        cursor = db_user.query(DB_Constants.ActiveUsersTable.ACTIVE_USER_TABLE_NAME, null , null , null , null , null ,null);

        list = new ArrayList<>();

        if (cursor.getCount() == 0 ){
            activeUser = new ActiveUser();
            activeUser.null_flag = true ;
            list.add(activeUser);
        }else {

            cursor.moveToFirst();
            while (true) {
                activeUser = new ActiveUser();

                activeUser.Username_id = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.USERNAME));
                activeUser.active_UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.ACTIVE_USER_UID));
                activeUser.NumJoyStick = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.NUM_JOYSTICK));
                activeUser.Tag_Num = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.TAG_NUM));
                activeUser.Tv_Num = cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.TV_NUM));
                activeUser.startTime = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.START_TIME));
                activeUser.NAME = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.NAME));
                activeUser.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.LAST_NAME));
                activeUser.endTime = cursor.getString(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.END_TIME));
                activeUser.money = cursor.getLong(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.MONEY));
                activeUser.Remaining_Time = cursor.getLong(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.REMAINING_TIME));
                activeUser.Elapsed_time = cursor.getLong(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.ELAPSED_TIME));
                activeUser.null_flag = false ;
                if (cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.IS_RUNNING)) > 0 ){
                    activeUser.isRunning = true ;
                }else {
                    activeUser.isRunning = false ;
                }

                if (cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.IS_RUN_PAUSE)) > 0 ){
                    activeUser.isPause = true ;
                }else {
                    activeUser.isPause = false ;
                }

                if (cursor.getInt(cursor.getColumnIndex(DB_Constants.ActiveUsersTable.IS_RUN_RESUME)) > 0 ){
                    activeUser.isResuming = true ;
                }else {
                    activeUser.isResuming = false ;
                }

                list.add(activeUser);

                cursor.moveToNext();
                if (cursor.isAfterLast()) {
                    break;
                }
            }

        }
            cursor.close();


        return list;
    }

    @Override
    public ArrayList<User> show_user() {
        ArrayList<User> list ;
        User user;
        Cursor cursor ;
        cursor = db_user.query(DB_Constants.UsersInfoTable.USER_TABLE_NAME , null , null , null , null , null ,DB_Constants.UsersInfoTable.NAME + " ASC");

        list = new ArrayList<>();

        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            user = new User();
            user.NullFlag = true ;
            list.add(user);
        }else {
            while (true) {
                user = new User();

                user.NullFlag = false ;
                user.Name = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.NAME));
                user.LeftMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.LEFT_MONEY));
                user.TotalMoney = cursor.getLong(cursor.getColumnIndex(DB_Constants.UsersInfoTable.ToTAL_MONEY));
                user.Date = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.DATE));
                user.Phone = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.PHONE));
                user.UID = cursor.getInt(cursor.getColumnIndex(DB_Constants.UsersInfoTable.USER_UID));
                user.LastName = cursor.getString(cursor.getColumnIndex(DB_Constants.UsersInfoTable.Last_Name));

                list.add(user);

                cursor.moveToNext();
                if (cursor.isAfterLast()) {
                    break;
                }
            }
        }
        cursor.close();

        return list ;
    }


}
