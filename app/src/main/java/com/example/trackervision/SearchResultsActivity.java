package com.example.trackervision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {
    public static String baseURL="https://api.themoviedb.org";
    public static String language="en-uk";
    public static String apiKey="a07d8418569b36aba9e3ae4a58107505";
    RecyclerView searchResultsRecyclerView;
    LinearLayoutManager searchResultsLayoutManager;
    recommendationsRecyclerAdapter searchResultsRecyclerAdapter;
    List<TVShowResults.Result> searchResultsList = new ArrayList<>();
    String searchResults;
    TextView searchResultsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent i = getIntent();
        searchResults = i.getStringExtra ("searchResults");
        searchResultsTextView = findViewById(R.id.searchResultName);
        searchResultsTextView.setText(searchResults);
        searchResultsLayoutManager = new LinearLayoutManager(this);
        searchResultsRecyclerView = findViewById(R.id.searchRecyclerView);
        searchResultsRecyclerAdapter = new recommendationsRecyclerAdapter(searchResultsList);
        searchResultsRecyclerView.setLayoutManager(searchResultsLayoutManager);
        searchResultsRecyclerView.setAdapter(searchResultsRecyclerAdapter);
        fetchSearchResults();



    }
    private void fetchSearchResults(){
        retrofitInterface.getRetrofitClient().searchTVShows(searchResults,apiKey,language).enqueue(new Callback<TVShowResults>() {
            @Override
            public void onResponse(Call<TVShowResults> call, Response<TVShowResults> response) {
                if(response.isSuccessful()){
                    searchResultsList.addAll(response.body().getResults());
                    searchResultsRecyclerAdapter.notifyDataSetChanged();
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
}