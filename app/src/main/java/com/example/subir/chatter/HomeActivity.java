package com.example.subir.chatter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rv;
    int countuser;
    List<ItemData> itemData = new ArrayList<>();
    MyAdapter myAdapter = new MyAdapter(itemData);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Parse.initialize(this);
        //ParseInstallation.getCurrentInstallation().saveInBackground();

        generateList();
    }

    public void generateList(){
        final ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser>usernameList, ParseException e) {
                if (e == null) {
                    for (ParseUser user :usernameList){
                        if(!(user.getUsername().equals(currentUser.getUsername()))) {
                            ItemData val = new ItemData(user.getUsername(), R.drawable.check_not_following);
                            itemData.add(val);
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
}
