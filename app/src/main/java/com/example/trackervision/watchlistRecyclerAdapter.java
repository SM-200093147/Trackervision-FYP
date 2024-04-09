package com.example.trackervision;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class watchlistRecyclerAdapter extends RecyclerView.Adapter<watchlistRecyclerAdapter.WatchlistViewHolder> {
    Context context;
    ArrayList<uploadWatchlistInfo> list;
    ArrayList <String> keys;
    String userUid;




    public watchlistRecyclerAdapter(Context context, ArrayList<uploadWatchlistInfo> list, ArrayList<String> keys) {
        this.context = context;
        this.list = list;
        this.keys = keys;

    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.watchlist_entry,parent,false);
        return new WatchlistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        userUid = FirebaseAuth.getInstance().getUid();
        uploadWatchlistInfo watchlistInfo =list.get(position);
        holder.showName.setText(watchlistInfo.getShowName());
        holder.showFirstAired.setText(""+watchlistInfo.getFirstAirDate());
        holder.showAverageRating.setText(""+watchlistInfo.getAverageRating());
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+watchlistInfo.getPosterURL()).into(holder.showPoster);
        holder.deleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = keys.get(position);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("WatchlistEntries").child(userUid);
                ref.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((WatchlistActivity)context).finish();
                        Toast.makeText(context, "Successfully Deleted. Watchlist has been refreshed.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        TextView showName, showAverageRating, showFirstAired;
        ImageView showPoster, deleteEntry;
        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.watchlistCardShowName);
            showAverageRating = itemView.findViewById(R.id.watchlistCardAverageRating);
            showFirstAired = itemView.findViewById(R.id.watchlistFirstAired);
            showPoster = itemView.findViewById(R.id.watchlistCardPoster);
            deleteEntry = itemView.findViewById(R.id.deleteWatchlistEvent);
        }
    }
}
