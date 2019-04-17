package com.example.finalproject.nytimess;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


// this activity loads  saved articles into Listview

public class nytimes_articles_saved extends AppCompatActivity {

    protected boolean isTablet;
    ListView listvw1 = (ListView) findViewById(R.id.listviewer1);
    nytimes_search_fragment fragmsg;
    nytimes_DataBaseHelper databasebOpener = new nytimes_DataBaseHelper(this);
    public static SQLiteDatabase db;
    nytimes_articles_fetch fetcharticle;

    //initializing for both ph and tablets

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newyorktimes_saved_articles);


        nytimes_articles_activity.createAdapter(nytimes_articles_saved.this);
        listvw1.setAdapter(nytimes_articles_activity.opsAdapter);


        db = databasebOpener.getWritableDatabase();
        if ((findViewById(R.id.fragmentLocation) != null)) isTablet = true;
        else {
            isTablet = false;
        }


        listvw1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bl;
                bl = new Bundle();


                bl.putString("url", nytimes_articles_activity.opsAdapter.getItem(position).getfetchurl());
                bl.putString("headline", nytimes_articles_activity.opsAdapter.getItem(position).getfetchheadline());
                bl.putString("pic_url", nytimes_articles_activity.opsAdapter.getItem(position).getfetchthumbnail());

                bl.putInt("id", nytimes_articles_activity.opsAdapter.getPosition(nytimes_articles_activity.opsAdapter.getItem(position)));


                if (isTablet) {
                    fragmsg = new nytimes_search_fragment(nytimes_articles_saved.this);
                    fragmsg.setArguments(bl);


                    //Error: wrong 2nd argument type found
                    //Solutiom: https://stackoverflow.com/questions/30339524/obj-fragment-wrong-2nd-argument-type-found-android-support-v4-app-fragment-re


                   /* FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction();
                    fragmentManager(R.id.fragmentLocation,fragmsg).commit();
*/
                    FragmentTransaction ft;
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentLocation, fragmsg);
                    ft.commit();


                } else
                    {

                    Intent msgdetailin = new Intent(getApplicationContext(), nytimes_articles_expanded.class);

                    msgdetailin.putExtras(bl);

                    startActivityForResult(msgdetailin, CONTEXT_INCLUDE_CODE);
                }
            }
        });

    }


    public void deleteItem(int delid) {
        db.delete(nytimes_DataBaseHelper.TABLE_NAME, nytimes_DataBaseHelper.COL_ID + "=" + delid, null);

        nytimes_articles_fetch post = nytimes_articles_activity.opsAdapter.getItem(delid);

        nytimes_articles_activity.saved_Articles.remove(post);

        nytimes_articles_activity.opsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentdata) {

        if (resultCode == CONTEXT_INCLUDE_CODE) {

            int messageID = intentdata.getIntExtra("msgID", -1);

            deleteItem(messageID);
        }
    }
























}
