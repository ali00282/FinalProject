package com.example.finalproject.flighttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText et;
    ProgressBar pb;
    Button b;
    SharedPreferences sp;
    public boolean onCreateOptionsMenu(Menu m) {
        //inflate the xml file into m
        getMenuInflater().inflate(R.menu.menu_main,m);
        return true;
    }
    @Override
    //When user selects the item from the menubar
    //selected MenuItem is passed as reference
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==R.id.m1)
        {
            Intent i = new Intent(this, ViewSavedFlights.class);
            startActivity(i);
        }
        if(id==R.id.m2)
        {
            MyCustomDialog mcd = new MyCustomDialog(this);
            mcd.show();
        }
        if(id== R.id.m3) {
            Intent i = new Intent(this, Pref.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
loadResources();

    }


    void setLocale(Locale locale){

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
     //   recreate();

        Log.d("info","Locale SEt");
      //  Intent i = new Intent(this, MainActivity.class);
       // startActivity(i);
        onConfigurationChanged(config);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume Called", Toast.LENGTH_SHORT).show();
        configureLocale();
        //    setContentView(R.layout.activity_main);

    }

    void loadResources()
    {
        et  = findViewById(R.id.editText);
        pb = (ProgressBar)(findViewById(R.id.progressBar));
        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetJSON di = new GetJSON(MainActivity.this);
                di.execute(et.getText().toString());

            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        loadResources();

    }

    void configureLocale()
    {
        boolean b = sp.getBoolean("k1f1", false);
        Log.d("k1f1 value", ""+b);
        if (b) {
                 setLocale(new Locale("ru"));
        }
        else
        {
            setLocale(new Locale("en"));
        }
    }
    class GetJSON extends AsyncTask<String,Void,ArrayList<String>>
    {
        Context ct;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJsonStr = null;

        GetJSON(Context ctx)
        {
            ct  = ctx;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String cn = strings[0];
                final String AVIATION_url =
                        "http://aviation-edge.com/v2/public/timetable?";
                final String QUERY_PARAM = "iataCode";
                final String APPID_PARAM = "key";

                Uri builtUriDep = Uri.parse(AVIATION_url).buildUpon()
                        .appendQueryParameter(APPID_PARAM, "5635af-6ec92f")
                        .appendQueryParameter(QUERY_PARAM, cn)
                        .appendQueryParameter("type", "departure")
                        .build();

            Uri builtUriArr = Uri.parse(AVIATION_url).buildUpon()
                    .appendQueryParameter(APPID_PARAM, "5635af-6ec92f")
                    .appendQueryParameter(QUERY_PARAM, cn)
                    .appendQueryParameter("type", "arrival")
                    .build();
            String jsonDep = fetchData(builtUriDep);
            String jsonArr = fetchData(builtUriArr);

            ArrayList<String> arlist = new ArrayList<>();
            arlist.add(jsonDep);
            arlist.add(jsonArr);
            return arlist;
        }

        String fetchData(Uri builtUri)
        {
            try{
                URL url = new URL(builtUri.toString());

                Log.d("URI", builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.d("STATUS","connected");

                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this,"Connected", Toast.LENGTH_SHORT).show();
                    }
                });




                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return "";
                }
                responseJsonStr = buffer.toString();
                System.out.println("Received : " + responseJsonStr);
            }
            catch(Exception e)
            {
                Log.d("Error",e.toString());
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("FLIGHT APP", "Error closing stream", e);
                    }
                }
            }
            return responseJsonStr;
        }



        @Override
        protected void onPostExecute(ArrayList<String> arlist) {
            super.onPostExecute(arlist);

            if(arlist!=null && arlist.size()>0)
            {
                try {
                    Log.d("Data", arlist.get(0));
                    JSONArray obj = new JSONArray(arlist.get(0));
                    ArrayList<TimeTable> deptimetable = new ArrayList<>();
                    for(int i=0; i<obj.length();i++)
                    {
                        JSONObject jsobj = obj.getJSONObject(i);
                        String type = jsobj.getString("type");
                        String status= jsobj.getString("status");


                        JSONObject depobj = jsobj.getJSONObject("departure");
                        String depiatacode = depobj.getString("iataCode");

                        JSONObject arrobj = jsobj.getJSONObject("arrival");
                        String arriatacode = arrobj.getString("iataCode");

                        JSONObject airobj = jsobj.getJSONObject("airline");
                        String airlinename = airobj.getString("name");
                        String airlineiatacode = airobj.getString("iataCode");

                        JSONObject flightobj = jsobj.getJSONObject("flight");
                        String flightnum = flightobj.getString("number");
                        String flightiatanum = flightobj.getString("iataNumber");
//
//                    System.out.println("Type : " + type);
//                    System.out.println("Status : " + status);
//                    System.out.println("Depiatacode : " + depiatacode);
//                    System.out.println("Arrival IATA CODE : " + arriatacode);
//                    System.out.println("airlinename : " + airlinename);
//
//                    System.out.println("airline Iatacode : " + airlineiatacode);
//                    System.out.println("flight num : " + flightnum);
//                    System.out.println("flight iata num : " + flightiatanum);
                        TimeTable tt = new TimeTable(type,status,depiatacode,arriatacode,airlinename,airlineiatacode,flightnum,flightiatanum);
                        deptimetable.add(tt);
                    }
                    ////////////////////////////////

                     obj = new JSONArray(arlist.get(1));
                    ArrayList<TimeTable> artimetable = new ArrayList<>();
                    for(int i=0; i<obj.length();i++)
                    {
                        JSONObject jsobj = obj.getJSONObject(i);
                        String type = jsobj.getString("type");
                        String status= jsobj.getString("status");


                        JSONObject depobj = jsobj.getJSONObject("departure");
                        String depiatacode = depobj.getString("iataCode");

                        JSONObject arrobj = jsobj.getJSONObject("arrival");
                        String arriatacode = arrobj.getString("iataCode");

                        JSONObject airobj = jsobj.getJSONObject("airline");
                        String airlinename = airobj.getString("name");
                        String airlineiatacode = airobj.getString("iataCode");

                        JSONObject flightobj = jsobj.getJSONObject("flight");
                        String flightnum = flightobj.getString("number");
                        String flightiatanum = flightobj.getString("iataNumber");
//
                        TimeTable tt = new TimeTable(type,status,depiatacode,arriatacode,airlinename,airlineiatacode,flightnum,flightiatanum);
                        artimetable.add(tt);
                    }


                    ///////////////////////////////////////////////
                    pb.setVisibility(View.GONE);   //progressbar gone
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

                    tabLayout.removeAllTabs();;
                    tabLayout.addTab(tabLayout.newTab().setText("Departure Flights"));
                    tabLayout.addTab(tabLayout.newTab().setText("Arrival Flights"));

                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                    final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

                    final PagerAdapter adapter = new MyAdapter
                            (getSupportFragmentManager(), tabLayout.getTabCount(),deptimetable, artimetable);
                    System.out.println("0");
                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            viewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }
}

