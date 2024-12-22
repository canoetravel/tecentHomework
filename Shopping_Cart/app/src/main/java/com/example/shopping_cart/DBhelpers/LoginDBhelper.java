package com.example.shopping_cart.DBhelpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopping_cart.entity.Personinfo;


import java.util.ArrayList;
import java.util.List;

public class LoginDBhelper extends SQLiteOpenHelper {
    private static final String db_name="login.db";
    private static final int db_version=1;
    private static final String table_login="initial_table";
    private static final String table_remember="remember_table";
    private static LoginDBhelper mHelper=null;
    private static SQLiteDatabase MRDB=null,MWDB=null;

    private LoginDBhelper(Context context){
        super(context,db_name,null,db_version);
    }

    public static LoginDBhelper getInstance(Context context){
        if(mHelper==null){
            mHelper=new LoginDBhelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink(){
        if(MRDB==null||!MRDB.isOpen()){
            MRDB=mHelper.getReadableDatabase();
        }
        return MRDB;
    }

    public SQLiteDatabase openWriteLink(){
        if(MWDB==null||!MWDB.isOpen()){
            MWDB=mHelper.getWritableDatabase();
        }
        return MWDB;
    }

    public void closeLink(){
        if(MRDB!=null&&MRDB.isOpen()){
            MRDB.close();
            MRDB=null;
        }
        if(MWDB!=null&&MWDB.isOpen()){
            MWDB.close();
            MWDB=null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + table_login + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                " phone TEXT NOT NULL," +
                " password TEXT NOT NULL," +
                " email text NOT NULL);";
        db.execSQL(sql);

        String sql_remember = "CREATE TABLE IF NOT EXISTS " + table_remember + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                " phone TEXT NOT NULL," +
                " password Text NOT NULL);";
        db.execSQL(sql_remember);

    }//创建函数

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void update_remember(String phone,String password,boolean is_remember){
        if(is_remember){
            ContentValues values=new ContentValues();
            values.put("phone",phone);
            values.put("password",password);
            MRDB.update(table_remember,values,null,null);
        }else{
            MWDB.delete(table_remember,null,null);
        }
    }

    @SuppressLint("Range")
    public Personinfo query_remember(){
        Cursor cursor = MRDB.query(table_remember, null, null, null,null,null,null);
        Personinfo info=new Personinfo();
        if(cursor.moveToNext()){
            //info.id=cursor.getInt(cursor.getColumnIndex("_id"));
            info.phone=cursor.getString(cursor.getColumnIndex("phone"));
            info.password=cursor.getString(cursor.getColumnIndex("password"));
            //info.email=cursor.getString(cursor.getColumnIndex("email"));
        }
        return info;
    }


    public void insertPerson(Personinfo info){
        try{
            MWDB.beginTransaction();

            ContentValues values=new ContentValues();
            values.put("phone",info.phone);
            values.put("password",info.password);
            values.put("email",info.email);
            MWDB.insert(table_login,null,values);

            MWDB.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MWDB.endTransaction();
        }
    }

//    public void deleteAll(){
//        MWDB.delete(table_login,null,null);
//    }
//
//    public void deletePhonenum(String[] phonenums){
//        MWDB.delete(table_login,"phone = ?", phonenums);
//    }

//    public List<Personinfo> queryForAll(){
//        String sql="select * from "+table_login;
//        List<Personinfo> list=new ArrayList<>();
//        Cursor cursor = MRDB.rawQuery(sql, null);
//        while(cursor.moveToNext()){
//           Personinfo info=new Personinfo();
//            info.id=cursor.getInt(0);
//            info.phone=cursor.getString(1);
//            info.password=cursor.getString(2);
//            info.email=cursor.getString(3);
//            list.add(info);
//        }
//        return list;
//    }

    public boolean IfPhoneRepeat(String phone){//注册时查看手机号是否重复
        //Log.d("liu", MRDB==null?"为0":"不为0");
        Cursor cursor = MRDB.query(table_login, null, "phone=?", new String[]{String.valueOf(phone)},null,null,null);
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public String IfEmailCorrect(String phone,String email){//找回是查看邮件是否正确
        Cursor cursor = MRDB.query(table_login, null, "email=?", new String[]{String.valueOf(email)},null,null,null);
        while(cursor.moveToNext()){
            if(phone.equals(cursor.getString(1))) return cursor.getString(2);
        }
        return null;
    }

    @SuppressLint("Range")
    public boolean IfPasswordCorrect(String phone, String password){//登录时查看密码是否正确

        Cursor cursor = MRDB.query(table_login, null, "phone=?", new String[]{String.valueOf(phone)},null,null,null);
        while(cursor.moveToNext()){
            //Log.d("liu", "查到了");
            if(password.equals(cursor.getString(cursor.getColumnIndex("password")))) return true;
        }
        return false;
    }

    public void changePassword(String phone,String password){
        ContentValues values=new ContentValues();
        values.put("password",password);
        MRDB.update(table_login,values,"phone=?",new String[]{phone});
    }
}
