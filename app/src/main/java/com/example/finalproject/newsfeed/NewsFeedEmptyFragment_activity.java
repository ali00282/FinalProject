package com.example.finalproject.newsfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.finalproject.R;


public class NewsFeedEmptyFragment_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_empty_activity);

        //get the data that was passed from FragmentExample
        Bundle dataToPass = getIntent().getExtras();

        //This is copied directly from FragmentExample.java lines 47-54
        NewsFeedDetailFragment_activity dFragment = new NewsFeedDetailFragment_activity();


        //fragment gets data passed to it
        dFragment.setArguments( dataToPass );

        //fragment not on a tablet device so on a phone
        dFragment.setTablet(false);

        getSupportFragmentManager()

                .beginTransaction()

                .add(R.id.fragmentLocation, dFragment)

                .addToBackStack("AnyName")

                .commit();
    }
}