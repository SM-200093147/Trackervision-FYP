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

public class MyLogActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<uploadLogInfo> list;
    ArrayList<String> keys;
    DatabaseReference databaseReference;
    logRecyclerAdapter adapter;
    TextView emptyLogText;
    String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_log);
        recyclerView = findViewById(R.id.logRecycleView);
        emptyLogText = findViewById(R.id.emptyLogText);
        userUid = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("LogEntries/"+userUid);
        list = new ArrayList<>();
        keys = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new logRecyclerAdapter(this, list, keys);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    uploadLogInfo logInfo = dataSnapshot.getValue(uploadLogInfo.class);
                    list.add(logInfo);
                    keys.add(dataSnapshot.getKey());
                    if(list.size()<0){
                        recyclerView.setVisibility(View.GONE);
                    }
                    else{
                        emptyLogText.setVisibility(View.GONE);
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