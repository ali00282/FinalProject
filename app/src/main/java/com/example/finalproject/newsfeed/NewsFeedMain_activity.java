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

import java.util.ArrayList;

public class NewsFeedMain_activity extends AppCompatActivity {
    SharedPreferences edSearchSharedPref;
    TextView edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_feed);

        //searchbutton and editsearch
        Button searchBtn = findViewById(R.id.search_btn);
        edSearch = findViewById(R.id.edit_search);

        edSearchSharedPref = getSharedPreferences("searchedArticle", Context.MODE_PRIVATE);
        String savedString = edSearchSharedPref.getString("savedSearch", "");
        edSearch.setText(savedString);

        searchBtn.setOnClickListener(c->{
            Intent nextPage = new Intent(NewsFeedMain_activity.this, NewsFeedSearches_activity.class );
            nextPage.putExtra("searchedArticle", edSearch.getText().toString());
            startActivity(nextPage);
            //startActivityForResult(nextPage, 30);
        });

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar_hd);
        setSupportActionBar(toolBar);

        //arraylist of articles
        ArrayList<News_activity> newsArrayList = new ArrayList<News_activity>();
        newsArrayList.add(new News_activity("Saved Article", "djsaiudjasiudja", 1));
        newsArrayList.add(new News_activity("Article 1", "djsaiudjasiudja", 2));
        newsArrayList.add(new News_activity("Article 2", "djsaiudjasiudja", 3));

        NewsAdapter_activity newsAdt = new NewsAdapter_activity(newsArrayList, getApplicationContext());

        //list view of article set to clickable
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

            case R.id.go_help:
                showDialog();
                break;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu_newsfeed_hd, menu);
        return true;
    }

    /**
     * show help dialog of toolbar(overflow)
     */
    private void showDialog() {
        //pop up custom dialog to show Activity Version, Author, and how to use news feed
        View middle = getLayoutInflater().inflate(R.layout.activity_main_news_feed_help_popup, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Activity Version: 1.0\n" +
                "Author: Sagal Yusuf\n" +
                "Instructions for app use: Enter search term of the article. Click on article for further details.")
/*                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })*/
                .setNegativeButton("OK.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }

    /**
     * shared prefs commit
     */
    @Override
    protected void onPause(){
        super.onPause();
        //get an editor object
        SharedPreferences.Editor editor = edSearchSharedPref.edit();

        //save what was typed under the name "editSearch"
        String whatWasTyped = edSearch.getText().toString();
        // xml tag name is editSearch
        editor.putString("savedSearch", whatWasTyped);

        //write it to disk:
        editor.commit();
    }
}