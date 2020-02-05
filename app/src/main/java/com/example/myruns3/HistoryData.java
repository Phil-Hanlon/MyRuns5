package com.example.myruns3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HistoryData {

    private ImpSQLiteOpenHelper impSQLiteOpenHelper;

    private SQLiteDatabase database;


    public HistoryData(Context context) {

        this.impSQLiteOpenHelper = new ImpSQLiteOpenHelper(context);
    }

    private void open() { database = impSQLiteOpenHelper.getWritableDatabase(); }

    private void close() { impSQLiteOpenHelper.close(); }


    public void insert(Entry entry) {

        open();

        ContentValues values = new ContentValues();

        // Strings
        values.put(ImpSQLiteOpenHelper.COLUMN_INPUT_TYPE, entry.input_type);
        values.put(ImpSQLiteOpenHelper.COLUMN_ACTIVITY_TYPE, entry.activity_type);

        // Integers
        values.put(ImpSQLiteOpenHelper.COLUMN_DURATION, entry.duration);
        values.put(ImpSQLiteOpenHelper.COLUMN_DISTANCE, entry.distance);
        values.put(ImpSQLiteOpenHelper.COLUMN_CALORIES, entry.calories);
        values.put(ImpSQLiteOpenHelper.COLUMN_HEART_RATE, entry.heart_rate);

        // Strings
        values.put(ImpSQLiteOpenHelper.COLUMN_COMMENT, entry.comment);
        values.put(ImpSQLiteOpenHelper.COLUMN_DATE, entry.date);
        values.put(ImpSQLiteOpenHelper.COLUMN_TIME, entry.time);

        database.insert(ImpSQLiteOpenHelper.TABLE_HISTORY, null, values);

        close();
    }



    public ArrayList<String> get_all_as_strings() {

        open();

        ArrayList<String> entries = new ArrayList<>();

        Cursor cursor = database.query(
                ImpSQLiteOpenHelper.TABLE_HISTORY,
                null,
                null, null, null, null, null
        );

        if( cursor.moveToFirst() ) {

            while( !cursor.isAfterLast() ) {

                // Gets the data from the entire row of the table
                Entry entry = new Entry(
                        cursor.getString(ImpSQLiteOpenHelper.COLUMN_INPUT_TYPE_I),
                        cursor.getString(ImpSQLiteOpenHelper.COLUMN_ACTIVITY_TYPE_I),

                        cursor.getInt(ImpSQLiteOpenHelper.COLUMN_DURATION_I),
                        cursor.getInt(ImpSQLiteOpenHelper.COLUMN_DISTANCE_I),
                        cursor.getInt(ImpSQLiteOpenHelper.COLUMN_CALORIES_I),
                        cursor.getInt(ImpSQLiteOpenHelper.COLUMN_HEART_RATE_I),

                        cursor.getString(ImpSQLiteOpenHelper.COLUMN_COMMENT_I),
                        cursor.getString(ImpSQLiteOpenHelper.COLUMN_DATE_I),
                        cursor.getString(ImpSQLiteOpenHelper.COLUMN_TIME_I)
                );


                // Adds the entry to the list (as a string)
                entries.add(entry.toString());

                cursor.moveToNext();
            }
        }


        cursor.close();

        close();

        return entries;
    }


    public void delete(String id) {

        open();

        String[] whereArgs = new String[] { id };

        database.delete(ImpSQLiteOpenHelper.TABLE_HISTORY, ImpSQLiteOpenHelper.COLUMN_TIME + "=?", whereArgs);


        close();
    }


    public void clear() {

        open();

        // Empty table in database
        database.execSQL("delete from " + ImpSQLiteOpenHelper.TABLE_HISTORY);

        close();
    }
}
