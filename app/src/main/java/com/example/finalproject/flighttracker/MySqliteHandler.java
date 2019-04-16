package com.example.finalproject.flighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MySqliteHandler extends SQLiteOpenHelper {
     static final int DATABASE_VERSION = 1;
     static final String DATABASE_NAME = "flight.db";
     static final String TABLE_FLIGHT = "tbflight";
     static final String COLUMN_ID = "id";
     static final String COLUMN_DEPIATA= "depiata";
     static final String COLUMN_ARRIATA= "arriata";
     static final String COLUMN_AIRLINEIATA="airlineIata";
     static final String COLUMN_LAT = "lat";
     static final String COLUMN_LON= "lon";
     static final String COLUMN_DIRECTION="direction";
     static final String COLUMN_ALITIUDE="altitude";
     static final String COLUMN_SPEEDHOR= "speedhorizontal";
     static final String COLUMN_SPEEDVER="speedvertical";
     static final String COLUMN_STATUS= "status";

    String CREATE_FLIGHT_TABLE = "CREATE TABLE " + TABLE_FLIGHT + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_DEPIATA + " TEXT, " +
            COLUMN_ARRIATA + " TEXT, "+ COLUMN_AIRLINEIATA + " TEXT, "+
          COLUMN_LAT  + " TEXT, "+ COLUMN_LON + " TEXT, "+
            COLUMN_DIRECTION + " TEXT, "+ COLUMN_ALITIUDE   + " TEXT, "+
            COLUMN_SPEEDHOR  + " TEXT, "+ COLUMN_SPEEDVER +" TEXT, "+ COLUMN_STATUS
            + " TEXT)";

    public MySqliteHandler(Context context) {
        //CursorFactory --> null
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    //called When database is created for the first time
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FLIGHT_TABLE);
    }

    @Override
    //Called when the database needs to be upgraded.
    //When we update our app or change our database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT);
        onCreate(db);
    }
    // All Database Operations: create, read, delete
    // create
    public void addFlight(FlightDetails fd) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DEPIATA,fd.getDepiata());
        values.put(COLUMN_ARRIATA,fd.getArriata());
        values.put(COLUMN_AIRLINEIATA,fd.getAirlineIata());
        values.put(COLUMN_LAT ,fd.getLat());
        values.put(COLUMN_LON,fd.getLon());
        values.put(COLUMN_DIRECTION,fd.getDirection());
        values.put(COLUMN_ALITIUDE,fd.getAltitude());
        values.put(COLUMN_SPEEDHOR,fd.getSpeedhorizontal());
        values.put(COLUMN_SPEEDVER,fd.getSpeedvertical());
        values.put( COLUMN_STATUS,fd.getStatus());

        //Name of table, nullColumnHack, ContentValues
        database.insert(TABLE_FLIGHT, null, values);
        database.close();
    }

    public FlightDetails getFlight(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_FLIGHT, new String[]{COLUMN_ID , COLUMN_DEPIATA,COLUMN_ARRIATA, COLUMN_AIRLINEIATA, COLUMN_LAT, COLUMN_LON, COLUMN_DIRECTION, COLUMN_ALITIUDE, COLUMN_SPEEDHOR, COLUMN_SPEEDVER, COLUMN_STATUS},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)  {
            cursor.moveToFirst();
        }
        FlightDetails fd = new FlightDetails(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8),
                cursor.getString(9), cursor.getString(10)
                );
        database.close();
        return fd;
    }
    // Getting all Objects
    public ArrayList<FlightDetails> getAllFlights() {
        ArrayList<FlightDetails> flightlist = new ArrayList<FlightDetails>();

        String selectAllQuery = "SELECT * FROM " + TABLE_FLIGHT;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectAllQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FlightDetails fd = new FlightDetails(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10)
                );
                flightlist.add(fd);
            } while (cursor.moveToNext());
        }
        database.close();
        return flightlist;
    }

    // Deleteing a single Flight
    public void deleteFlight(FlightDetails fd) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_FLIGHT, COLUMN_ID + " = ?",
                new String[]{String.valueOf(fd.getId())});
        database.close();
    }
    // Getting the number of Flights

    public int getFlightCount() {
        String CountQuery = "SELECT * FROM " + TABLE_FLIGHT;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(CountQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
