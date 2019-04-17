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


public class nytimes_saved_arrayops extends ArrayAdapter<nytimes_articles_fetch> {

    public nytimes_saved_arrayops(Context context, List<nytimes_articles_fetch> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        nytimes_articles_fetch article = this.getItem(position);

        if (convertView == null) {
            LayoutInflater layoutinflater = LayoutInflater.from(getContext());
            convertView = layoutinflater.inflate(R.layout.nytimes_item_saved_article, parent, false);
        }

        ImageView imgvewr = (ImageView) convertView.findViewById(R.id.image_viewer);

        imgvewr.setImageResource(0);

        TextView textviewtitle = (TextView) convertView.findViewById(R.id.title_txtviewer);
        textviewtitle.setText(article.getfetchheadline());


        String thumbnail = article.getfetchthumbnail();


        if (!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(imgvewr);
        }

        return convertView;

    }
}
