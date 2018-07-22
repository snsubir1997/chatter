package com.example.subir.chatter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FollowListActivity extends AppCompatActivity {

    RecyclerView rv;
    List<ItemData> itemData = new ArrayList<>();
    MyAdapter myAdapter = new MyAdapter(itemData);
    static boolean ticket;
    List<String> nameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();


        getFolllowList(ParseUser.getCurrentUser().getUsername());
        generateList();
        nameList.clear();
    }

    public void generateList(){
        final ParseUser currentUser = ParseUser.getCurrentUser();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser>usernameList, ParseException e) {
                if (e == null) {
                    for (final ParseUser user :usernameList){//getting all users
                        if(!(user.getUsername().equals(currentUser.getUsername()))) {

                            //FollowListActivity.getTick(user.getUsername());

                            if(nameList.contains(user.getUsername())) {
                                ItemData val = new ItemData(user.getUsername(), R.drawable.check_following);
                                itemData.add(val);
                            }else{
                                ItemData val = new ItemData(user.getUsername(), R.drawable.check_not_following);
                                itemData.add(val);
                            }

                            myAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.d("error", "Error: " + e.getMessage());
                }
            }
        });

        rv = findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
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
                Intent intent = new Intent(FollowListActivity.this,TweetsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                ParseUser.logOut();
                alertDisplayer("So, you're going...", "Ok...Bye-bye then");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(FollowListActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(FollowListActivity.this, LoginActivity.class);
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
