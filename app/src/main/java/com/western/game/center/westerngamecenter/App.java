package com.western.game.center.westerngamecenter;


import android.app.Application;

import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;

public class App extends Application {

    private static DataBase_Operation dataBaseOperation;

    @Override
    public void onCreate() {
        super.onCreate();

        dataBaseOperation = new DataBase_Operation(this);
    }

    public static DataBase_Operation getDataBaseOperation(){return dataBaseOperation ; }
}
