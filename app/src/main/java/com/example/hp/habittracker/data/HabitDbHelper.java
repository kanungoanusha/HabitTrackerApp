package com.example.hp.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hp.habittracker.data.HabitContract.HabitEntry;

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + "("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_DATE_YEAR + " INTEGER NOT NULL DEFAULT 2018, "
                + HabitEntry.COLUMN_DATE_MONTH + " TEXT NOT NULL DEFAULT 1, "
                + HabitEntry.COLUMN_DATE_DAY + " INTEGER NOT NULL DEFAULT 1);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

        db.execSQL(SQL_DELETE_TABLE);

        onCreate(db);
    }

    public void insertHabit(String habitName, int yearOfDate, String monthOfDate, int dayOfDate) {
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, habitName);
        values.put(HabitEntry.COLUMN_DATE_YEAR, yearOfDate);
        values.put(HabitEntry.COLUMN_DATE_MONTH, monthOfDate);
        values.put(HabitEntry.COLUMN_DATE_DAY, dayOfDate);

        SQLiteDatabase database = getWritableDatabase();
        database.insert(HabitEntry.TABLE_NAME, null, values);
    }

    public Cursor readAllHabits() {

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_DATE_YEAR,
                HabitEntry.COLUMN_DATE_MONTH,
                HabitEntry.COLUMN_DATE_DAY
        };

        SQLiteDatabase database = getReadableDatabase();

        return database.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
    }
}