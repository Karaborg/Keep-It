package com.example.karaborg.keep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseSubSection extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sqllite_subsectiondatabase";//database adý

    private static final String SUB_TABLE_NAME = "sub_list";
    private static String SUB_NAME = "sub_name";
    private static String SUB_ID = "sub_id";
    private static String SUB_PASSWORD = "sub_password";
    private static String SUB_USER = "sub_user";
    private static String SUB_SEC = "subsec_id";

    public DatabaseSubSection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + SUB_TABLE_NAME + "(" + SUB_ID + " TEXT, " + SUB_NAME + " TEXT," + SUB_PASSWORD + " TEXT," + SUB_USER + " TEXT," + SUB_SEC + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    public void deleteSubSection(String subName){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SUB_TABLE_NAME, SUB_NAME + " = ?", new String[] { String.valueOf(subName) });
        db.close();

    }

    public void AddSubSection(String title, String id, String password, String section, String sub_user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUB_NAME, title);
        values.put(SUB_ID, id);
        values.put(SUB_PASSWORD, password);
        values.put(SUB_SEC, section);
        values.put(SUB_USER, sub_user);

        db.insert(SUB_TABLE_NAME, null, values);
        db.close();

    }

    public String Subsectionname (String subname, String subuser) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = " SELECT " + SUB_NAME + " FROM " + SUB_TABLE_NAME + " WHERE sub_name='" + subname +"'AND sub_user='"+ subuser +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String subsectionname = null;

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    subsectionname = "" + cursor.getString(i);
                }
            } while (cursor.moveToNext());
        }
        db.close();

        return subsectionname;
    }

    public String Subsectionid (String subname, String subuser) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = " SELECT " + SUB_ID + " FROM " + SUB_TABLE_NAME + " WHERE sub_name='" + subname +"'AND sub_user='"+ subuser +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String subsectionid = null;

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    subsectionid = "" + cursor.getString(i);
                }
            } while (cursor.moveToNext());
        }
        db.close();

        return subsectionid;
    }

    public String SubsectionPassword (String subname, String subuser) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = " SELECT " + SUB_PASSWORD + " FROM " + SUB_TABLE_NAME + " WHERE sub_name='" + subname +"'AND sub_user='"+ subuser +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String subsectionPassword = null;

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    subsectionPassword = "" + cursor.getString(i);
                }
            } while (cursor.moveToNext());
        }
        db.close();

        return subsectionPassword;
    }

    public String SubsectionSection (String subname, String subuser) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = " SELECT " + SUB_SEC + " FROM " + SUB_TABLE_NAME + " WHERE sub_name='" + subname +"'AND sub_user='"+ subuser +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String subsectionsection = null;

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    subsectionsection = "" + cursor.getString(i);
                }
            } while (cursor.moveToNext());
        }
        db.close();

        return subsectionsection;
    }

    public ArrayList<String> Subsections(String subsec, String subuser) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = " SELECT " + SUB_NAME + " FROM " + SUB_TABLE_NAME + " WHERE subsec_id='" + subsec +"'AND sub_user='"+ subuser +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> sectionIDs = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    sectionIDs.add(cursor.getString(i));
                }
            } while (cursor.moveToNext());
        }
        db.close();

        return sectionIDs;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
