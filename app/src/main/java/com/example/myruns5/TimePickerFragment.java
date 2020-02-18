package com.example.myruns5;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {


    TimePickerDialog.OnTimeSetListener listener;


    public TimePickerFragment(TimePickerDialog.OnTimeSetListener listener) {

        this.listener = listener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.HOUR_OF_DAY;
        int minute = calendar.MINUTE;

        return new TimePickerDialog(getActivity(), listener, hour, minute, true);
    }
}
