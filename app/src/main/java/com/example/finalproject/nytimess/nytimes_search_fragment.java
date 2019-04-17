package com.example.finalproject.nytimess;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class nytimes_search_fragment extends Fragment {


    protected TextView ttl_article;
    protected TextView weburl_article;
    protected ImageView img_article;
    protected Button dltButton;

    Intent itent;
    int articlenoid;
    String weburlText;

    public static String urlTextforLoad;
    protected nytimes_articles_saved article_popwindow;

    public nytimes_search_fragment() {

        super();
    }



    @SuppressLint("ValidFragment")
    public nytimes_search_fragment(nytimes_articles_saved article_popwindow) {
        super();
        this.article_popwindow = article_popwindow;
    }



    @Override
    public View onCreateView(LayoutInflater inflater,  @Nullable ViewGroup container, Bundle savedInstanceState) {

        final Bundle arguments = getArguments();
        final View view = inflater.inflate(R.layout.nytimes_activity_articles_fragment, container, false);

        ttl_article = (TextView) view.findViewById(R.id.fragment_article_title);
        weburl_article = (TextView) view.findViewById(R.id.fragment_article_urladdress);
        img_article = (ImageView) view.findViewById(R.id.fragment_image_viewer);
        dltButton = (Button) view.findViewById(R.id.delete_button);

        weburlText = "URL: " + " " + arguments.getString("url");
        articlenoid = arguments.getInt("id");


        String texttitls = "Article Title:" + " " + arguments.getString("headline");

        String thumbnailz = arguments.getString("pic_url");



        if (thumbnailz.length()>0) {
            Picasso.with(getContext()).load(thumbnailz).into(img_article);
        }



        ttl_article.setText(texttitls);
        weburl_article.setText(weburlText);
        urlTextforLoad = arguments.getString("url");


        dltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (article_popwindow == null) {

                    Intent detailedmsgIntent = new Intent(getActivity(), nytimes_search_fragment.class);
                    detailedmsgIntent.putExtra("msgID", articlenoid);

                    getActivity().setResult(nytimes_articles_saved.CONTEXT_INCLUDE_CODE, detailedmsgIntent);
                    getActivity().finish();
                } else {
                    article_popwindow.deleteItem(articlenoid);

                    FragmentTransaction transfrag = getFragmentManager().beginTransaction();
                    transfrag.remove(nytimes_search_fragment.this);
                    transfrag.commit();
                }
            }
        });

        weburl_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itent = new Intent(getActivity(), nytimes_saved_articles_webview.class);
                startActivity(itent);
            }
        });
        return view;
    }
}


