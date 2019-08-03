package com.example.karaborg.keep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseNotes extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sqllite_notesdatabase";//database adý

    private static final String NOTE_TABLE_NAME = "note_list";
    private static String NOTE_TITLE = "note_title";
    private static String NOTE_TEXT = "note_text";

    public DatabaseNotes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + NOTE_TABLE_NAME + "(" +  NOTE_TITLE + " TEXT," + NOTE_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    public void deleteUser(String note_title){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE_NAME, NOTE_TITLE + " = ?", new String[] { String.valueOf(note_title) });
        db.close();

    }

    public void AddNote(String note_title, String note_text) {

        //kitapEkle methodu ise adý üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note_title);
        values.put(NOTE_TEXT, note_text);

        db.insert(NOTE_TABLE_NAME, null, values);
        db.close(); //DatabaseUser Baðlantýsýný kapattýk*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
