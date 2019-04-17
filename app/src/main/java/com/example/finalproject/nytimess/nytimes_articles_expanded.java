package com.example.finalproject.nytimess;

import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;



public class nytimes_articles_expanded extends AppCompatActivity {

    FrameLayout frmlyout;
    nytimes_search_fragment msgfragmnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frmlyout = new FrameLayout(this);

        frmlyout.setId(R.id.fragmentLocation);
        setContentView(frmlyout);

        msgfragmnt = new nytimes_search_fragment();
        msgfragmnt.setArguments(getIntent().getExtras());


        //Error: wrong 2nd argument type found
        //Solutiom: https://stackoverflow.com/questions/30339524/obj-fragment-wrong-2nd-argument-type-found-android-support-v4-app-fragment-re

/*        FragmentManager msgfragmnt = getSupportFragmentManager();
        msgfragmnt.beginTransaction().replace(R.id.fragmentLocation,msgfragmnt).commit();*/

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLocation, msgfragmnt);
        ft.commit();

    }
}
