package com.example.trackervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class LogDateActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    String userUid, showName, showPosterPath, showFirstAirDate;
    private DatabaseReference mDatabase;
    EditText showDateCompleted;
    TextView addToLogTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_date);
        datePicker();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userUid = FirebaseAuth.getInstance().getUid();
        Intent i = getIntent();
        showName = i.getStringExtra ("showName");
        showPosterPath = i.getStringExtra("showPosterPath");
        showDateCompleted = findViewById(R.id.logDateEditText);
        addToLogTitle = findViewById(R.id.logDateTitle);
        addToLogTitle.setText("Please enter the date that you finished watching " + showName + " below.");

    }

    public void addLogEntryToDatabase(View v){
        writeNewLogEntry();
    }
    public void writeNewLogEntry() {
        uploadLogInfo newLogEntry = new uploadLogInfo(
                showName,
                showFirstAirDate,
                showPosterPath,
                showDateCompleted.getText().toString()
        );
        String showID = showPosterPath.substring(1,showPosterPath.length()-4);
        mDatabase.child("LogEntries").child(userUid).child(showID).setValue(newLogEntry);
        Toast.makeText(this, "Successfully added "+showName+" to log.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void datePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = makeDateString(dayOfMonth,month,year);
                showDateCompleted.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH);
        int dayOfMonth =calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + "/" + month + "/" + year;
    }
    public void startDatePicker (View v){
        datePickerDialog.show();
    }
}