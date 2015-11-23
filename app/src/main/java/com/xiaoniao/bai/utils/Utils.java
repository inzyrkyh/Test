package com.xiaoniao.bai.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.Calendar;

/**
 * Created by bai on 2015/11/2.
 */
public class Utils {
    public static boolean isNetStateOK(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if( connMgr.getActiveNetworkInfo()!=null ){
            if( connMgr.getActiveNetworkInfo().isAvailable()==true )
                return true;
        }
        return false;
    }
    public static String GetMacAddress(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if( wifi == null )
            return null;
        WifiInfo info = wifi.getConnectionInfo();
        if( info == null )
            return null;
        return info.getMacAddress();
    }
    public static int RandomInt(int Min,int Max){
        if( Min < 0 || Max < Min )
            return AppConstants.iRetError;
        return new java.util.Random().nextInt(Max-Min+1)+Min;
    }
    public static long GetCurSecondTime(){
        return Calendar.getInstance().getTimeInMillis()/1000;
    }
    public static long GetCurTimeInMillis(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
