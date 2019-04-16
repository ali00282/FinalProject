package com.example.finalproject.flighttracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.List;

public class CustomListView {

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.List;

    public class CustomListView extends BaseAdapter {
        Context ctx;
        LayoutInflater flater;
        private List<TimeTable> ls = new ArrayList<TimeTable>();
        ProgressBar pb;
        public void setData(List<TimeTable> data) {
            ls.addAll(data);
            notifyDataSetChanged();
        }
        CustomListView(Context c)
        {
            ctx = c;
            flater = (LayoutInflater)(ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        }

        @Override
        public int getCount() {
            return ls.size();
        }

        @Override
        //returns the item present at the specified position
        public Object getItem(int position) {
            return ls.get(position);
        }
        @Override
        //returns the Id of the item
        //WE will use the same index numbers as id of our item
        public long getItemId(int position) {
            return position;
        }
        @Override
        //This method is called for every item
        //view contains the reference of the current item in design

        public View getView(final int position, View view, ViewGroup viewGroup) {
            view = flater.inflate(R.layout.list1,null);

            TextView t1 = (TextView)(view.findViewById(R.id.textView4));
            TextView t2 = (TextView)(view.findViewById(R.id.textView5));
            TextView t3 = (TextView)(view.findViewById(R.id.textView7));
            pb = view.findViewById(R.id.progressBar2);
            final TimeTable tt = (TimeTable) getItem(position);

            t1.setText(tt.getDepiatacode());
            t2.setText(tt.getArrIataCode());
            t3.setText(tt.getAirlineName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ctx, "Airline is : " + tt.getAirlineName(),Toast.LENGTH_LONG).show();
                    GetJSONFlight di = new GetJSONFlight(ctx);

                    String flighturl = "http://aviation-edge.com/v2/public/flights?key=5635af-6ec92f&flightNum="+tt.getFlightNum();
                    di.execute(flighturl);
                }
            });
            return view;
        }

        class GetJSONFlight extends AsyncTask<String,Void,ArrayList<String>>
        {
            Context ct;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String responseJsonStr = null;

            GetJSONFlight(Context ctx)
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


                Uri builtUriDep = Uri.parse(cn);

                String jsonFlighDetails = fetchData(builtUriDep);
                System.out.println("Received : " + jsonFlighDetails);
                ArrayList<String> arlist = new ArrayList<>();
                arlist.add(jsonFlighDetails);
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
                    //System.out.println("Received : " + responseJsonStr);
                }
                catch(Exception e)
                {
                    Log.d("Error",e.toString());
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
                pb.setVisibility(View.INVISIBLE);   //progressbar gone
                if(arlist!=null && arlist.size()>0)
                {
                    try {

                        JSONArray obj = new JSONArray(arlist.get(0));

                        ArrayList<TimeTable> deptimetable = new ArrayList<>();

                        JSONObject jsobj = obj.getJSONObject(0);

                        JSONObject geo = jsobj.getJSONObject("geography");
                        String lat = geo.getString("latitude");
                        String lon = geo.getString("longitude");
                        String alt = geo.getString("altitude");
                        String dir = geo.getString("direction");

                        JSONObject speed = jsobj.getJSONObject("speed");
                        String hor = speed.getString("horizontal");
                        String ver = speed.getString("vertical");

                        JSONObject departure = jsobj.getJSONObject("departure");
                        String depiataCode = departure.getString("iataCode");

                        JSONObject arrival = jsobj.getJSONObject("arrival");
                        String arriataCode = arrival.getString("iataCode");

                        JSONObject airline = jsobj.getJSONObject("airline");
                        String airlineiataCode = airline.getString("iataCode");

                        JSONObject flight = jsobj.getJSONObject("flight");
                        String flightnumber = flight.getString("number");

                        String status = jsobj.getString("status");

                        FlightDetails fd = new FlightDetails(depiataCode,arriataCode,airlineiataCode,
                                lat,lon,dir,alt,hor, ver,status);

                        Intent i = new Intent(ctx, FlightDetailsActivity.class);
                        i.putExtra("data",fd);
                        ctx.startActivity(i);



                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


}
