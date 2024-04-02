package com.example.trackervision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ScheduleViewActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);
    }
    public void startAddToScheduleActivity (View v) {
        Intent i = new Intent(this, AddToScheduleActivity.class);
        startActivity(i);
    }

    public void startViewMyScheduleActivity (View v) {
        Intent i = new Intent(this, ViewMyScheduleActivity.class);
        startActivity(i);
    }

}