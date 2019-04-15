package com.example.finalproject.newsfeed;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsFeedSearches_activity extends AppCompatActivity {
    private TextView titleView, uuidView;
    private ProgressBar progressBar;
    String preUrl = "http://webhose.io/filterWebContent?token=86940a5c-b094-4465-942e-81ce096fe5c9&format=xml&sort=relevancy&q=";
    String postUrl = "%20market%20language%3Aenglish";
    NewsFeedDBHelper_activity dbHelper;
    SQLiteDatabase db;
    String URL;
    String searchedTerm;
    public String titleAtt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_searches);

        // this is to obtain the searched term
        Intent previousPage = getIntent();
        searchedTerm = previousPage.getStringExtra("searchedArticle");
        URL = preUrl + searchedTerm + postUrl;

        NewsFeedQuery wq = new NewsFeedQuery();
        wq.execute(URL);
        progressBar = findViewById(R.id.progressBar_hd);
        progressBar.setVisibility(View.VISIBLE);
        titleView = findViewById(R.id.title_hd);
        uuidView = findViewById(R.id.uuid_hd);

        // these indicate our used buttons, saved, delete and return
        Button saveBtn = (Button)findViewById(R.id.savebtn_hd);
        Button delBtn = (Button)findViewById(R.id.deletebtn_hd);
        Button retBtn = (Button)findViewById(R.id.returnbtn_hd);

        // here listening for the save button and saving in database
        saveBtn.setOnClickListener(c->{
            showToast("Article Saved.");

            // this inidactes locating the database
            dbHelper = new NewsFeedDBHelper_activity(this);
            db = dbHelper.getWritableDatabase();
            this.saveWord(db, titleAtt);
        });


        // here listening for the delete button and deleting from database
        delBtn.setOnClickListener(c->{
            alertDelete();
        });


        //here listening fot the return button
        retBtn.setOnClickListener(c->{

            // and initiating snackbar if button is pressed
            Snackbar sb = Snackbar.make(retBtn, "Go back to previous page? ", Snackbar.LENGTH_LONG)

                    .setAction("Yes.", e -> Log.e("Toast", "Clicked return"));

            sb.setAction("Yes.",f -> finish());

            sb.show();
        });
    }


    //Async and its three parameter types to query data from the internet
    private class NewsFeedQuery extends AsyncTask<String, Integer, String>
    {

        //here is the uuid value
        public String uuid;

        // here attmepting to create connection
        @Override
        protected String doInBackground(String... params) {

            try {

                String myUrl = params[0];
                URL url = new URL(myUrl);

                // connecting to the http server
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 );
                connection.setConnectTimeout(15000  );
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                InputStream inStream = connection.getInputStream();
                int responseCode = connection.getResponseCode();

                //determine if it is a failed respose or successful one
                if (responseCode == 200) {
                    Log.e("Conection to WEBHOSE", "Successful" );
                } else {
                    Log.e("Conection to WEBHOSE", "FAILED" );
                }

                connection.connect();

                //pull parse using the factory pattern
                XmlPullParserFactory xppFactory = XmlPullParserFactory.newInstance();
                xppFactory.setNamespaceAware(false);
                XmlPullParser xpp = xppFactory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");


                //here looping over the webhose.io XML:
                while(xpp.getEventType() != XmlPullParser.END_DOCUMENT)
                { if(xpp.getEventType() == XmlPullParser.START_TAG)
                {String tagName = xpp.getName();
                    if(tagName.equals("uuid")) {
                        if(xpp.next() == XmlPullParser.TEXT) {
                            uuid = xpp.getText();
                            Log.e("AsyncTask", "Parameter found for uuid " + uuid);
                        }

                        publishProgress(15);

                    }
                    else if(tagName.equals("title")) { if(xpp.next() == XmlPullParser.TEXT) {
                        titleAtt = xpp.getText();
                        Log.e("AsyncTask", "Found parameter titleAtt: " + titleAtt);
                    }

                        publishProgress(25);

                    }
                }
                    xpp.next();
                }

            }catch (Exception ex)
            {
                Log.e("Crash!!", ex.getMessage() );
            }
            return "Finished task";
        }

        // to update an progress indicators,
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("AsyncTask", "update:" + values[0]);

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(15);
            progressBar.setProgress(25);
            progressBar.setProgress(50);
            progressBar.setProgress(75);
            progressBar.setProgress(100);
            progressBar.setMax(100);
        }

        //Function of AsyncTask
        @Override
        protected void onPostExecute(String args) {
            Log.i("AsyncTask", "onPostExecute" );
            titleView.setText(titleAtt);
            uuidView.setText(uuid);
        }
    }
    // to obtain the searchTerm, and inseart into database
    private void saveWord(SQLiteDatabase db, String inputWord){

        ContentValues newRowValues = new ContentValues();
        newRowValues.put(NewsFeedDBHelper_activity.COL_TITLE, inputWord);
        long newId = db.insert(NewsFeedDBHelper_activity.TABLE_NAME, null, newRowValues);
        String saveResultMessage = "";
        if(newId>0){
            saveResultMessage = "News_activity saved successfully!.";
        }else{
            saveResultMessage = "NOT SAVED!.";
        }

        //idicating it was successful
        Toast.makeText(NewsFeedSearches_activity.this, saveResultMessage, Toast.LENGTH_LONG).show();
    }

    // confirm whether would like to go ahead with delete
    public void alertDelete() {
        View middle = getLayoutInflater().inflate(R.layout.activity_news_feed_popup_delete, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete article?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                        showToast("Article Has Been Successfully Deleted.");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //else
                    }
                }).setView(middle);
        builder.create().show();
    }
    //here we have the toast message
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



}