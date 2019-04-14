package com.example.finalproject.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.finalproject.R;

public class Dict_EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict__empty);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        Dict_DetailFragment fragment =  new Dict_DetailFragment();
        fragment.setArguments(dataToPass);
        fragment.setTablet(false);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, fragment) //Add the fragment in FrameLayout
                .addToBackStack("AnyName") //make the back button undo the transaction
                .commit(); //actually load the fragment.
    }
}

