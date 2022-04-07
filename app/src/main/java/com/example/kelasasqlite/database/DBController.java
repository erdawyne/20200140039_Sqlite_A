package com.example.kelasasqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {
    public DBController( Context context) {
        super(context, "ProdiTI", null, 1); //Perintah DDL
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table teman(id integer primary key, nama text, telpon text)");   //Membuat tabel
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists teman"); //Jika update harus dihapus
        onCreate(db);

    }

    public void insertData(HashMap<String,String> queryValues){
        SQLiteDatabase basisdata = this.getWritableDatabase();
        ContentValues nilai = new ContentValues();//Ambil data
        nilai.put("nama",queryValues.get("nama"));
        nilai.put("telpon",queryValues.get("telpon"));
        basisdata.insert("teman",null, nilai);//Masukkan ke database
        basisdata.close(); //Basisdata ditutup agar tidak terbuka terus
    }

    public ArrayList<HashMap<String,String>> getAllTeman(){
        ArrayList<HashMap<String,String>> daftarTeman;
        daftarTeman = new ArrayList<>();
        String selectQuery = "Select * from teman"; //Proses membaca dari tabel teman
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",cursor.getString(0));
                map.put("nama",cursor.getString(1));
                map.put("telpon",cursor.getString(2));
                daftarTeman.add(map);
            } while (cursor.moveToNext());
        }

        return daftarTeman;
    }
}
