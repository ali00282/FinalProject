
package com.example.finalproject.nytimess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity
{


    /**
     * The New york button.
     */
    ImageButton launchAppButton;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        launchAppButton = findViewById(R.id.launchbutton);



        launchAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),  nytimes_main.class);

                startActivity(i);
        }
        });




    }
}
