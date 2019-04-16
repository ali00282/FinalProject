package com.example.finalproject.flighttracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class MyAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<TimeTable> ar1,ar2;
    public MyAdapter(FragmentManager fm, int NumOfTabs, ArrayList<TimeTable> depar,
                     ArrayList<TimeTable> Arrar
                     ) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        ar1 = depar;
        ar2 = Arrar;
    }

    @Override
    public Fragment getItem(int position) {

        System.out.println("2");
        switch (position) {
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                Bundle args = new Bundle();
                args.putSerializable("k1",ar1);
                tab1.setArguments(args);
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                Bundle args2 = new Bundle();
                args2.putSerializable("k1",ar2);
                tab2.setArguments(args2);
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}