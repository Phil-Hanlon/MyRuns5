package com.example.myruns5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

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



    public void insert(GPSEntry entry) {

        open();

        ContentValues values = new ContentValues();

        values.put(ImpSQLiteOpenHelper.COLUMN_GPS_DURATION, entry.getDuration());
        values.put(ImpSQLiteOpenHelper.COLUMN_START_LAT, entry.getStartMarkerCoords()[0]);
        values.put(ImpSQLiteOpenHelper.COLUMN_START_LNG, entry.getStartMarkerCoords()[1]);
        values.put(ImpSQLiteOpenHelper.COLUMN_CURRENT_LAT, entry.getCurrentMarkerCoords()[0]);
        values.put(ImpSQLiteOpenHelper.COLUMN_CURRENT_LNG, entry.getCurrentMarkerCoords()[1]);

        database.insert(ImpSQLiteOpenHelper.TABLE_GPS, null, values);

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


    /**
     * Gets the two markers of the saved run
     *
     * @param duration The duration of the desired run;\
     *                 Used as the id for the map data
     */
    public ArrayList<LatLng> get_map_data(int duration) {

        open();

        ArrayList<LatLng> theMarkers = new ArrayList<>();

        // Only gets the 4 columns we care about
        String[] queriedColumns = new String[] {
                ImpSQLiteOpenHelper.COLUMN_START_LAT,
                ImpSQLiteOpenHelper.COLUMN_START_LNG,
                ImpSQLiteOpenHelper.COLUMN_CURRENT_LAT,
                ImpSQLiteOpenHelper.COLUMN_CURRENT_LNG
        };

        String columnSelection = ImpSQLiteOpenHelper.COLUMN_GPS_DURATION + "=" + duration;

        // Queries only the row with the matching duration
        Cursor cursor = database.query(
                ImpSQLiteOpenHelper.TABLE_GPS,
                queriedColumns,
                columnSelection,
                null,
                null,
                null,
                null
        );

        if( cursor.moveToFirst() ) {

            int start_lat = cursor.getInt(ImpSQLiteOpenHelper.COLUMN_START_LAT_I);
            int start_lng = cursor.getInt(ImpSQLiteOpenHelper.COLUMN_START_LNG_I);
            int current_lat = cursor.getInt(ImpSQLiteOpenHelper.COLUMN_START_LAT_I);
            int current_lng = cursor.getInt(ImpSQLiteOpenHelper.COLUMN_START_LNG_I);

            theMarkers.add(new LatLng((double)start_lat, (double)start_lng));
            theMarkers.add(new LatLng((double)current_lat, (double)current_lng));
        }

        close();

        return theMarkers;
    }


    public void delete(String id, String table) {

        open();

        String[] whereArgs = new String[] { id };

        if( table == ImpSQLiteOpenHelper.TABLE_HISTORY) {
            database.delete(ImpSQLiteOpenHelper.TABLE_HISTORY, ImpSQLiteOpenHelper.COLUMN_TIME + "=?", whereArgs);
        }
        else if( table == ImpSQLiteOpenHelper.TABLE_GPS) {

            database.delete(ImpSQLiteOpenHelper.TABLE_GPS, ImpSQLiteOpenHelper.COLUMN_GPS_DURATION + "=?", whereArgs);
            database.delete(ImpSQLiteOpenHelper.TABLE_HISTORY, ImpSQLiteOpenHelper.COLUMN_DURATION + "=?", whereArgs);
        }

        close();
    }


    public void clear() {

        open();

        // Empty table in database
        database.execSQL("delete from " + ImpSQLiteOpenHelper.TABLE_HISTORY);

        close();
    }
}
