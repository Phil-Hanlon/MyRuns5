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

import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends DialogFragment {


    public CommentFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);


        String dialog_string = preferences.getString("dialog", "");

        if( dialog_string == "" ) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

            dialogBuilder.setTitle("Comments");

            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SharedPreferences.Editor preferencesEditor = preferences.edit();

                    preferencesEditor.putString("dialog", dialog.toString());

                    dialog.dismiss();
                }
            });

            dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });


            EditText textBlank = new EditText(getActivity());

            dialogBuilder.setView(textBlank);

            return dialogBuilder.create();
        }
        else {

            return null;
        }
    }
}
