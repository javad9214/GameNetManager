package com.western.game.center.westerngamecenter.DataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase_OpenHelper extends SQLiteOpenHelper{


    public DataBase_OpenHelper(Context context) {
        super(context,DB_Constants.DATABASE_NAME, null , DB_Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_Constants.UsersInfoTable.CREATE_ALARMS_TABLE_SYNTAX);
        db.execSQL(DB_Constants.ActiveUsersTable.CREATE_ACTIVE_USER_TABLE_SYNTAX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
