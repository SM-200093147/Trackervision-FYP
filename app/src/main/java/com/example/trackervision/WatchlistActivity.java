package com.example.trackervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WatchlistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<uploadWatchlistInfo> list;
    ArrayList<String> keys;
    DatabaseReference databaseReference;
    watchlistRecyclerAdapter adapter;
    TextView emptyWatchlistText;
    String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        recyclerView = findViewById(R.id.watchlistRecycleView);
        emptyWatchlistText = findViewById(R.id.emptyWatchlistText);
        userUid = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("WatchlistEntries/"+userUid);
        list = new ArrayList<>();
        keys = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new watchlistRecyclerAdapter(this, list, keys);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    uploadWatchlistInfo watchlistInfo = dataSnapshot.getValue(uploadWatchlistInfo.class);
                    list.add(watchlistInfo);
                    keys.add(dataSnapshot.getKey());
                    if(list.size()<0){
                        recyclerView.setVisibility(View.GONE);
                    }
                    else{
                        emptyWatchlistText.setVisibility(View.GONE);
                    }
                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}