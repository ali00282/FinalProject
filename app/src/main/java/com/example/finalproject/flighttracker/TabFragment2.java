package com.example.finalproject.flighttracker;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TabFragment2 extends ListFragment {
    ArrayList<TimeTable> ar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ar =(ArrayList<TimeTable>) getArguments().getSerializable("k1");
        System.out.println("Size : " + ar.size());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CustomListView adapter = new CustomListView(getContext());
        adapter.setData(ar);
        getListView().setAdapter(adapter);
        //it will automatically fetch the listview (android id has been given)
    }
}
