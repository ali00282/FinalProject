package com.example.finalproject.nytimess;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class nytimes_articles_arrayops extends ArrayAdapter<nytimes_articles_fetch> {

    public nytimes_articles_arrayops(Context context, List<nytimes_articles_fetch> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        nytimes_articles_fetch article = this.getItem(position);


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.nytimes_activity_searchresult, parent, false);
        }


        ImageView imgv = (ImageView) convertView.findViewById(R.id.image_viewer);
        imgv.setImageResource(0);


        TextView textviewTitle;
        textviewTitle = (TextView) convertView.findViewById(R.id.title_txtviewer);
        textviewTitle.setText(article.getfetchheadline());


        String thumbnailstrng;
        thumbnailstrng = article.getfetchthumbnail();


        if (!TextUtils.isEmpty(thumbnailstrng)) {
            Picasso.with(getContext()).load(thumbnailstrng).into(imgv);
        }
        return convertView;
    }
}
