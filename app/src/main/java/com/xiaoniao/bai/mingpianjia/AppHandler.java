package com.xiaoniao.bai.mingpianjia;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xiaoniao.bai.net.NetPacket;
import com.xiaoniao.bai.net.NetThread;
import com.xiaoniao.bai.utils.AppConstants;

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
