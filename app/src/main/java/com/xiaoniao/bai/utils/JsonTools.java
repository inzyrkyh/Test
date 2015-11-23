package com.xiaoniao.bai.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bai on 2015/11/16.
 */
public class JsonTools {
    private static String jsonBase = "{" +
                "\"DataRoot\": [" +
                    "{" +
                            "\"User\": {" +
                                    "\"userid\": \"NULL\"," +
                                    "\"username\": \"NULL\"," +
                                    "\"passwd\": \"NULL\"," +
                                    "\"MacAddr\": \"00-00-00-00-00-00\"" +
                            "}," +
                            "\"Type\": {" +
                                 "\"typeid\": \"1\"" +
                            "}," +
                            "\"Data\": {" +
                                "\"var1\": \"0\"," +
                                "\"var2\": \"var2\"," +
                                "\"var3\": \"var3\"," +
                                "\"var4\": \"var4\"" +
                            "}" +
                    "}" +
                "]" +
            "}";
    private final String TAG = "JsonTools";
    private JSONObject mJsonObject = null;
    public final static String userid = "userid";
    public final static String username = "username";
    public final static String passwd = "passwd";
    public final static String MacAddr = "MacAddr";
    public final static String typeid = "typeid";
    public final static String User = "User";
    public final static String Data = "Data";
    public final static String var1 = "var1";
    public final static String var2 = "var2";
    public final static String var3 = "var3";
    public final static String var4 = "var4";
    public final static String Type = "Type";
    public final static String DataRoot = "DataRoot";
    public final static String mYao = "1";
    public final static String mCuo = "0";
    public final static String NULL = "NULL";
    private static JsonTools me = null;
    private JsonTools(){}
    public static JsonTools getInstance(){
        if ( me == null )
            me = new JsonTools();
        if( me.mJsonObject == null ){
            me.createBaseStruct();
        }
        return me;
    }
    public void Reset(){
        SetUserInfo(username,"NULL");
        SetUserInfo(passwd,"NULL");
        SetUserInfo(userid,"NULL");
        SetDataValue(var1, "NULL");
        SetDataValue(var2,"NULL");
        SetDataValue(var3,"NULL");
        SetDataValue(var4,"NULL");
    }
    public void createBaseStruct(){
        try {
            mJsonObject = new JSONObject(jsonBase);
        } catch (JSONException ex) {
            Log.i(TAG, "createBaseStruct Error:" + ex.getMessage());
        }
    }
    public String GetUserInfo(String key){
        JSONArray jsonArray = null;
        try {
            jsonArray = mJsonObject.getJSONArray(DataRoot);
            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject(User);
            return jsonObject.getString(key);
        }catch (JSONException ex) {
            Log.i(TAG, "GetUserInfo Error:" + ex.getMessage());
        }
        return null;
    }
    public void SetUserInfo(String key,String value){
        try {
            JSONArray jsonArray = mJsonObject.getJSONArray(DataRoot);
            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject(User);
            jsonObject.put(key,value);
        }catch (JSONException e) {
            Log.i(TAG, "SetUserInfo Error:" + e.getMessage());
        }
    }
    public String GetTypeId(){
        JSONArray jsonArray = null;
        try {
            jsonArray = mJsonObject.getJSONArray(DataRoot);
            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject(Type);
            return jsonObject.getString(typeid);
        }catch (JSONException ex) {
            Log.i(TAG, "GetTypeId Error:" + ex.getMessage());
        }
        return null;
    }
    public void SetTypeId(String tid){
        JSONArray jsonArray = null;
        try {
            jsonArray = mJsonObject.getJSONArray(DataRoot);
            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject(Type);
            jsonObject.put(typeid,tid);
        }catch (JSONException ex) {
            Log.i(TAG, "SetTypeId Error:" + ex.getMessage());
        }
    }
    public void SetDataValue(String key,String value){
        JSONArray jsonArray = null;
        try {
            jsonArray = mJsonObject.getJSONArray(DataRoot);
            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject(Data);
            jsonObject.put(key,value);
        }catch (JSONException ex) {
            Log.i(TAG, "SetDataValue Error:" + ex.getMessage());
        }
    }
    public String GetDataValue(String key){
        JSONArray jsonArray = null;
        try {
            jsonArray = mJsonObject.getJSONArray(DataRoot);
            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject(Data);
            return jsonObject.getString(key);
        }catch (JSONException ex) {
            Log.i(TAG, "GetDataValue Error:" + ex.getMessage());
        }
        return null;
    }
    public void Init(Context context){
        SetUserInfo(MacAddr, Utils.GetMacAddress(context).replaceAll(":","-"));
        Test();
    }
    public void Test(){
//        String msg2 = GetMsg2(mYao);
//        msg2 = GetDataValue(var2);
    }
    public String GetMsg6(){
        SetTypeId("6");
        SetUserInfo(userid, "6");
        return mJsonObject.toString();
    }
    public String GetMsg4(){
        Reset();
        SetTypeId("4");
        SetUserInfo(userid, "4");
        return mJsonObject.toString();
    }
    public String GetMsg7(){
        SetTypeId("7");
        SetUserInfo(userid, "7");
        return mJsonObject.toString();
    }
    public String GetMsg2(String type){
        if( !type.equals(mYao) && !type.equals(mCuo) ) return null;
        SetTypeId("2");
        SetUserInfo(userid,"2");
        SetDataValue(var2,"0");
        SetDataValue(var3,type);
        return mJsonObject.toString();
    }
}
