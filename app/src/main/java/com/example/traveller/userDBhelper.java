package com.example.traveller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class userDBhelper extends SQLiteOpenHelper {
    public userDBhelper(Context context) {
        super(context, "userdetail", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userdetail (name Text, eid Text primary key, password Text, pnumber Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists userdetail");
    }

    public boolean insertUser (String name, String eid, String password, String pnumber){
        if(name.length() == 0 || eid.length() == 0 || password.length() == 0 || pnumber.length() != 10 )
            return false;
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("eid", eid);
        contentValues.put("password", password);
        contentValues.put("pnumber", pnumber);
        long result = DB.insert("userdetail", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from userdetail", null);
        return cursor;
    }
}
