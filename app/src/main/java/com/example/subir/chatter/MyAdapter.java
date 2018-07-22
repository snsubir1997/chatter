package com.example.subir.chatter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SignUpCallback;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<ItemData> itemDatas;

    public MyAdapter(@NonNull List<ItemData> itemDatas) {
        this.itemDatas = itemDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final ItemData itemData = itemDatas.get(position);

        holder.nameTextView.setText(itemData.getName());
        holder.iconImageView.setImageResource(itemData.getUrl());

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("addedUsers");
                query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

                // Retrieve the object by id
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> objects, ParseException eg) {
                        List<String> nameList =  new ArrayList<>();

                        if (eg == null && objects.size() > 0) {
                            ParseObject Grillen = objects.get(0);

                            if (Grillen.getList("following")!=null) {
                                nameList =  Grillen.getList("following");
                            }//fetch from array

                            if (nameList.contains(itemData.getName()))
                            {
                                Grillen.remove("following");
                                nameList.remove(itemData.getName());//remove all
                                Grillen.addAllUnique("following", nameList);//add all to table
                                nameList.clear();
                                holder.iconImageView.setImageResource(R.drawable.check_not_following);
                            }
                            else
                            {
                                Grillen.addUnique("following", itemData.getName());//add to table
                                holder.iconImageView.setImageResource(R.drawable.check_following);
                            }
                            Grillen.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        //success, saved!
                                        Log.d("MyApp", "Successfully saved!");
                                    } else {
                                        //fail to save!
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else {
                            //fail to get!!
                            eg.printStackTrace();
                        }
                    }
                });

                Toast.makeText(v.getContext(), itemData.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.follower);
            iconImageView = itemView.findViewById(R.id.tickImg);
        }
    }
}
