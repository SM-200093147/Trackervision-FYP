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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class scheduleRecyclerAdapter extends RecyclerView.Adapter<scheduleRecyclerAdapter.ScheduleViewHolder> {
    Context context;
    ArrayList<uploadScheduleInfo> list;
    ArrayList <String> keys;
    String userUid;




    public scheduleRecyclerAdapter(Context context, ArrayList<uploadScheduleInfo> list, ArrayList<String> keys) {
        this.context = context;
        this.list = list;
        this.keys = keys;

    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_entry,parent,false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        userUid = FirebaseAuth.getInstance().getUid();
        uploadScheduleInfo scheduleInfo =list.get(position);
        holder.showName.setText(scheduleInfo.getShowName());
        holder.showSeason.setText(scheduleInfo.getShowSeason());
        holder.showEpisode.setText(scheduleInfo.getShowEpisode());
        holder.showDate.setText(scheduleInfo.getDate());
        holder.showTime.setText(scheduleInfo.getTime());
        holder.deleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String key = keys.get(position);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ScheduleEntries").child(userUid);
                    ref.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            ((ViewMyScheduleActivity)context).finish();
                            Toast.makeText(context, "Successfully Deleted. Schedule has been refreshed.", Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView showName, showSeason, showEpisode, showDate, showTime;
        ImageView deleteEntry;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.scheduleCardShowName);
            showSeason = itemView.findViewById(R.id.scheduleCardSeason);
            showEpisode = itemView.findViewById(R.id.scheduleCardEpisode);
            showDate = itemView.findViewById(R.id.scheduleCardDate);
            showTime = itemView.findViewById(R.id.scheduleCardShowTime);
            deleteEntry = itemView.findViewById(R.id.deleteScheduleEvent);
        }
    }
}
