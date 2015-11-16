package com.test.test.Database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.xiaoniao.bai.mingpianjia.Contact;
import com.xiaoniao.bai.mingpianjia.ContactsMgr;
import com.xiaoniao.bai.utils.AppConstants;

import java.util.HashMap;

/**
 * Created by bai on 2015/11/11.
 */
public class DbBase{
    private class TInt{
        int Id;
        int Value;
        int AlterTime;
    }
    private HashMap<Integer,TInt> intDatas = new HashMap<>();
    private DbHelper mHelper;
    private static DbBase me = null;
    private Context mContext;
    private DbBase(){}
    public static DbBase getInstance(){
        if ( me == null )
            me = new DbBase();
        return me;
    }
    public void Init(Context context){
        mContext = context;
        mHelper = new DbHelper(mContext);
        mHelper.Init();

        InsertRecord();
        Load();
        mHelper.CloseDb();
        Test();
    }
    private void Test(){

    }
    private void InsertRecord(){
        //mHelper.insertIntRecord(DbConstants.iFirstInstall);
    }
    private void Load(){
        Cursor cursor = mHelper.GetIntAll();
        while (cursor!=null && cursor.moveToNext()){
            TInt tInt = new TInt();
            tInt.Id = cursor.getInt(0);
            tInt.Value = cursor.getInt(1);
            tInt.AlterTime = cursor.getInt(2);
            intDatas.put(tInt.Id,tInt);
        }
        cursor.close();
        if( GetInt(DbConstants.iFirstInstall)== DbConstants.iDefault ){
            ReadPhonebook();
            //SetInt(DbConstants.iFirstInstall,100);
        }else{
            LoadContacts();
        }
    }
    private void LoadContacts(){

    }
    private void SaveContact(Contact contact){

    }
    private void ReadPhonebook(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY}, null, null, "sort_key");
        if ( cursor.getCount() > 0 )
            while (cursor.moveToNext()){
                Contact contact = new Contact();
                contact.setName(cursor.getString(0));
                contact.setPhoneNum(cursor.getString(1));
                contact.setSortKey(contact.getSortKey(cursor.getString(2)));
                ContactsMgr.getInstance().AddContact(contact);
                SaveContact(contact);
            }
        cursor.close();
    }
    public int GetInt(int Id){
        TInt tInt = intDatas.get(Id);
        if(Id <= DbConstants.iSeg || Id >= DbConstants.cSeg || tInt==null)
            return AppConstants.iRetError;
        return tInt.Value;
    }
    public int AddInt(int Id,int value){
        TInt tInt = intDatas.get(Id);
        if(Id <= DbConstants.iSeg || Id >= DbConstants.cSeg || tInt==null)
            return AppConstants.iRetError;
        return SetInt(Id,value+tInt.Value);
    }
    public int SetInt(int Id,int value){
        TInt tInt = intDatas.get(Id);
        if(Id <= DbConstants.iSeg || Id >= DbConstants.cSeg || tInt==null)
            return AppConstants.iRetError;
        if( value != tInt.Value )
        {
            tInt.Value = value;
            SaveInt(Id);
        }
        return value;
    }
    private int SaveInt(int Id){
        return mHelper.UpdateIntData(Id,intDatas.get(Id).Value);
    }
}
