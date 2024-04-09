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

public class logRecyclerAdapter extends RecyclerView.Adapter<logRecyclerAdapter.LogViewHolder> {
    Context context;
    ArrayList<uploadLogInfo> list;
    ArrayList <String> keys;
    String userUid;




    public logRecyclerAdapter(Context context, ArrayList<uploadLogInfo> list, ArrayList<String> keys) {
        this.context = context;
        this.list = list;
        this.keys = keys;

    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.log_entry,parent,false);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        userUid = FirebaseAuth.getInstance().getUid();
        uploadLogInfo logInfo =list.get(position);
        holder.showName.setText(logInfo.getShowName());
        holder.showDateCompleted.setText(""+logInfo.getDateCompleted());
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+logInfo.getPosterURL()).into(holder.showPoster);
        holder.deleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = keys.get(position);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LogEntries").child(userUid);
                ref.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((MyLogActivity)context).finish();
                        Toast.makeText(context, "Successfully Deleted. Log has been refreshed.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView showName, showDateCompleted;
        ImageView showPoster, deleteEntry;
        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.logCardShowName);
            showPoster = itemView.findViewById(R.id.logCardPoster);
            showDateCompleted = itemView.findViewById(R.id.logCardDateCompleted);
            deleteEntry = itemView.findViewById(R.id.deleteLogEvent);
        }
    }
}
