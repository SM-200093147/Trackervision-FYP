package com.example.trackervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class AddToScheduleActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private DatePickerDialog datePickerDialog;
    private EditText dateText, timeText, showNameText, episodeNumberText, seasonNumberText;
    private String userUid;

    int hour, minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_schedule);
        datePicker();
        dateText = findViewById(R.id.dateSelection);
        dateText.setShowSoftInputOnFocus(false);
        timeText = findViewById(R.id.timeSelection);
        timeText.setShowSoftInputOnFocus(false);
        showNameText = findViewById(R.id.showNameText);
        episodeNumberText = findViewById(R.id.episodeNumberText);
        seasonNumberText = findViewById(R.id.seasonNumberText);
        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    private void datePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = makeDateString(dayOfMonth,month,year);
                dateText.setText(date);
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

    public void timePicker (View v){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
                timeText.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minutes));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minutes, true);
        timePickerDialog.setTitle("Please Select a Start Time");
        timePickerDialog.show();
    }

    public void sendToDatabase(View v) {
        writeNewScheduleEntry();

    }

    public void writeNewScheduleEntry() {
        uploadScheduleInfo newScheduleActivity = new uploadScheduleInfo(showNameText.getText().toString(),
                seasonNumberText.getText().toString(),
                episodeNumberText.getText().toString(),
                dateText.getText().toString(),
                timeText.getText().toString(), userUid.toString());


        mDatabase.child("ScheduleEntries").child(userUid).push().setValue(newScheduleActivity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddToScheduleActivity.this, "Successfully added to schedule.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(AddToScheduleActivity.this, "An error has occurred. Event not added to schedule.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}