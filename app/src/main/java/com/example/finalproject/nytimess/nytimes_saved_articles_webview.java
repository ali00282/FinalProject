package com.example.finalproject.nytimess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class nytimes_saved_articles_webview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nytimes_saved_web);



        WebView wbview = (WebView) findViewById(R.id.wvArticlesaved);

        wbview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView wbview, String strngurl) {
                wbview.loadUrl(strngurl);
                return true;
            }
        });
        wbview.loadUrl(nytimes_search_fragment.urlTextforLoad);
    }

}
