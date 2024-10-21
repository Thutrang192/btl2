package com.example.save;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // khai bao cac tham so cho db
    public static final String DBname = "note.db";
    public static final int DBVersion = 1;

    public static final String TBName = "tblNote";
    public static final String ID = "id";
    public static final String Title = "title";
    public static final String Date = "date";
    public static final String Content = "content";

    public DBHelper(@Nullable Context context) {
        super(context, DBname, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TBName + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Title + " TEXT, " +
                Date + " TEXT, " +
                Content + " TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBName);
        onCreate(db);

    }
}
