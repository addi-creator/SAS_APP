package com.sos.emergencyapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static  final String DBNAME="SAS.db";

    public DBHelper(Context context) {
        super(context, "SAS.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table user(username TEXT primary key, password TEXT,email TEXT,phoneNumber TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists user");
    }

    public boolean insertData(String username,String password,String email,String phoneNumber){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("email" , email);
        contentValues.put("phoneNumber",phoneNumber);
        long result=db.insert("user",null,contentValues);
        if(result==-1) return false;
        else return true;
    }

    public boolean checkusername(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from user where email =?",new String[] {email});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean checkusernamePassword(String name,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from user where username =? and password=?",new String[] {name,password});
        if(cursor.getCount()>0) return  true;
        else return false;
    }

    public boolean updatePassword(String email,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("password",pass);
        long result=db.update("user",contentValues,"email=?",new String[] {email});
        if(result==-1) return false;
        else return  true;
    }
    public boolean updateData(String name,String email,String phone){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("phoneNumber",phone);
        long result=db.update("user",contentValues,"username=?",new String[] {name});
        if(result==-1) return false;
        else return  true;
    }

    public Cursor getListContents(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from user where username=?",new String[] {name});
        return  cursor;
    }


}
