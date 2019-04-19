package com.example.finalproject.flighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ViewSavedFlights extends AppCompatActivity {

    MySqliteHandler db;
    private ListView ls;
    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_flights);

        db = new MySqliteHandler(this);

        ArrayList<FlightDetails> ar  = db.getAllFlights();

        ls = (ListView) findViewById(R.id.flightls);
        ls.setEmptyView(findViewById(R.id.textView6));

        FlightListView adapter = new FlightListView(ViewSavedFlights.this);
        adapter.setData(ar);
        ls.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
