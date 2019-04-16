package com.example.finalproject.flighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ViewSavedFlightDetailsActivity extends AppCompatActivity {

    MySqliteHandler db;
    FlightDetails fd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_flight_details);

        fd = (FlightDetails)getIntent().getExtras().get("data");
        System.out.println("data : " + fd.getAirlineIata());
        db = new MySqliteHandler(ViewSavedFlightDetailsActivity.this);

        TextView deptv = findViewById(R.id.ba2);
        TextView arrtv = findViewById(R.id.ba4);
        TextView airtv = findViewById(R.id.ba6);
        TextView lattv = findViewById(R.id.ba8);
        TextView lontv = findViewById(R.id.ba10);
        TextView dirtv = findViewById(R.id.ba12);
        TextView altv = findViewById(R.id.ba14);
        TextView horspeedtv = findViewById(R.id.ba16);
        TextView statustv = findViewById(R.id.ba18);
        TextView verspeedtv = findViewById(R.id.ba20);

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

        Button b = findViewById(R.id.bbutton2);

        //Button Click Code
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFlight(fd);

                Snackbar snackbar = Snackbar
                        .make( v, "Deleted Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent(ViewSavedFlightDetailsActivity.this, ViewSavedFlights.class);
                         startActivity(i);
                    }
                }, 2 * 1000);

            }
        });
    }
}
