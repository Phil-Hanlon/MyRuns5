package com.example.myruns3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImpSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "history.db";   // Specifies the database
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HISTORY = "history";  // Specifies the table in the database
    public static final String COLUMN_CALORIES = "calories";    // Specifies the column in the table

    public static final String[] ALL_COLUMNS = {"calories"};


    public ImpSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    public void onCreate(SQLiteDatabase database) {

        String sql_create_table =
                "create table "
                + TABLE_HISTORY + "("
                + COLUMN_CALORIES + " integer);";

        database.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("rafa", "onUpgrade() executed");
    }
}
