package com.example.save;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {
    private Context context;
    private SQLiteDatabase db;
    private DBHelper helper;

    // phuog thuc khoi tao
    public DBManager(Context context) {
        this.context = context;
    }

    // phuong thuc mo ket noi
    public DBManager open() {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase(); // lay ra doi tuong sqliteDatabase
        return this;
    }

    // phuong thuc dong ket noi
    public void close() {
        helper.close();
    }

    // phuong thuc insert note
    public void insNote (Note note) {
        ContentValues values = new ContentValues();
        values.put(helper.Title, note.getTitle());
        values.put(helper.Date, note.getCreateDate());
        values.put(helper.Content, note.getContent());
        db.insert(helper.TBName, null, values);
    }

    // phuong thuc update
    public void updNote (int id, String title, String content) {
        ContentValues values = new ContentValues();
        values.put(helper.Title, title);
        //values.put(helper.Date, date);
        values.put(helper.Content, content);
        db.update(helper.TBName, values, helper.ID + "=" + id,null);
    }

    public void delNote(int id) {
        db.delete(helper.TBName, helper.ID + "=" + id, null);
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> kq = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + helper.TBName, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String content = cursor.getString(3);
                kq.add(new Note(id, title, date, content));
            }
            cursor.close();
        }
        return kq;
    }

    public  Note getNoteById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + helper.TBName + "WHERE " + helper.ID + "=" + id, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id1 = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String content = cursor.getString(3);
                return new Note(id1, title, date, content);
            }
        }
        return null;
    }

}

