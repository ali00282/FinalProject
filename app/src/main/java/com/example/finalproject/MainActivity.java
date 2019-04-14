package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.dictionary.Dict_EmptyActivity;
import com.example.finalproject.dictionary.Dict_MainActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button dictionaryButton;
    Button flightTrackerButton;
    Button newsFeedButton;
    Button articleSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictionaryButton = (Button)findViewById(R.id.dictionaryButton);
        flightTrackerButton = (Button)findViewById(R.id.flightTrackerButton);
        newsFeedButton = (Button)findViewById(R.id.newsFeedButton);
        articleSearchButton = (Button)findViewById(R.id.articleSearchButton);

        dictionaryButton.setOnClickListener(this);
        flightTrackerButton.setOnClickListener(this);
        newsFeedButton.setOnClickListener(this);
        articleSearchButton.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {

        Intent it = null;
        switch (v.getId()) {
            case R.id.dictionaryButton:
                it = new Intent(this, Dict_MainActivity.class);
                break;
            case R.id.flightTrackerButton:
                it = new Intent(this, Dict_MainActivity.class);
                break;
            case R.id.articleSearchButton:
                it = new Intent(this, Dict_MainActivity.class);
                break;
            case R.id.newsFeedButton:
                it = new Intent(this, Dict_EmptyActivity.class);
                break;
        }
        startActivity(it);
    }
}