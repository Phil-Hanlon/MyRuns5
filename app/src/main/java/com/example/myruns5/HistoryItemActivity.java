package com.example.myruns5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HistoryItemActivity extends AppCompatActivity {


    HistoryData historyData;
    String entry_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item);

        historyData = new HistoryData(getApplicationContext());


        entry_string = getIntent().getStringExtra("entry_string");


        EditText input_type = findViewById(R.id.input_type);

        input_type.setText(

                entry_string.split(":")[0]
        );


        EditText activity_type = findViewById(R.id.activity_type);

        activity_type.setText(

                entry_string.split(":")[1].split(",")[0].substring(1)
        );


        EditText date_and_time = findViewById(R.id.date_and_time);

        date_and_time.setText(

                entry_string.split(", ")[1].split("\n")[0]
        );

        EditText duration = findViewById(R.id.duration);

        duration.setText(

                entry_string.split(", ")[3]
        );

        EditText distance = findViewById(R.id.distance);

        distance.setText(

                entry_string.split("\n")[2].split(",")[0]
        );


        EditText calories = findViewById(R.id.calories);

        calories.setText(

                entry_string.split("calories burned: ")[1].split(",")[0]
        );


        EditText heart_rate = findViewById(R.id.heart_rate);

        heart_rate.setText(

                entry_string.split("heart rate: ")[1].split("\n")[0]
        );
    }



    public void delete(View view) {

        String time = entry_string.split(", ")[1].split(" ")[0];

        historyData.delete(time, ImpSQLiteOpenHelper.TABLE_HISTORY);

        finish();
    }
}
