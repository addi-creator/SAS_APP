package com.sos.emergencyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmergencyContactHelperDB extends SQLiteOpenHelper {
    public static  final String DBNAME="contact.db";

    public EmergencyContactHelperDB(Context context) {
        super(context, "contact.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table contactuser(name TEXT ,phoneNumber TEXT primary key,username TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists contactuser");
    }

    public boolean insertData(String name,String phoneNumber,String username){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("phoneNumber",phoneNumber);
        contentValues.put("username",username);
        long result=db.insert("contactuser",null,contentValues);
        if(result==-1) return false;
        else return true;
    }

    public int countContact(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from contactuser",new String[] {});
        return cursor.getCount();
    }
    public boolean checkusernamePassword(String phone){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from contactuser where phoneNumber=?",new String[] {phone});
        if(cursor.getCount()>0) return  true;
        else return false;
    }

    public Cursor getListContents(String user){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from contactuser where username=?",new String[] {user});
        return  cursor;
    }

    public boolean checkPhone(String ph){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from contactuser where phoneNumber =?",new String[] {ph});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public boolean deletePhone(String ph){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from contactuser where phoneNumber =?",new String[] {ph});
        if(cursor.getCount()>0){
            long result=db.delete("contactuser","phoneNumber=?",new String[] {ph});
            if(result==-1) return false;
            else return  true;
        }else
            return false;
    }


}
