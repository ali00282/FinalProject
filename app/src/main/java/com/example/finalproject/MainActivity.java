package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.dictionary.Dict_MainActivity;

public class MainActivity extends AppCompatActivity {

    Button dictionayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictionayButton = (Button)findViewById(R.id.dictionaryButton);

        Intent it = new Intent(this, Dict_MainActivity.class);
        startActivityForResult(it,345);

    }
}
