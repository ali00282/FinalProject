package com.example.finalproject.newsfeed;

//imports
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.dictionary.Dict_MainActivity;
import com.example.finalproject.flighttracker.MainActivity;
import com.example.finalproject.nytimes.MainActivity_nytimess;


import java.util.ArrayList;

public class NewsFeedMain_activity extends AppCompatActivity {
    SharedPreferences edSearchSharedPref;
    TextView edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_feed);

        //searchbutton established
        Button searchBtn = findViewById(R.id.search_btn);


        // edit search established
        edSearch = findViewById(R.id.edit_search);

        //establishing sharedPref
        edSearchSharedPref = getSharedPreferences("searchedArticle", Context.MODE_PRIVATE);
        String savedString = edSearchSharedPref.getString("savedSearch", "");
        edSearch.setText(savedString);

        //applying onCickListener to listen to when search button is clicked
        searchBtn.setOnClickListener(c->{

            // when search button is clicked, NewsFeedSearches_acticity layout generated
            Intent nextPage = new Intent(NewsFeedMain_activity.this, NewsFeedSearches_activity.class );
            nextPage.putExtra("searchedArticle", edSearch.getText().toString());
            startActivity(nextPage);

        });

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar_hd);
        setSupportActionBar(toolBar);

        //arraylist that holds added numbered articles
        ArrayList<News_activity> newsArrayList = new ArrayList<News_activity>();

        newsArrayList.add(new News_activity("Saved Article", "djsaiudjasiudja", 1));

        newsArrayList.add(new News_activity("Article 1", "djsaiudjasiudja", 2));

        newsArrayList.add(new News_activity("Article 2", "djsaiudjasiudja", 3));


        NewsAdapter_activity newsAdt = new NewsAdapter_activity(newsArrayList, getApplicationContext());


        ListView listview = (ListView) findViewById(R.id.news_feed_list);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent nextPage = new Intent(NewsFeedMain_activity.this, NewsFeedSavedArticles_activity.class );
                    Log.i("ListView clicked: ", "0");
                    startActivity(nextPage);
                }

            }
        });
        listview.setAdapter(newsAdt);
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent nextPage = null;

        switch(item.getItemId())
        {

            //when the airplane icone is clicked on will go to flight tracker main activity
            case R.id.go_flight:
                nextPage = new Intent(NewsFeedMain_activity.this, MainActivityFlightStatusTracker.class);
                startActivity(nextPage);
                break;

            //when dictionary icon is clicked on, will go to the dictionary main activity
            case R.id.go_dic:
                nextPage = new Intent(NewsFeedMain_activity.this, Dict_MainActivity.class);
                startActivity(nextPage);
                break;


            //when new york icon is clicked on wil go to the new york times main activity
            case R.id.go_new_york:
                nextPage = new Intent(NewsFeedMain_activity.this, MainActivity_nytimess.class);
                startActivity(nextPage);
                break;

            //when the three dots indicating help / info is clicked on open help dialogue window
            case R.id.go_help:
                showDialog();
                break;
        }
        return true;
    }


    // inflating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_newsfeed_hd, menu);
        return true;
    }

    //instruction on how to use app along with info on author and version number
    private void showDialog() {

        View middle = getLayoutInflater().inflate(R.layout.activity_main_news_feed_help_popup, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Activity Version: 1.0\n" +
                "Author: Sagal Yusuf\n" +
                "Instructions for app use: Enter search term of the article. Click on article for further details.")
                .setNegativeButton("OK.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).setView(middle);

        builder.create().show();
    }
    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor editor = edSearchSharedPref.edit();

        String whatWasTyped = edSearch.getText().toString();

        editor.putString("savedSearch", whatWasTyped);

        editor.commit();
    }
}