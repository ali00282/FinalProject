package com.example.finalproject.flighttracker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class MyCustomDialog extends Dialog {

     Activity a1;
     Dialog d;
     Button close;

    public MyCustomDialog(Activity a) {
        super(a);
        this.a1 = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialoglayout);

    }

}