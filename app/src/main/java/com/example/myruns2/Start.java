package com.example.myruns2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }



    public void dateClicked(View view) {

        DialogFragment datePickerDialog = new DatePickerFragment();
        datePickerDialog.show(getSupportFragmentManager(), "datePickerDialog");
    }


    public void timeClicked(View view) {

        DialogFragment timePickerDialog = new TimePickerFragment();
        timePickerDialog.show(getSupportFragmentManager(), "timePickerDialog");
    }

    public void durationClicked(View view) {

        alertDialog("Duration", "");
    }

    public void distanceClicked(View view) {

        alertDialog("Distance", "");
    }

    public void caloriesClicked(View view) {

        alertDialog("Calories", "");
    }


    public void heartRateClicked(View view) {

        alertDialog("Heart Rate", "");
    }


    public void commentClicked(View view) {

        alertDialog("Comment", "How did it go? Notes here.");
    }


    /**
     *
     * @param title The title displayed at the top of the dialog-box
     * @param hint The greyed out text that fills the blank before the user types
     */
    private void alertDialog(String title, String hint) {

        AlertDialog.Builder durationDialogBuilder = new AlertDialog.Builder(this);

        durationDialogBuilder.setTitle(title);

        final EditText inputField = new EditText(this);

        if( hint != "" ) inputField.setHint( hint );

        durationDialogBuilder.setView(inputField);

        durationDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // inputField.getText() gets the input
                // Use it for whatever you need to here!
                // input = inputField.getText().toString();
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

        // Save things

        finish();
    }


    public void cancelClicked(View view) {

        Toast.makeText(this, "Entry discarded.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
