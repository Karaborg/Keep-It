package com.example.karaborg.keep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseSection extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sqllite_sectiondatabase";//database adý

    private static final String SEC_TABLE_NAME = "sec_list";
    private static String SEC_NAME = "sec_name";
    private static String SEC_ID = "sec_id";
    private static String SEC_USER = "sec_user";  //insert user here to define who has which sections

    public DatabaseSection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + SEC_TABLE_NAME + "(" + SEC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SEC_NAME + " TEXT," + SEC_USER + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    public void deleteSection(String secName){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SEC_TABLE_NAME, SEC_NAME + " = ?", new String[] { String.valueOf(secName) });
        db.close();

    }

    public void AddSection(String section_name, String which_user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SEC_NAME, section_name);
        values.put(SEC_USER, which_user);

        db.insert(SEC_TABLE_NAME, null, values);
        db.close(); //DatabaseUser Baðlantýsýný kapattýk*/

    }

    public ArrayList<String> sections(String user) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = " SELECT " + SEC_NAME + " FROM " + SEC_TABLE_NAME + " WHERE sec_user='" + user +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> sectionlist = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    sectionlist.add(cursor.getString(i));
                }
            } while (cursor.moveToNext());
        }
        db.close();

        return sectionlist;
    }

    public void kitapDuzenle(String kitap_adi, String kitap_yazari,String kitap_basim_yili,String kitap_fiyat,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(SEC_NAME, kitap_adi);
        values.put(SEC_USER, kitap_yazari);

        // updating row
        db.update(SEC_TABLE_NAME, values, SEC_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int getRowCount() {
        // Bu method bu uygulamada kullanýlmýyor ama her zaman lazým olabilir.Tablodaki row sayýsýný geri döner.
        //Login uygulamasýnda kullanacaðýz
        String countQuery = "SELECT  * FROM " + SEC_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }


    public void resetTables(){
        //Bunuda uygulamada kullanmýyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SEC_TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}

