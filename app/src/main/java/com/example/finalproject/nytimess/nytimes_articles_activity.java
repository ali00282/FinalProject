package com.example.finalproject.nytimess;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;



public class nytimes_articles_activity extends AppCompatActivity {


    //public static ListAdapter savedAdapter;
    nytimes_DataBaseHelper databaseOpener;

    public static SQLiteDatabase db;

    WebView wbview;

    Toolbar tlbar;

    Cursor storing_result;

    MenuInflater menuinflater;

    String news_site_address;

    String news_header_title;

    public static String news_picture;

    long anewid;

    ContentValues newrowcontentvalues;

    nytimes_articles_fetch article;

    public static ArrayList<nytimes_articles_fetch> saved_Articles = new ArrayList<nytimes_articles_fetch>();

    public static nytimes_saved_arrayops opsAdapter;



    public static void createAdapter(Context cntxt) {
        opsAdapter = new nytimes_saved_arrayops(cntxt, saved_Articles);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nytimes_activity_articles);
        tlbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tlbar);
        opsAdapter = new nytimes_saved_arrayops(this, saved_Articles);
        article = (nytimes_articles_fetch) getIntent().getSerializableExtra("article");
        wbview = findViewById(R.id.article_web_view);


        wbview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                final boolean b = true;
                return b;

            }
        });

        wbview.loadUrl(article.getfetchurl());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.newyorktimes_menu_search, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info_details:
                infoDialog();
                break;
            case R.id.save_icon:
                onSaveIcon();
                Toast.makeText(this, getResources().getString(R.string.article_saving), Toast.LENGTH_LONG).show();
                break;
        }
        return false;

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    public void onSaveIcon() {

        databaseOpener = new nytimes_DataBaseHelper(this);
        db = databaseOpener.getWritableDatabase();

        String[] columns = {nytimes_DataBaseHelper.COL_ID, nytimes_DataBaseHelper.COL_HEADER, nytimes_DataBaseHelper.COL_URL, nytimes_DataBaseHelper.COL_PIC_URL};
        storing_result = db.query(false, nytimes_DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        news_header_title = article.getfetchheadline();
        news_site_address = article.getfetchurl();
        news_picture = article.getfetchthumbnail();


        newrowcontentvalues = new ContentValues();

        newrowcontentvalues.put(nytimes_DataBaseHelper.COL_HEADER, news_header_title);
        newrowcontentvalues.put(nytimes_DataBaseHelper.COL_URL, news_site_address);
        newrowcontentvalues.put(nytimes_DataBaseHelper.COL_PIC_URL, news_picture);
        anewid = db.insert(nytimes_DataBaseHelper.TABLE_NAME, null, newrowcontentvalues);

        saved_Articles.add(article);
        opsAdapter.notifyDataSetChanged();
    }


    public void infoDialog() {


        View middle_info = getLayoutInflater().inflate(R.layout.nytimes_activity_showdialoginfo, null);

        AlertDialog.Builder   builder = new AlertDialog.Builder(this);

        builder.setMessage("").setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {


                onPause();
            }
        }).setView(middle_info);



        builder.create().show();
    }


}