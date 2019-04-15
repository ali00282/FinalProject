package com.example.finalproject.newsfeed;

public class News_activity {

    private String title;
    private String body;
    private int newsID;

    //indicates the title, body and idConstructor
    public News_activity(String title, String body, int newsID){
        this.title = title;
        this.body = body;
        this.newsID = newsID;
    }
    //getter and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }
}

