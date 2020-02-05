package com.example.myruns4;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private ArrayList<String> entries;
    private ArrayAdapter<String> arrayAdapter;

    private HistoryData historyData;

    public HistoryFragment(Context context) {
        // Required empty public constructor

        historyData = new HistoryData(context);

        entries = historyData.get_all_as_strings();

        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, entries);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Generates listview
        ListView listView = view.findViewById(R.id.history_list);

        listView.setAdapter(arrayAdapter);


        // Starts the new activity when one of the entries gets clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView)view;

                Intent intent = new Intent(getActivity(), HistoryItemActivity.class);

                intent.putExtra("entry_string", (textView.getText()));

                startActivityForResult(intent, 0);
            }
        });


        // Generate update-button
        Button updateButton = view.findViewById(R.id.update_button);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entries.clear();
                entries.addAll( historyData.get_all_as_strings() );
                arrayAdapter.notifyDataSetChanged();
            }
        });



        Button clearButton = view.findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Deletes the data from the database
                historyData.clear();

                // Empty view's arraylist
                entries.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Updates the list in case the viewed run was deleted
        entries.clear();
        entries.addAll( historyData.get_all_as_strings() );
        arrayAdapter.notifyDataSetChanged();
    }
}
