package com.example.myruns3;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment implements View.OnClickListener {


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);

        view.findViewById(R.id.start_button).setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        createSpinner(R.id.input_type_spinner, R.array.input_types);
        createSpinner(R.id.activity_type_spinner, R.array.activity_types);
    }


    public void createSpinner(int spinner_id, int array_id) {

        Spinner spinner = (Spinner) getView().findViewById(spinner_id);

        ArrayAdapter<CharSequence> spinnerAdapter  = ArrayAdapter.createFromResource(
                getActivity(), array_id, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
    }



    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.start_button:

                Intent intent = new Intent(getActivity(), Start.class);

                Spinner input_type = getView().findViewById(R.id.input_type_spinner);
                Spinner activity_type = getView().findViewById(R.id.activity_type_spinner);

                intent.putExtra("input_type", input_type.getSelectedItem().toString());
                intent.putExtra("activity_type", activity_type.getSelectedItem().toString());

                // Starts the "Start" activity with the input-type and the activity-type known
                startActivity(intent);

                break;

            case R.id.sync_button:

                break;

            default:

                break;
        }
    }

}
