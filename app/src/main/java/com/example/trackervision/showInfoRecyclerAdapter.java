package com.example.trackervision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class showInfoRecyclerAdapter extends RecyclerView.Adapter<showInfoRecyclerAdapter.RecommendationsViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<TVShowResults.Result> showsList;

    public showInfoRecyclerAdapter(List<TVShowResults.Result> showsList, RecyclerViewInterface recyclerViewInterface) {
        this.showsList = showsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecommendationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendations_entry,parent,false);
        return new RecommendationsViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationsViewHolder holder, int position) {
        holder.showName.setText(showsList.get(position).getName());
        holder.showOverview.setText(showsList.get(position).getOverview());
        holder.showRating.setText((String.format(" "+"%.1f",showsList.get(position).getVoteAverage())));
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
        public RecommendationsViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            showName = itemView.findViewById(R.id.recommendationCardShowName);
            showRating = itemView.findViewById(R.id.recommendationAverageRating);
            showOverview = itemView.findViewById(R.id.recommendationOverviewText);
            showPoster = itemView.findViewById(R.id.imageView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onRecyclerEntryClick(position);
                        }
                    }
                }
            });
        }
    }
}
