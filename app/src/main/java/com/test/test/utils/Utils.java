package com.test.test.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Calendar;

/**
 * Created by bai on 2015/11/2.
 */
public class Utils {
    public static int isNetStateOK(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if( connMgr.getActiveNetworkInfo()!=null ){
            if( connMgr.getActiveNetworkInfo().isAvailable()==true )
                return 1;
        }
        return 0;
    }
    public static long GetCurSecondTime(){
        return Calendar.getInstance().getTimeInMillis()/1000;
    }
    public static long GetCurTimeInMillis(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
