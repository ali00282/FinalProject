package com.example.finalproject.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;

import java.util.ArrayList;

public class Dict_MainActivity extends AppCompatActivity {

    ArrayList<WordModel> wordModels = new ArrayList<>();
    private static int DICT_EMPTY_ACTIVITY = 2;
    MyOwnAdapter myOwnAdapter;
    //get a database:
    DictDBHelper dbOpener;
    SQLiteDatabase db;
    ListView theList;
    ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    Toolbar     toolbar;
    SharedPreferences sp;
    TextView recentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict__main);
        boolean isTablet = findViewById(R.id.fragment) != null; //check if the FrameLayout is loaded
        EditText searchText = (EditText)findViewById(R.id.search);
        Button searchButton = (Button)findViewById(R.id.searchButton);
        theList = (ListView)findViewById(R.id.theList);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recentWord = (TextView)findViewById(R.id.recentSearch);
        setSupportActionBar(toolbar);


        sp = getSharedPreferences("wordSaved", Context.MODE_PRIVATE);
        String savedString = sp.getString("wordSaved", "No thing");
        recentWord.setText(savedString);
        //add back navigation button
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        dbOpener = new DictDBHelper(this);
        db = dbOpener.getWritableDatabase();

        queryData();

        searchButton.setOnClickListener(clk -> {
            if (searchText.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Your input is invalid, enter again to search", Toast.LENGTH_LONG).show();
            }else {

                Bundle dataToPass = new Bundle();
                dataToPass.putString("wordSearch", searchText.getText().toString());
                //get an editor object
                SharedPreferences.Editor editor = sp.edit();

                //save what was typed under the name "ReserveName"
                String whatWasTyped = searchText.getText().toString();
                editor.putString("wordSaved", whatWasTyped);

                if (isTablet){
                    Dict_DetailFragment fragment =  new Dict_DetailFragment();
                    fragment.setArguments(dataToPass);
                    fragment.setTablet(true);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, fragment) //Add the fragment in FrameLayout
                            .addToBackStack("AnyName") //make the back button undo the transaction
                            .commit(); //actually load the fragment.
                } else {
                    Intent nextActivity = new Intent(Dict_MainActivity.this, Dict_EmptyActivity.class);
                    nextActivity.putExtras(dataToPass); //send data to next activity
                    startActivityForResult(nextActivity, DICT_EMPTY_ACTIVITY); //make the transition
                }
            }
        });

        theList.setOnItemClickListener( (list, item, position, id) -> {


            Bundle dataToPass = new Bundle();
            dataToPass.putString("wordSave", wordModels.get(position).getWord());
            dataToPass.putString("pronunciationSave", wordModels.get(position).getPronunciation());
            dataToPass.putString("definitionsSave", wordModels.get(position).getDefinition());
            dataToPass.putLong("idSave", wordModels.get(position).getId());


            if (isTablet){
                Dict_DetailFragment fragment =  new Dict_DetailFragment();
                fragment.setArguments(dataToPass);
                fragment.setTablet(true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment, fragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            } else {
                Intent nextActivity = new Intent(Dict_MainActivity.this, Dict_EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivityForResult(nextActivity, DICT_EMPTY_ACTIVITY); //make the transition
            }

        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're coming back from the view contact activity
        if(requestCode == DICT_EMPTY_ACTIVITY)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra("wordID", 0);
                deleteWord(id);
            }else if (resultCode == 35){
                insertWord(data.getStringExtra("word"),data.getStringExtra("pronunciation"),data.getStringExtra("definitions"));
            }
        }
    }

    //This class needs 4 functions to work properly:
    protected class MyOwnAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return wordModels.size();
        }

        public WordModel getItem(int position){
            return wordModels.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.dict_row, parent, false );

            WordModel thisRow = getItem(position);
            TextView rowWord = (TextView)newView.findViewById(R.id.rowWord);


            rowWord.setText(thisRow.getWord());
            //return the row:
            return newView;
        }

        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }

    public void deleteWord(long id)
    {
        db.delete(DictDBHelper.TABLE_NAME, DictDBHelper.COL_ID + "=?", new String[] {Long.toString(id)});
        wordModels.clear();
        queryData();
        //        wordModels.remove(id);
//        myOwnAdapter.notifyDataSetChanged();
    }

    public long insertWord(String word, String pronunciation, String definitions){
        ContentValues newRowValues = new ContentValues();
        newRowValues.put(DictDBHelper.COL_WORD, word);
        newRowValues.put(DictDBHelper.COL_PRONUNCIATION, pronunciation);
        newRowValues.put(DictDBHelper.COL_DEFINITION, definitions);

        long id = db.insert(DictDBHelper.TABLE_NAME, null, newRowValues);
        if (id > 0){
//            wordModels.add(new WordModel(word, pronunciation, definitions, id));
//            myOwnAdapter.notifyDataSetChanged();
            wordModels.clear();
            queryData();
        }
        return id;
    }

    private void queryData(){


        //query all the results from the database:
        String [] columns = {DictDBHelper.COL_ID, DictDBHelper.COL_WORD, DictDBHelper.COL_PRONUNCIATION, DictDBHelper.COL_DEFINITION};
        Cursor results = db.query(false, DictDBHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int wordColumnIndex = results.getColumnIndex(DictDBHelper.COL_WORD);
        int pronunciationColIndex = results.getColumnIndex(DictDBHelper.COL_PRONUNCIATION);
        int definitionColIndex = results.getColumnIndex(DictDBHelper.COL_DEFINITION);
        int idColIndex = results.getColumnIndex(DictDBHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 4) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus*25);
                            if (progressStatus == 3)
                                progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        while(results.moveToNext())
        {
            String word = results.getString(wordColumnIndex);
            String pronunciation = results.getString(pronunciationColIndex);
            String definitions = results.getString(definitionColIndex);
            long id = results.getLong(idColIndex);
            System.out.println(results.getPosition());

            //add the new Contact to the array list:
            wordModels.add(new WordModel(word, pronunciation, definitions, id));
        }



        myOwnAdapter = new MyOwnAdapter();
        theList.setAdapter(myOwnAdapter);
    }



}

