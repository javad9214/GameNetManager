package com.western.game.center.westerngamecenter.DataBase;


public class DB_Constants {


    public static final String DATABASE_NAME = "User.db" ;
    public static final int DATABASE_VERSION = 1 ;



    public static class UsersInfoTable{

            public static final String USER_TABLE_NAME  = "user_info" ;
            public static final String USER_UID = "_id" ;
            public static final String Last_Name = "family_name" ;
            public static final String ToTAL_MONEY = "total_money" ;
            public static final String LEFT_MONEY = "left_money" ;
            public static final String DATE = "date" ;
            public static final String NAME = "name" ;
            public static final String PHONE = "phone" ;


            public static final String CREATE_ALARMS_TABLE_SYNTAX =
                    "create table " + USER_TABLE_NAME  + " ( " +
                            USER_UID + " integer primary key autoincrement ," +
                            Last_Name + " text ,"+
                            ToTAL_MONEY + " real ,"+
                            LEFT_MONEY + " real ," +
                            DATE + " text ," +
                            NAME + " text ," +
                            PHONE + " text " +
                            ");";


        }


    public static class ActiveUsersTable {

        public static final String ACTIVE_USER_TABLE_NAME = "active_user";
        public static final String ACTIVE_USER_UID = "_id";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String ELAPSED_TIME = "elapsed_time";
        public static final String IS_RUNNING = "is_running";
        public static final String IS_RUN_PAUSE = "is_run_pause";
        public static final String IS_RUN_RESUME = "is_run_resume";
        public static final String USERNAME = "username";
        public static final String NAME = "name";
        public static final String LAST_NAME = "last_name";
        public static final String MONEY = "money";
        public static final String REMAINING_TIME = "remaining_time";
        public static final String NUM_JOYSTICK = "num_joystick";
        public static final String TV_NUM = "tv_num";
        public static final String TAG_NUM = "tag_num" ;


        public static final String CREATE_ACTIVE_USER_TABLE_SYNTAX =
                "create table " + ACTIVE_USER_TABLE_NAME + " ( " +
                        ACTIVE_USER_UID + " integer primary key autoincrement ," +
                        USERNAME + " integer ," +
                        START_TIME + " text ,"+
                        END_TIME + " text ,"+
                        NAME + " text ,"+
                        LAST_NAME + " text ,"+
                        NUM_JOYSTICK + " integer ," +
                        TV_NUM + " integer ," +
                        MONEY + " real ," +
                        IS_RUNNING + " integer ," +
                        IS_RUN_PAUSE + " integer ," +
                        IS_RUN_RESUME + " integer ," +
                        TAG_NUM + " integer ," +
                        ELAPSED_TIME + " real ," +
                        REMAINING_TIME + " real " +
                        ");";
    }


}
