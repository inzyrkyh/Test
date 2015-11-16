package com.test.test.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiaoniao.bai.utils.AppConstants;
import com.xiaoniao.bai.utils.Utils;

/**
 * Created by bai on 2015/11/11.
 */
class DbHelper extends SQLiteOpenHelper {
    private final static String DB_NAME="data.db3";
    private final static String DB1_TB1="contacts";
    private final static String DB1_TB2="IntData";
    private final static String DbTag="DbHelp";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(DbTag,"Database:"+this.getDatabaseName()+" is opened.");
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void Init(){
        if(!IsExistTable(DB1_TB2))
            InitIntDataTable();
    }
    public void CloseDb(){
        try {
            this.close();
        }catch (Exception ex){
            Log.i(DbTag,"CloseDb Error:"+ex.getMessage());
        }
    }
    public boolean IsExistTable(String tbName){
        boolean bRet = false;
        String sql = "select name from sqlite_master where type='table' and name=?";
        Cursor cursor = null;
        try {
            cursor = this.getReadableDatabase().rawQuery(sql, new String[]{"" + tbName});
            if(cursor.moveToNext()){
                //String name = cursor.getString(0);
                bRet = true;
            }
        }catch (Exception ex){
            Log.i(DbTag,"rawQuery Error:"+ex.getMessage());
        }
        if( cursor!=null )
            cursor.close();
        return bRet;
    }
    public void DeleteTable(String tableName){
        String  sql="drop table if exists "+ tableName;
        doSql(sql);
    }
    // DB1_TB2 op
    public void InitIntDataTable(){
        String sql="CREATE table "+ DB1_TB2 + " (ID INTEGER primary key,Value  INTEGER,AlterTime Integer);";
        doSql(sql);
        insertIntRecord(DbConstants.iFirstInstall);
    }
    public void insertIntRecord(int Id){
        String sql = "insert into "+DB1_TB2+"(ID,Value,AlterTime) values("+Id+","+DbConstants.iDefault+","+ Utils.GetCurSecondTime()+");";
        doSql(sql);
    }
    public void DeleteIntRecord(int Id){
        String sql = "delete from "+DB1_TB2+" where ID="+Id;
        doSql(sql);
    }
    public int GetIntData(int Id){
        int iRet = AppConstants.iRetError;
        String sql = "select * from "+DB1_TB2+" where ID=?";
        Cursor cursor = null;
        try {
            cursor = this.getReadableDatabase().rawQuery(sql, new String[]{"" + Id});
            if (cursor.moveToNext()) {
                iRet = cursor.getInt(1);
            }
        }catch (Exception ex){
            Log.i(DbTag,"rawQuery Error:"+ex.getMessage());
        }
        if( cursor!=null )
            cursor.close();
        return iRet;
    }
    public Cursor GetIntAll(){
        Cursor cursor = null;
        String sql = "select * from "+DB1_TB2;
        try {
            cursor = this.getReadableDatabase().rawQuery(sql, null);
            if ( cursor.getCount() > 0 ) {
                return cursor;
            }
        }catch (Exception ex){
            Log.i(DbTag,"rawQuery Error:"+ex.getMessage());
        }
        return cursor;
    }
    public int UpdateIntData(int Id,int value){
        String sql = "update ["+DB1_TB2+"] set Value = "+value+",AlterTime = "+Utils.GetCurSecondTime()+" where ID="+Id;
        if( !doSql(sql) )
            return AppConstants.iRetError;
        return GetIntData(Id);
    }
    private boolean doSql(String sql){
        try {
            this.getWritableDatabase().execSQL(sql);
        }catch (SQLiteException ex){
            Log.i(DbTag,"execSQL Error:"+ex.getMessage());
            return false;
        }
        return true;
    }
}
