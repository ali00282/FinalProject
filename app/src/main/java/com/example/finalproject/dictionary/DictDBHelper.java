package com.example.finalproject.dictionary;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Dictionary";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Dictionaries";
    public static final String COL_ID = "ID";
    public static final String COL_WORD = "WORD";
    public static final String COL_PRONUNCIATION = "PRONUNCIATION";
    public static final String COL_DEFINITION = "DEFINITION";

    public DictDBHelper(Activity ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db)
    {
        //Make sure you put spaces between SQL statements and Java strings:
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_WORD + " TEXT,  "+ COL_PRONUNCIATION + " TEXT, " + COL_DEFINITION + " TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }


}
