package com.example.trackervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RecommendationsActivity extends AppCompatActivity {
    public static String baseURL="https://api.themoviedb.org";
    public static String language="en-uk";
    public static String apiKey="a07d8418569b36aba9e3ae4a58107505";
    public String topRatedCategory="top_rated";
    public String popularCategory="popular";
    public String airingTodayCategory="airing_today";
    public String airingNext7Days = "on_the_air";

    RecyclerView trendingRecyclerview;
    LinearLayoutManager layoutManager;
    recommendationsRecyclerAdapter recommendationsRecyclerAdapter;
    List<TVShowResults.Result> airingTodayResults = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        trendingRecyclerview = findViewById(R.id.trendingRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recommendationsRecyclerAdapter = new recommendationsRecyclerAdapter(airingTodayResults);
        trendingRecyclerview.setLayoutManager(layoutManager);
        trendingRecyclerview.setAdapter(recommendationsRecyclerAdapter);

        fetchShows();



    }

    private void fetchShows(){
        retrofitInterface.getRetrofitClient().getTopRatedShows(topRatedCategory,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                if(response.isSuccessful()){
                    airingTodayResults.addAll(response.body().getResults());
                    recommendationsRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(RecommendationsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}