package com.example.trackervision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

public class recommendationsRecyclerAdapter extends RecyclerView.Adapter<recommendationsRecyclerAdapter.RecommendationsViewHolder> {
    private List<TVShowResults.Result> showsList;

    public recommendationsRecyclerAdapter(List<TVShowResults.Result> showsList) {
        this.showsList = showsList;
    }

    @NonNull
    @Override
    public RecommendationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendations_entry,parent,false);
        return new RecommendationsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationsViewHolder holder, int position) {
        holder.showName.setText(showsList.get(position).getName());
        holder.showOverview.setText(showsList.get(position).getOverview());
        holder.showRating.setText((String.format("%.1f",showsList.get(position).getVoteAverage())));
        String posterURL = "https://image.tmdb.org/t/p/w500/"+showsList.get(position).getPosterPath();
        Glide.with(holder.itemView.getContext()).load(posterURL).into(holder.showPoster);

    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public class RecommendationsViewHolder extends RecyclerView.ViewHolder {
        TextView showName;
        TextView showRating;
        TextView showOverview;
        ImageView showPoster;
        public RecommendationsViewHolder(@NonNull View itemView) {
            super(itemView);

            showName = itemView.findViewById(R.id.recommendationCardShowName);
            showRating = itemView.findViewById(R.id.recommendationAverageRating);
            showOverview = itemView.findViewById(R.id.recommendationOverviewText);
            showPoster = itemView.findViewById(R.id.imageView3);
        }
    }
}
