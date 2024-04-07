package com.example.trackervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button logoutButton;
    TextView textView;
    FirebaseUser user;
    EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEditText = findViewById(R.id.searchEditText);

        auth=FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logoutButton);
        textView = findViewById(R.id.userDetails);
        user = auth.getCurrentUser();
        if(user == null){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void startScheduleViewActivity (View v) {
        Intent i = new Intent(this, ScheduleViewActivity.class);
        startActivity(i);

    }

    public void startRecommendationsActivity (View v) {
        Intent i = new Intent(this, RecommendationsActivity.class);
        startActivity(i);

    }

    public void searchResultsActivity (View v){
        if (TextUtils.isEmpty(searchEditText.getText())){
            Toast.makeText(this, "Please enter a show name before searching.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(this, SearchResultsActivity.class);
            i.putExtra("searchResults", searchEditText.getText().toString());
            startActivity(i);
        }
    }
}