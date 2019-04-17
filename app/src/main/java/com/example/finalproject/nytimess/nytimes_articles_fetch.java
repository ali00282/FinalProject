package com.example.finalproject.nytimess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The type Nytimes articles fetch.
 */
public class nytimes_articles_fetch implements Serializable {

    /**
     * The Fetchurl.
     */
    String fetchurl;
    /**
     * The Fetchheadline.
     */
    String fetchheadline;
    /**
     * The Fetchthumbnail.
     */
    String fetchthumbnail;

    //public static String id = "article";

    /**
     * Instantiates a new Nytimes articles fetch.
     *
     * @param fetchurl       the fetchurl
     * @param fetchheadline  the fetchheadline
     * @param fetchthumbnail the fetchthumbnail
     */
    public nytimes_articles_fetch(String fetchurl, String fetchheadline, String fetchthumbnail) {
        this.fetchurl = fetchurl;
        this.fetchheadline = fetchheadline;
        this.fetchthumbnail = fetchthumbnail;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public String getfetchurl() {

        return fetchurl;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public String getfetchheadline() {

        return fetchheadline;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public String getfetchthumbnail() {

        return fetchthumbnail;
    }


    /**
     * Instantiates a new Nytimes articles fetch.
     *
     * @param jsonObject the json object
     */
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


    /**
     * From json array array list.
     *
     * @param array the array
     * @return the array list
     */
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
