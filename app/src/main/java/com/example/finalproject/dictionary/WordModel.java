package com.example.finalproject.dictionary;

public class WordModel {


    private String word, pronunciation, definition;
    private long id;

    public WordModel(String word, String pronunciation, String definition, long id) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.definition = definition;
        this.id = id;
    }

    public WordModel() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

