package com.example.myruns3;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private ArrayList<Integer> arrayList;
    private ArrayAdapter<Integer> arrayAdapter;

    private ImpSQLiteOpenHelper impSQLiteOpenHelper;
    private SQLiteDatabase database;

    public HistoryFragment(Context context) {
        // Required empty public constructor

        impSQLiteOpenHelper = new ImpSQLiteOpenHelper(context);
        database = impSQLiteOpenHelper.getWritableDatabase();


        arrayList = get_entries_from_database();

        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);
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


        // Generate update-button
        Button updateButton = view.findViewById(R.id.update_button);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayList.clear();
                arrayList.addAll( get_entries_from_database() );
                arrayAdapter.notifyDataSetChanged();
            }
        });



        Button clearButton = view.findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clear_database();
            }
        });
    }


    private ArrayList<Integer> get_entries_from_database() {

        ArrayList<Integer> entries = new ArrayList<>();

        Cursor cursor = database.query(
                ImpSQLiteOpenHelper.TABLE_HISTORY,
                null,
                null, null, null, null, null
        );

        if( cursor.moveToFirst() ) {

            while( !cursor.isAfterLast() ) {

                Integer calories = cursor.getInt(0);

                entries.add(calories);

                cursor.moveToNext();
            }
        }


        cursor.close();

        return entries;
    }


    private void clear_database() {

        // Empty table in database
        database.execSQL("delete from " + ImpSQLiteOpenHelper.TABLE_HISTORY);

        // Empty view's arraylist
        arrayList.clear();
        arrayAdapter.notifyDataSetChanged();
    }
}
