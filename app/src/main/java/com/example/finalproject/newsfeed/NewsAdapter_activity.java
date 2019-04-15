package com.example.finalproject.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.finalproject.R;

import java.util.ArrayList;

public class NewsAdapter_activity extends BaseAdapter {

    private ArrayList<News_activity> news;
    private Context context;
    private LayoutInflater inflater;

    public NewsAdapter_activity(ArrayList<News_activity> news, Context context) {
        this.news = news;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return news.size();
    }
    @Override
    public Object getItem(int position) {
        return news.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.activity_news_list_item, parent, false);
        }
        News_activity currentItem = (News_activity) getItem(position);
        TextView textViewItemName = (TextView) convertView.findViewById(R.id.news_title);
        textViewItemName.setText(currentItem.getTitle());

        return convertView;
    }
}
