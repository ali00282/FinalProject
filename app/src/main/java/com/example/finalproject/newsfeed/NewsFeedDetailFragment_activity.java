package com.example.finalproject.newsfeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

/*
creating different layout depending on the device
 */
public class NewsFeedDetailFragment_activity extends Fragment {

    //if its a tablet

    private boolean ifTablet;

    //if it is a bundle from a previous page

    private Bundle dataFromPrevActivity;

    // this is for id from previous page
    private long prevId;


    //method to set the tablet
    public void setTablet(boolean tablet) {

        // in the case that it is a tablet
        ifTablet = tablet;
    }
    //when the emptyFragment page is launched, methods inflater, container and savedInstance will run

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // this is to obtain argument from the previous page
        dataFromPrevActivity = getArguments();

        // this is where we inflate the arguement
        View result =  inflater.inflate(R.layout.activity_news_feed_detail_fragment, container, false);


        prevId = dataFromPrevActivity.getLong(NewsFeedSavedArticles_activity.ITEM_ID );
        TextView idView = (TextView)result.findViewById(R.id.idText);
        idView.setText("ID is "+ prevId);

        //this is to display the show the word content
        TextView contentView = (TextView)result.findViewById(R.id.news_title);
        idView.setText(dataFromPrevActivity.getString(NewsFeedSavedArticles_activity.ITEM_SELECTED));

        // adding a clickListenenr to listen to the deleteButton
        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( clk -> {

            // if it a tablet that is being used then detail and list will be shown on screen

            if(ifTablet) {
                NewsFeedSavedArticles_activity parent = (NewsFeedSavedArticles_activity)getActivity();

                //here the item will be deleted and list updated
                parent.deleteMessageId((int)prevId);

                //here fragment is being removed since it was removed from database
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();

                //this is to head back
                parent.finish();
            }
            //if a phone is being used
            else
            {
                NewsFeedEmptyFragment_activity parent = (NewsFeedEmptyFragment_activity) getActivity();
                Intent backToFragmentExample = new Intent();
                long myID = dataFromPrevActivity.getLong(NewsFeedSavedArticles_activity.ITEM_ID);
                backToFragmentExample.putExtra("deletedId", myID);
                parent.setResult(Activity.RESULT_OK, backToFragmentExample);
                parent.finish();
            }
        });

        
        return result;
    }
}