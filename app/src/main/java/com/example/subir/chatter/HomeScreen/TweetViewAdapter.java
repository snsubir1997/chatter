package com.example.subir.chatter.HomeScreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subir.chatter.R;

import java.util.List;

public class TweetViewAdapter extends RecyclerView.Adapter<TweetViewAdapter.ViewHolder> {

    List<TweetData> tweetDatas;

    public TweetViewAdapter(@NonNull List<TweetData> tweetDatas) {
        this.tweetDatas = tweetDatas;
    }

    @Override
    public TweetViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_row, null);
        TweetViewAdapter.ViewHolder viewHolder = new TweetViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewAdapter.ViewHolder holder, int position) {
        TweetData tweetData = tweetDatas.get(position);

        holder.personNameTextView.setText(tweetData.getNameOfPerson());
        holder.tweetTextView.setText(tweetData.getTextTweeted());
    }

    @Override
    public int getItemCount() {
        return tweetDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView personNameTextView;
        public TextView tweetTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            personNameTextView = itemView.findViewById(R.id.personName);
            tweetTextView = itemView.findViewById(R.id.personTweet);
        }
    }
}
