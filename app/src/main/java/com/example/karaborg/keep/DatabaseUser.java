package com.example.karaborg.keep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseUser extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sqllite_userdatabase";//database adý

    private static final String USER_TABLE_NAME = "user_list";
    private static String USER_NAME = "user_name";
    private static String USER_ID = "user_id";
    private static String USER_PASSWORD = "user_password";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    public void deleteUser(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE_NAME, USER_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();

    }

    public void AddUser(String user_name, String user_password) {

        //kitapEkle methodu ise adý üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user_name);
        values.put(USER_PASSWORD, user_password);

        db.insert(USER_TABLE_NAME, null, values);
        db.close(); //DatabaseUser Baðlantýsýný kapattýk*/

    }

    public HashMap<String, String> userDetail(String encryptedUsername){

        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME+ " WHERE user_name='" + encryptedUsername +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        if(cursor.getCount() > 0){

            user.put(USER_NAME, cursor.getString(1));
            user.put(USER_PASSWORD, cursor.getString(2));

        }
        cursor.close();
        db.close();
        // return kitap
        return user;
    }

    public ArrayList<HashMap<String, String>> users(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> userlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                userlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return kitap liste
        return userlist;
    }

    public void kitapDuzenle(String kitap_adi, String kitap_yazari,String kitap_basim_yili,String kitap_fiyat,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(USER_NAME, kitap_adi);
        values.put(USER_PASSWORD, kitap_yazari);

        // updating row
        db.update(USER_TABLE_NAME, values, USER_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int getRowCount() {
        // Bu method bu uygulamada kullanýlmýyor ama her zaman lazým olabilir.Tablodaki row sayýsýný geri döner.
        //Login uygulamasýnda kullanacaðýz
        String countQuery = "SELECT  * FROM " + USER_TABLE_NAME;
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
        db.delete(USER_TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}

