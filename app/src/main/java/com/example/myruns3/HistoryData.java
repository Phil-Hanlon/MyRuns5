package com.example.myruns3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class HistoryData {

    private ImpSQLiteOpenHelper impSQLiteOpenHelper;

    private SQLiteDatabase database;


    public HistoryData(Context context) {

        this.impSQLiteOpenHelper = new ImpSQLiteOpenHelper(context);
    }

    public void open() { database = impSQLiteOpenHelper.getWritableDatabase(); }

    public void close() { impSQLiteOpenHelper.close(); }


    public void insert(int calories) {

        ContentValues values = new ContentValues();

        values.put(ImpSQLiteOpenHelper.COLUMN_CALORIES, calories);

        database.insert(ImpSQLiteOpenHelper.TABLE_HISTORY, null, values);
    }
}
