package com.test.test.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.test.test.Network.NetPacket;

/**
 * Created by bai on 2015/11/4.
 */
public class AppHandler extends Handler  {
    private static AppHandler me = null;
    private AppHandler(){
        super(Looper.getMainLooper());
    }
    public static AppHandler getInstance(){
        if ( me == null )
            me = new AppHandler();
        return me;
    }
    public void Init(){

    }

    @Override
    public void handleMessage(Message msg) {
        Bundle b = msg.getData();
        String color = b.getString(AppConstants.NetGetKey);
        NetPacket.getInstance().AddPacket(color);
    }
}
