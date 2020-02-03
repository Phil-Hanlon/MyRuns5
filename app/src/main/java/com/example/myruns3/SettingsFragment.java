package com.example.myruns3;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createClickListeners();
    }



    public void createClickListeners() {

        getView().findViewById(R.id.user_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UserProfile.class);

                startActivity(intent);
            }
        });


        getView().findViewById(R.id.unit_preference).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment unitPreferencesFragment = new UnitPreferenceFragment();

                unitPreferencesFragment.show(getActivity().getSupportFragmentManager(), "unitPreferencesFragment");
            }
        });


        getView().findViewById(R.id.comments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragment commentFragment = new CommentFragment();

                commentFragment.show(getFragmentManager(), "commentFragment");
            }
        });


        getView().findViewById(R.id.webpage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://web.cs.dartmouth.edu/"));

                startActivity(intent);
            }
        });
    }



}
