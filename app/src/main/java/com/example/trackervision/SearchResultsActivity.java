package com.example.trackervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity implements  RecyclerViewInterface{
    public static String baseURL="https://api.themoviedb.org";
    public static String language="en-uk";
    public static String apiKey="a07d8418569b36aba9e3ae4a58107505";
    RecyclerView searchResultsRecyclerView;
    LinearLayoutManager searchResultsLayoutManager;
    showInfoRecyclerAdapter searchResultsRecyclerAdapter;
    List<TVShowResults.Result> searchResultsList = new ArrayList<>();
    String searchResults;
    TextView searchResultsTextView, noResultsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent i = getIntent();
        noResultsTextView = findViewById(R.id.noResults);
        searchResults = i.getStringExtra ("searchResults");
        searchResultsTextView = findViewById(R.id.searchResultName);
        searchResultsTextView.setText(searchResults);
        searchResultsLayoutManager = new LinearLayoutManager(this);
        searchResultsRecyclerView = findViewById(R.id.searchRecyclerView);
        searchResultsRecyclerAdapter = new showInfoRecyclerAdapter(searchResultsList, this);
        searchResultsRecyclerView.setLayoutManager(searchResultsLayoutManager);
        searchResultsRecyclerView.setAdapter(searchResultsRecyclerAdapter);
        fetchSearchResults();



    }
    private void fetchSearchResults(){
        retrofitInterface.getRetrofitClient().searchTVShows(searchResults,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                    searchResultsList.addAll(response.body().getResults());
                    searchResultsRecyclerAdapter.notifyDataSetChanged();
                    if (searchResultsList.size()>0) {
                        noResultsTextView.setVisibility(View.GONE);
                    }
                    else {
                        searchResultsRecyclerView.setVisibility(View.GONE);
                    }
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(SearchResultsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void closeSearchResults(){
        finish();
    }

    @Override
    public void onRecyclerEntryClick(int position) {
        retrofitInterface.getRetrofitClient().searchTVShows(searchResults,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                    TVShowResults results = response.body();
                    List<TVShowResults.Result> allTVShowDetails = results.getResults();
                    TVShowResults.Result showDetails = allTVShowDetails.get(position);
                    List<String> originCountry= new ArrayList<>();
                    originCountry = showDetails.getOriginCountry();
                    String showOriginCountry = originCountry.get(0);
                    Intent i = new Intent(SearchResultsActivity.this, showDetailsActivity.class);
                    i.putExtra("showName",showDetails.getName());
                    i.putExtra("showOverview",showDetails.getOverview());
                    i.putExtra("showFirstAirDate", showDetails.getFirstAirDate());
                    i.putExtra("showVoteAverage", (" "+String.format("%.1f",showDetails.getVoteAverage())));
                    i.putExtra("showVoteNumber", showDetails.getVoteCount().toString());
                    i.putExtra("showPosterPath", showDetails.getPosterPath());
                    i.putExtra("showBackdropPath", showDetails.getBackdropPath());
                    i.putExtra("showOriginalLanguage", showDetails.getOriginalLanguage());
                    i.putExtra("showOriginalName", showDetails.getOriginalName());
                    i.putExtra("showOriginCountry", showOriginCountry);
                    i.putExtra("showID", showDetails.getId());
                    startActivity(i);
            }

            @Override
            public void onFailure(Call<TVShowResults> call, Throwable t) {
                Toast.makeText(SearchResultsActivity.this, "API Call failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}