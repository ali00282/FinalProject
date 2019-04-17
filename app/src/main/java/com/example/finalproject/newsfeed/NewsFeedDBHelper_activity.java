package com.example.finalproject.newsfeed;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsFeedDBHelper_activity extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "NewsFeedDB";
    public static final int VERSION_NUM = 1;

    /*
    indicated the column names
     */
    public static final String TABLE_NAME = "saved_news_feed";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "news_title";


    public NewsFeedDBHelper_activity(Activity ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }


    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT)";
        Log.i("Database onCreate:", "Query:" + sql);
        db.execSQL(sql);

        /*
        log message
         */
        Log.i("Database onCreate:", "Created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database upgrade:", " Old version:" + oldVersion + " newVersion:"+newVersion);

        /*
        Here deleting the old table
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        /*
        creating the new table
         */
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", " Old version:" + oldVersion + " newVersion:"+newVersion);

          /*
        Here deleting the old table
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

         /*
        creating the new table
         */
        onCreate(db);
    }



}