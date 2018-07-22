package com.example.subir.chatter.HomeScreen;

public class TweetData {

    String nameOfPerson, textTweeted;

    public TweetData(String nameOfPerson, String textTweeted) {
        this.nameOfPerson = nameOfPerson;
        this.textTweeted = textTweeted;
    }

    public String getNameOfPerson() {
        return nameOfPerson;
    }

    public void setNameOfPerson(String nameOfPerson) {
        this.nameOfPerson = nameOfPerson;
    }

    public String getTextTweeted() {
        return textTweeted;
    }

    public void setTextTweeted(String textTweeted) {
        this.textTweeted = textTweeted;
    }
}
