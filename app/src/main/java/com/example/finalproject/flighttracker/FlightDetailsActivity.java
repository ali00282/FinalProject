package com.example.finalproject.flighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Used for Displaying the Details of the Flight
 */
public class FlightDetailsActivity extends AppCompatActivity {
    MySqliteHandler db;
    FlightDetails fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
         fd = (FlightDetails)getIntent().getExtras().get("data");
        System.out.println("data : " + fd.getAirlineIata());
        db = new MySqliteHandler(FlightDetailsActivity.this);
        TextView deptv = findViewById(R.id.a2);
        TextView arrtv = findViewById(R.id.a4);
        TextView airtv = findViewById(R.id.a6);
        TextView lattv = findViewById(R.id.a8);
        TextView lontv = findViewById(R.id.a10);
        TextView dirtv = findViewById(R.id.a12);
        TextView altv = findViewById(R.id.a14);
        TextView horspeedtv = findViewById(R.id.a16);
        TextView statustv = findViewById(R.id.a18);
        TextView verspeedtv = findViewById(R.id.a20);

        deptv.setText(fd.getDepiata());
        arrtv.setText(fd.getArriata());
        airtv.setText(fd.getAirlineIata());
        lattv.setText(fd.getLat());
        lontv.setText(fd.getLon());
        dirtv.setText(fd.getDirection());
        altv.setText(fd.getAltitude());
        horspeedtv.setText(fd.getSpeedhorizontal());
        verspeedtv.setText(fd.getSpeedvertical());
        statustv.setText(fd.getStatus());

        Button b = findViewById(R.id.button2);

        //Button Click Code
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addFlight(fd);
                Toast.makeText(FlightDetailsActivity.this,"SAVED",Toast.LENGTH_LONG).show();

                Intent i = new Intent(FlightDetailsActivity.this, ViewSavedFlights.class);
                startActivity(i);
            }
        });
    }
}
