package com.example.subir.chatter.HomeScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.subir.chatter.Friends.FollowListActivity;
import com.example.subir.chatter.LoginActivity;
import com.example.subir.chatter.R;
import com.example.subir.chatter.TweetsActivity;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NewsFeed extends AppCompatActivity {

    RecyclerView rv;
    List<TweetData> tweetData = new ArrayList<>();
    TweetViewAdapter tweetViewAdapter = new TweetViewAdapter(tweetData);
    List<String> nameList = new ArrayList<>();
    static boolean ticket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);


        ParseQuery<ParseObject> query = new ParseQuery<>("Tweets");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for(ParseObject p : list){
                    String user = p.getString("username");
                    String twit = p.getString("tweet");

                    if(nameList.contains(user)) {
                        TweetData val = new TweetData(user, twit);
                        tweetData.add(val);
                    }
                    tweetViewAdapter.notifyDataSetChanged();
                }
            }
        });

        rv = findViewById(R.id.newsfeedList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(tweetViewAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    protected void onResume() {
        super.onResume();

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();


        getFolllowList(ParseUser.getCurrentUser().getUsername());
        nameList.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tweet:
                Intent intent = new Intent(NewsFeed.this,TweetsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                ParseUser.logOut();
                alertDisplayer("So, you're going...", "Ok...Bye-bye then");
                return true;
            case R.id.following:
                Intent intent1 = new Intent(NewsFeed.this,FollowListActivity.class);
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(NewsFeed.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    public void getFolllowList(final String username) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("addedUsers");
        query1.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query1.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException eg) {

                if (eg == null && objects.size() > 0) {
                    ParseObject Grillen = objects.get(0);

                    if (Grillen.getList("following") != null) {
                        nameList = Grillen.getList("following");
                    }//fetch from array

                    if (nameList.contains(username)) {
                        ticket = true;
                    } else {
                        ticket = false;
                    }
                } else {
                    //fail to get!!
                    eg.printStackTrace();
                }
            }
        });
    }
}
