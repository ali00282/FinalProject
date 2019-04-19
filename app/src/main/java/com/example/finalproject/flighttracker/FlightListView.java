package com.example.finalproject.flighttracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FlightListView extends BaseAdapter {
    Context ctx;
    LayoutInflater flater;
    private List<FlightDetails> ls = new ArrayList<FlightDetails>();
    public void setData(List<FlightDetails> data) {
        ls.addAll(data);
        notifyDataSetChanged();
    }
    FlightListView(Context c)
    {
        ctx = c;
        flater = (LayoutInflater)(ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = flater.inflate(R.layout.list1,null);

        TextView t1 = (TextView)(view.findViewById(R.id.textView4));
        TextView t2 = (TextView)(view.findViewById(R.id.textView5));
        TextView t3 = (TextView)(view.findViewById(R.id.textView7));
        final FlightDetails tt = (FlightDetails) getItem(position);

        t1.setText(tt.getDepiata());
        t2.setText(tt.getArriata());
        t3.setText(tt.getAirlineIata());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Airline is : " + tt.getAirlineIata(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(ctx, ViewSavedFlightDetailsActivity.class);
                i.putExtra("data", tt);
                ctx.startActivity(i);
            }
        });
        return view;
    }
}
