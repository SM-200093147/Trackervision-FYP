package com.example.trackervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class showDetailsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String userUid, showName, showID, showPosterPath, showBackdropPath, showOverview, showFirstAirDate, showVoteAverage, showVoteNumber, showOriginalName, showOriginalLanguage, showOriginCountry;
    TextView testTextView, showNameTextView, showOverviewTextView, showFirstAirDateTextView, showOriginCountryTextView, showOriginalLanguageTextView, showOriginalNameTextView,showOriginalNameHeading,showAverageRatingTextView,showRatingCountTextView;
    ImageView posterImage, backdropImage;
    Button addToWatchlistButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        addToWatchlistButton = findViewById(R.id.addToWatchlistButton);
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
    }

    public void sendWatchlistToDatabase(View v){
        writeNewWatchlistEntry();
    }

    public void writeNewWatchlistEntry() {
        uploadWatchlistInfo newWatchlistEntry = new uploadWatchlistInfo(
                showNameTextView.getText().toString(),
                showAverageRatingTextView.getText().toString(),
                showFirstAirDateTextView.getText().toString(),
                showPosterPath
                );
        mDatabase.child("WatchlistEntries").child(userUid).child(showPosterPath.substring(1,showPosterPath.length()-4)).setValue(newWatchlistEntry)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            addToWatchlistButton.setText("Remove From Watchlist");
                            Toast.makeText(showDetailsActivity.this, "Successfully added "+showNameTextView.getText().toString()+" to watchlist.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(showDetailsActivity.this, "An error has occurred. Event not added to schedule.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}