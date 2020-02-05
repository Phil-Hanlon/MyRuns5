package com.example.myruns3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Start extends AppCompatActivity {

    private HistoryData historyData;

    private int duration;
    private int distance;
    private int calories;
    private int heart_rate;
    private String comment;

    // Will contain the year, month, and day returned by the datepicker dialog
    // In that order
    private int[] date;

    // Will contain the hour and minute returned by the timepicker dialog
    // in that order
    private int[] time;

    private enum WhichDialog {

        DURATION,
        DISTANCE,
        CALORIES,
        HEART_RATE,
        COMMENT
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();

        // Initializes the date and time to today
        date = new int[] {
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        };

        time = new int[] {
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE)
        };

        setContentView(R.layout.activity_start);

        historyData = new HistoryData(this);
    }



    public void dateClicked(View view) {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date[0] = year;
                date[1] = month;
                date[2] = dayOfMonth;
            }
        };

        DialogFragment datePickerDialog = new DatePickerFragment(listener);
        datePickerDialog.show(getSupportFragmentManager(), "datePickerDialog");
    }


    public void timeClicked(View view) {

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                time[0] = hourOfDay;
                time[1] = minute;
            }
        };

        DialogFragment timePickerDialog = new TimePickerFragment(listener);
        timePickerDialog.show(getSupportFragmentManager(), "timePickerDialog");
    }

    public void durationClicked(View view) {

        alertDialog("Duration", "", true, WhichDialog.DURATION);
    }

    public void distanceClicked(View view) {

        alertDialog("Distance", "", true, WhichDialog.DISTANCE);
    }

    public void caloriesClicked(View view) {

        alertDialog("Calories", "", true, WhichDialog.CALORIES);
    }


    public void heartRateClicked(View view) {

        alertDialog("Heart Rate", "", true, WhichDialog.HEART_RATE);
    }


    public void commentClicked(View view) {

        alertDialog("Comment", "How did it go? Notes here.", false, WhichDialog.COMMENT);
    }


    /**
     *
     * @param title The title displayed at the top of the dialog-box
     * @param hint The greyed out text that fills the blank before the user types
     */
    private void alertDialog(String title, String hint, final boolean useNumpad, final WhichDialog whichDialog) {

        AlertDialog.Builder durationDialogBuilder = new AlertDialog.Builder(this);

        durationDialogBuilder.setTitle(title);

        final EditText inputField = new EditText(this);

        if( hint != "" ) inputField.setHint( hint );

        if(useNumpad) inputField.setInputType(InputType.TYPE_CLASS_NUMBER);


        durationDialogBuilder.setView(inputField);

        durationDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // inputField.getText() gets the input
                // Use it for whatever you need to here!



                switch(whichDialog) {

                    case CALORIES:

                        calories = Integer.parseInt(inputField.getText().toString());
                        break;

                    case DISTANCE:

                        distance = Integer.parseInt(inputField.getText().toString());
                        break;

                    case DURATION:

                        duration = Integer.parseInt(inputField.getText().toString());
                        break;

                    case HEART_RATE:

                        heart_rate = Integer.parseInt(inputField.getText().toString());
                        break;

                    case COMMENT:

                        comment = inputField.getText().toString();
                        break;
                }

            }
        });


        durationDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // closes the dialog without doing anything
                dialog.cancel();
            }
        });


        // Displays the built dialog!
        durationDialogBuilder.show();
    }


    public void saveClicked(View view) {

        // Make the entry in history!

        Intent intent = getIntent();

        String input_type = getIntent().getStringExtra("input_type");
        String activity_type = getIntent().getStringExtra("activity_type");

        Entry entry = new Entry(input_type, activity_type, duration, distance, calories, heart_rate, comment, date, time);

        historyData.insert(entry);



        // Create the toast to display
        final Calendar calendar = Calendar.getInstance();

        String id =
                "" +
                calendar.get(Calendar.DAY_OF_MONTH)
                + calendar.get(Calendar.MONTH)
                + calendar.get(Calendar.YEAR)
                + calendar.get(Calendar.MINUTE)
                + calendar.get(Calendar.HOUR);

        Toast.makeText(
                this,
                "Entry made. (id: " + id + ")",
                Toast.LENGTH_SHORT).show();

        finish();
    }


    public void cancelClicked(View view) {

        Toast.makeText(this, "Entry discarded.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
