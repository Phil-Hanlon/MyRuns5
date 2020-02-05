package com.example.myruns4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImpSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "history.db";   // Specifies the database
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HISTORY = "history";  // Specifies the table in the database

    public static final String COLUMN_INPUT_TYPE = "input_type";    // Specifies the column in the table
    public static final int COLUMN_INPUT_TYPE_I = 0;
    public static final String COLUMN_ACTIVITY_TYPE = "activity_type";
    public static final int COLUMN_ACTIVITY_TYPE_I = 1;
    public static final String COLUMN_DURATION = "duration";
    public static final int COLUMN_DURATION_I = 2;
    public static final String COLUMN_DISTANCE = "distance";
    public static final int COLUMN_DISTANCE_I = 3;
    public static final String COLUMN_CALORIES = "calories";
    public static final int COLUMN_CALORIES_I = 4;
    public static final String COLUMN_HEART_RATE = "heart_rate";
    public static final int COLUMN_HEART_RATE_I = 5;
    public static final String COLUMN_COMMENT = "comments";
    public static final int COLUMN_COMMENT_I = 6;
    public static final String COLUMN_DATE = "date";
    public static final int COLUMN_DATE_I = 7;
    public static final String COLUMN_TIME = "time";
    public static final int COLUMN_TIME_I = 8;


    public ImpSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    public void onCreate(SQLiteDatabase database) {

        String sql_create_table =
                "create table "
                    + TABLE_HISTORY + "("
                    + COLUMN_INPUT_TYPE + " text, "
                    + COLUMN_ACTIVITY_TYPE + " text, "
                    + COLUMN_DURATION + " integer, "
                    + COLUMN_DISTANCE + " integer, "
                    + COLUMN_CALORIES + " integer, "
                    + COLUMN_HEART_RATE + " integer, "
                    + COLUMN_COMMENT + " text, "
                    + COLUMN_DATE + " text, "
                    + COLUMN_TIME + " text"
                + ");";

        database.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("rafa", "onUpgrade() executed");
    }
}
