package com.example.trackervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class showDetailsActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference mDatabase;
    String userUid, showName, showPosterPath, showBackdropPath, showOverview, showFirstAirDate, showVoteAverage, showVoteNumber, showOriginalName, showOriginalLanguage, showOriginCountry;
    TextView showNameTextView, showOverviewTextView, showFirstAirDateTextView, showOriginCountryTextView, showOriginalLanguageTextView, showOriginalNameTextView,showOriginalNameHeading,showAverageRatingTextView,showRatingCountTextView;
    ImageView posterImage, backdropImage;
    Button addToWatchlistButton,addToLogButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        addToWatchlistButton = findViewById(R.id.addToWatchlistButton);
        addToLogButton = findViewById(R.id.addToLogButton);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
        });

        assignDetails();



    }

    public void assignDetails(){
        Intent i = getIntent();
        showName = i.getStringExtra ("showName");
        showPosterPath = i.getStringExtra("showPosterPath");
        showBackdropPath = i.getStringExtra("showBackdropPath");
        showOverview = i.getStringExtra("showOverview");
        showFirstAirDate = i.getStringExtra("showFirstAirDate");
        showVoteAverage = i.getStringExtra("showVoteAverage");
        showVoteNumber = i.getStringExtra("showVoteNumber");
        showOriginalLanguage= i.getStringExtra("showOriginalLanguage");
        showOriginalName = i.getStringExtra("showOriginalName");
        showOriginCountry = i.getStringExtra("showOriginCountry");

        backdropImage = findViewById(R.id.backdropImage);
        posterImage = findViewById(R.id.posterImage);
        showNameTextView = findViewById(R.id.showTitle);
        showOverviewTextView = findViewById(R.id.overviewTextView);
        showFirstAirDateTextView = findViewById(R.id.firstAiredDateTextView);
        showOriginCountryTextView = findViewById(R.id.countryOfOriginTextView);
        showOriginalLanguageTextView = findViewById(R.id.originalLanguageTextView);
        showOriginalNameTextView = findViewById(R.id.originalNameTextView);
        showOriginalNameHeading = findViewById(R.id.originalNameHeading);
        showAverageRatingTextView = findViewById(R.id.averageRatingTextView);
        showRatingCountTextView = findViewById(R.id.voteCountTextView);

        String posterURL = "https://image.tmdb.org/t/p/w1280/"+showPosterPath;
        String backdropURL = "https://image.tmdb.org/t/p/w1280/"+showBackdropPath;


        Glide.with(this).load(posterURL).into(posterImage);
        Glide.with(this).load(backdropURL).into(backdropImage);

        showNameTextView.setText(showName);
        showOverviewTextView.setText(showOverview);
        showFirstAirDateTextView.setText(showFirstAirDate);
        showOriginCountryTextView.setText(showOriginCountry);
        showOriginalLanguageTextView.setText(showOriginalLanguage);
        showOriginalNameTextView.setText(showOriginalName);
        showAverageRatingTextView.setText(showVoteAverage);
        showRatingCountTextView.setText("("+showVoteNumber+")");

        String showID = showPosterPath.substring(1,showPosterPath.length()-4);
        DatabaseReference watchlistDatabase = mDatabase.child("WatchlistEntries").child(userUid);
        DatabaseReference logDatabase = mDatabase.child("LogEntries").child(userUid);
        watchlistDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(showID)){
                    addToWatchlistButton.setText("Remove From Watchlist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(showID)){
                    addToLogButton.setText("Remove From Log");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendWatchlistToDatabase(View v){
        writeNewWatchlistEntry();
    }

    public void onAddToLogButtonClick(View v){
        addToLogButton();
    }

    public void addToLogButton(){
            String showID = showPosterPath.substring(1,showPosterPath.length()-4);
            DatabaseReference logDatabase = mDatabase.child("LogEntries").child(userUid);
            logDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(showID)){
                        Toast.makeText(showDetailsActivity.this, "Successfully removed "+showNameTextView.getText().toString()+" from log.", Toast.LENGTH_SHORT).show();
                        logDatabase.child(showID).removeValue();
                        addToLogButton.setText("Add To Log");
                    } else{
                        //addToLogButton.setText("Remove From Log");
                        Intent i = new Intent(showDetailsActivity.this, LogDateActivity.class);
                        i.putExtra("showName",showName);
                        i.putExtra("showFirstAirDate", showFirstAirDate);
                        i.putExtra("showPosterPath",showPosterPath);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    public void writeNewWatchlistEntry() {
        uploadWatchlistInfo newWatchlistEntry = new uploadWatchlistInfo(
                showNameTextView.getText().toString(),
                showAverageRatingTextView.getText().toString(),
                showFirstAirDateTextView.getText().toString(),
                showPosterPath
                );
        String showID = showPosterPath.substring(1,showPosterPath.length()-4);
        DatabaseReference watchlistDatabase = mDatabase.child("WatchlistEntries").child(userUid);
        watchlistDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(showID)){
                    Toast.makeText(showDetailsActivity.this, "Successfully removed "+showNameTextView.getText().toString()+" from watchlist.", Toast.LENGTH_SHORT).show();
                    watchlistDatabase.child(showID).removeValue();
                    addToWatchlistButton.setText("Add To Watchlist");
                } else{
                    addToWatchlistButton.setText("Remove From Watchlist");
                    mDatabase.child("WatchlistEntries").child(userUid).child(showPosterPath.substring(1,showPosterPath.length()-4)).setValue(newWatchlistEntry);
                    Toast.makeText(showDetailsActivity.this, "Successfully added "+showNameTextView.getText().toString()+" to watchlist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }

    }
