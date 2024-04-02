package com.example.trackervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMyScheduleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<uploadScheduleInfo> list;
    DatabaseReference databaseReference;
    scheduleRecyclerAdapter adapter;
    TextView emptyScheduleText;
    String userUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_schedule);
        recyclerView = findViewById(R.id.scheduleRecycleView);
        emptyScheduleText = findViewById(R.id.emptyScheduleText);
        userUid = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("ScheduleEntries/"+userUid);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new scheduleRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        uploadScheduleInfo scheduleInfo = dataSnapshot.getValue(uploadScheduleInfo.class);
                        list.add(scheduleInfo);
                        if(list.size()<0){
                            recyclerView.setVisibility(View.GONE);
                        }
                        else{
                            emptyScheduleText.setVisibility(View.GONE);
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