package com.example.trackervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationsActivity extends AppCompatActivity {
    public static String baseURL="https://api.themoviedb.org";
    public static String language="en-uk";
    public static String apiKey="a07d8418569b36aba9e3ae4a58107505";
    public String topRatedCategory="top_rated";
    public String trendingCategory="popular";
    public String airingTodayCategory="airing_today";
    public String airingNext7Days = "on_the_air";
    public String  time_window = "day";

    RecyclerView allTimePopularRecyclerview, trendingRecyclerView, airingTodayRecyclerView, airingNext7DaysRecyclerView;
    LinearLayoutManager allTimePopularLayoutManager, trendingLayoutManager, airingTodayLayoutManager, airingNext7DaysLayoutManager;
    recommendationsRecyclerAdapter allTimePopularRecyclerAdapter, trendingRecyclerAdapter, airingTodayRecyclerAdapter, airingNext7DaysRecyclerAdapter;
    List<TVShowResults.Result> airingTodayResults = new ArrayList<>(),trendingResults = new ArrayList<>(),airingInTheNext7DaysResults = new ArrayList<>(),allTimePopularResults = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        allTimePopularLayoutManager = new LinearLayoutManager(this);
        allTimePopularRecyclerview = findViewById(R.id.allTimePopularRecyclerView);
        allTimePopularRecyclerAdapter = new recommendationsRecyclerAdapter(allTimePopularResults);
        allTimePopularRecyclerview.setLayoutManager(allTimePopularLayoutManager);
        allTimePopularRecyclerview.setAdapter(allTimePopularRecyclerAdapter);
        fetchTopRatedShows();

        trendingLayoutManager = new LinearLayoutManager(this);
        trendingRecyclerView = findViewById(R.id.trendingRecyclerView);
        trendingRecyclerAdapter = new recommendationsRecyclerAdapter(trendingResults);
        trendingRecyclerView.setLayoutManager(trendingLayoutManager);
        trendingRecyclerView.setAdapter(trendingRecyclerAdapter);
        fetchTrendingCategory();

        airingTodayLayoutManager = new LinearLayoutManager(this);
        airingTodayRecyclerView = findViewById(R.id.airingTodayRecyclerView);
        airingTodayRecyclerAdapter = new recommendationsRecyclerAdapter(airingTodayResults);
        airingTodayRecyclerView.setLayoutManager(airingTodayLayoutManager);
        airingTodayRecyclerView.setAdapter(airingTodayRecyclerAdapter);
        fetchAiringTodayCategory();

        airingNext7DaysLayoutManager = new LinearLayoutManager(this);
        airingNext7DaysRecyclerView = findViewById(R.id.airingNext7DaysRecyclerView);
        airingNext7DaysRecyclerAdapter = new recommendationsRecyclerAdapter(airingInTheNext7DaysResults);
        airingNext7DaysRecyclerView.setLayoutManager(airingNext7DaysLayoutManager);
        airingNext7DaysRecyclerView.setAdapter(airingNext7DaysRecyclerAdapter);
        fetchAiringNext7DaysCategory();




    }

    private void fetchTopRatedShows(){
        retrofitInterface.getRetrofitClient().getTopRatedShows(topRatedCategory,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                if(response.isSuccessful()){
                    allTimePopularResults.addAll(response.body().getResults());
                    allTimePopularRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(RecommendationsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchTrendingCategory(){
        retrofitInterface.getRetrofitClient().showTrending(time_window,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                if(response.isSuccessful()){
                    trendingResults.addAll(response.body().getResults());
                    trendingRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(RecommendationsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchAiringTodayCategory(){
        retrofitInterface.getRetrofitClient().getTopRatedShows(airingTodayCategory,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                if(response.isSuccessful()){
                    airingTodayResults.addAll(response.body().getResults());
                    airingTodayRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(RecommendationsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchAiringNext7DaysCategory(){
        retrofitInterface.getRetrofitClient().getTopRatedShows(airingNext7Days,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                if(response.isSuccessful()){
                    airingInTheNext7DaysResults.addAll(response.body().getResults());
                    airingNext7DaysRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(RecommendationsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}