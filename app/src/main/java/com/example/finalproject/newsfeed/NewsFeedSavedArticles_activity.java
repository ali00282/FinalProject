package com.example.finalproject.newsfeed;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsFeedSavedArticles_activity extends AppCompatActivity {
    SavedWordsAdapter adapter;
    NewsFeedDBHelper_activity databaseHelper;
    SQLiteDatabase database;
    ArrayList<News_activity> newsArrayList;
    News_activity selectedID;



    public static final String ITEM_SELECTED = "ITEM";
    public static final int EMPTY_ACTIVITY = 345;

    public static final String ITEM_ID = "ID";
    public static final String ITEM_POSITION = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_saved_article);
        // there is no array list
        if (newsArrayList == null) {

            //then create one
            newsArrayList = new ArrayList<>();
        }

        //locate database
        databaseHelper = new NewsFeedDBHelper_activity(this);
        database = databaseHelper.getWritableDatabase();

        //locate all data
        this.findAllData(database);
        //locating the listView object
        ListView theList = (ListView)findViewById(R.id.saved_news_list_hd);

        //locating the fragment and check for fragmentlocation
        boolean isTablet = findViewById(R.id.fragmentLocation) != null;

        //initializing adapter
        adapter = new SavedWordsAdapter(newsArrayList);
        theList.setAdapter(adapter);

        theList.setOnItemClickListener( (list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, newsArrayList.get(position).getTitle());
            dataToPass.putInt(ITEM_POSITION, position);
            dataToPass.putLong(ITEM_ID, id);

            // if it is running on a tablet load the fragment
            if(isTablet)
            {
                NewsFeedDetailFragment_activity dFragment = new NewsFeedDetailFragment_activity();
                dFragment.setArguments( dataToPass );
                dFragment.setTablet(true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment)
                        .addToBackStack("AnyName")
                        .commit();
            }
            // if is running on a phone, passing data o the next activity
            else
            {
                Intent nextActivity = new Intent(NewsFeedSavedArticles_activity.this, NewsFeedEmptyFragment_activity.class);
                nextActivity.putExtras(dataToPass);
                startActivityForResult(nextActivity, EMPTY_ACTIVITY);
            }
        });
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EMPTY_ACTIVITY && resultCode == RESULT_OK) {
            long deletedId = data.getLongExtra("deletedId", 0);
            deleteMessageId((int)deletedId);
        }
    }

    public void deleteMessageId(int id)
    {
        Log.i("Delete this message:" , " id="+id);
        String str="";
        Cursor c;
        String [] cols = {NewsFeedDBHelper_activity.COL_ID, NewsFeedDBHelper_activity.COL_TITLE};
        c = database.query(false, NewsFeedDBHelper_activity.TABLE_NAME, cols, null, null, null, null, null, null);
        if(c.moveToFirst()) {
            for (int i =0; i<id; i++) {
                c.moveToNext();
            }
            str = c.getString(c.getColumnIndex(NewsFeedDBHelper_activity.COL_ID));
        }
        int x = database.delete(NewsFeedDBHelper_activity.TABLE_NAME, NewsFeedDBHelper_activity.COL_ID+"=?", new String[] {str});
        Log.i("ViewContact", "Deleted " + x + " rows");
        newsArrayList.remove(id);
        adapter.notifyDataSetChanged();
    }


    // finding the data and returning if there is a next item
    private void findAllData(SQLiteDatabase db){
        Log.e("FindAllData ", "reached");
        String [] columns = {NewsFeedDBHelper_activity.COL_ID, NewsFeedDBHelper_activity.COL_TITLE};
        Cursor results = db.query(false, NewsFeedDBHelper_activity.TABLE_NAME, columns, null, null, null, null, null, null);
        int idColIndex = results.getColumnIndex(NewsFeedDBHelper_activity.COL_ID);
        int contentColumnIndex = results.getColumnIndex(NewsFeedDBHelper_activity.COL_TITLE);
        while(results.moveToNext())
        {

            String Title = results.getString(contentColumnIndex);
            int id = results.getInt(idColIndex);
            String bdy = results.getString(contentColumnIndex);
            newsArrayList.add(new News_activity(Title, bdy ,id));
        }
    }


    protected class SavedWordsAdapter<E> extends BaseAdapter {
        private List<E> dataCopied = null;
        //data coped and kept
        public SavedWordsAdapter(List<E> originalData) {
            dataCopied = originalData;
        }

        //passing an array
        public SavedWordsAdapter(E[] array) {
            dataCopied = Arrays.asList(array);
        }

        public SavedWordsAdapter() {
        }

        // returning size of items to show
        @Override
        public int getCount()
        {
            return dataCopied.size();
        }

        // return the contents will show up in each row
        @Override
        public E getItem(int position) {
            return dataCopied.get(position);
        }

        @Override
        public View getView(int location, View prev, ViewGroup parent) {
            View newView = null;
            LayoutInflater inflater = getLayoutInflater();
            selectedID = (News_activity)getItem(location);
            newView = inflater.inflate(R.layout.activity_news_feed_word_list_item, parent, false);
            TextView contentView = (TextView) newView.findViewById(R.id.news_title_detailed);
            String content = ((News_activity) getItem(location)).getTitle();
            contentView.setText(content);
            return newView;
        }
        //locating item location
        @Override
        public long getItemId(int position) {
            return (long)position;
        }
    }
}