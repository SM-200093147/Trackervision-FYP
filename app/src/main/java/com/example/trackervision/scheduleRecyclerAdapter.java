package com.example.trackervision;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class scheduleRecyclerAdapter extends RecyclerView.Adapter<scheduleRecyclerAdapter.ScheduleViewHolder> {
    Context context;
    ArrayList<uploadScheduleInfo> list;

    public scheduleRecyclerAdapter(Context context, ArrayList<uploadScheduleInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_entry,parent,false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        uploadScheduleInfo scheduleInfo =list.get(position);
        holder.showName.setText(scheduleInfo.getShowName());
        holder.showSeason.setText(scheduleInfo.getShowSeason());
        holder.showEpisode.setText(scheduleInfo.getShowEpisode());
        holder.showDate.setText(scheduleInfo.getDate());
        holder.showTime.setText(scheduleInfo.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView showName, showSeason, showEpisode, showDate, showTime;
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.scheduleCardShowName);
            showSeason = itemView.findViewById(R.id.scheduleCardSeason);
            showEpisode = itemView.findViewById(R.id.scheduleCardEpisode);
            showDate = itemView.findViewById(R.id.scheduleCardDate);
            showTime = itemView.findViewById(R.id.scheduleCardShowTime);
        }
    }
}
