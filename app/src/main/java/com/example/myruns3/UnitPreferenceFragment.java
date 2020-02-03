package com.example.myruns3;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnitPreferenceFragment extends DialogFragment {


    public UnitPreferenceFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {

        CharSequence[] unit_types = {

            "Metric (Kilometers)",
            "Imperial (Miles)"
        };


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setTitle("Unit Preference");


        final SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        final int checkedItem = preferences.getInt("checkedItem", -1);

        dialogBuilder.setSingleChoiceItems(unit_types, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                // Save the choice in sharedPreferences here!
                SharedPreferences.Editor preferencesEditor = preferences.edit();
                preferencesEditor.putInt("checkedItem", which);
                preferencesEditor.commit();


                dialog.dismiss();
            }
        });

        dialogBuilder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });



        return dialogBuilder.create();
    }
}
