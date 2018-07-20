package com.example.subir.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ItemData[] itemDatas;

    public MyAdapter(@NonNull ItemData[] itemDatas) {
        this.itemDatas = itemDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.nameTextView.setText(itemDatas[position].getName());
        holder.noTextView.setText(itemDatas[position].getNo());
        holder.emailTextView.setText(itemDatas[position].getEmail());
        holder.iconImageView.setImageResource(itemDatas[position].getUrl());

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), itemDatas[position].getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemDatas.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView noTextView;
        public ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            emailTextView = itemView.findViewById(R.id.email);
            noTextView = itemView.findViewById(R.id.phoneno);
            iconImageView = itemView.findViewById(R.id.contactpic);
        }

    }
}
