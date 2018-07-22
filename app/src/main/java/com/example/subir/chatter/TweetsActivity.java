package com.example.subir.chatter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class TweetsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText _tweet;
    Button tweeted,cleared;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);


        /*Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();*/

        _tweet = findViewById(R.id.tweetEditText);
        tweeted = findViewById(R.id.submitButton);
        cleared = findViewById(R.id.clearButton);

        tweeted.setOnClickListener(this);
        cleared.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitButton:
                final ParseUser currentUser = ParseUser.getCurrentUser();
                ParseObject object = new ParseObject("Tweets");
                object.put("username", currentUser.getUsername());
                object.put("tweet", _tweet.getText().toString());
                object.add("xyz","hello");
                object.saveInBackground();
                Intent intent = new Intent(TweetsActivity.this, FollowListActivity.class);
                startActivity(intent);
                break;
            case R.id.clearButton:
                _tweet.setText("");
                break;
        }
    }
}
