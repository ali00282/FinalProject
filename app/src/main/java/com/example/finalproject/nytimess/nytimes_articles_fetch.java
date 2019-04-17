package com.example.finalproject.nytimess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class nytimes_articles_fetch implements Serializable {

    String fetchurl;
    String fetchheadline;
    String fetchthumbnail;

    //public static String id = "article";

    public nytimes_articles_fetch(String fetchurl, String fetchheadline, String fetchthumbnail) {
        this.fetchurl = fetchurl;
        this.fetchheadline = fetchheadline;
        this.fetchthumbnail = fetchthumbnail;
    }

    public String getfetchurl() {

        return fetchurl;
    }

    public String getfetchheadline() {

        return fetchheadline;
    }

    public String getfetchthumbnail() {

        return fetchthumbnail;
    }



    public nytimes_articles_fetch(JSONObject jsonObject) {
        try {
            this.fetchurl = jsonObject.getString("web_url");
            this.fetchheadline = jsonObject.getJSONObject("headline").getString("main");


            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.fetchthumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else this.fetchthumbnail = "";


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<nytimes_articles_fetch> fromJSONArray(JSONArray array) {
        ArrayList<nytimes_articles_fetch> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new nytimes_articles_fetch(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
