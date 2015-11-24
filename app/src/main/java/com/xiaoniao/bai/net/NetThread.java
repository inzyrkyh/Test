package com.xiaoniao.bai.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.xiaoniao.bai.mingpianjia.AppHandler;
import com.xiaoniao.bai.utils.AppConstants;
import com.xiaoniao.bai.utils.Utils;

/**
 * Created by bai on 2015/11/4.
 */
public class NetThread extends Thread{
    private final static int mRepeatInterval = 200;
    private boolean bRun = true;
    private boolean bStartup = false;
    private Activity mActivity;
    private static NetThread me = null;
    private NetThread(){

    }
    public static NetThread getInstance(){
        if ( me == null )
            me = new NetThread();
        return me;
    }
    private boolean isNetOk(){
        return Utils.isNetStateOK(mActivity);
    }
    public void Init(Activity activity){
        mActivity = activity;
        if( isNetOk() )
           me.start();
    }
//    public void NetStop(){
//        bRun = false;
//        HttpClientBase.getInstance().disconnect();
//    }

    @Override
    public synchronized void run() {
        while (bRun){
            if ( isNetOk() && NetPacket.getInstance().GetSendSize() > 0 ) {
                connectServer();
                if( bStartup ){
                    String info = HttpClientBase.getInstance().read();
                    if ( info != null && !info.equals("") ) {
                        Message msg = AppHandler.getInstance().obtainMessage();
                        Bundle b = new Bundle();
                        b.putString(AppConstants.NetGetKey, info);
                        msg.setData(b);
                        AppHandler.getInstance().sendMessage(msg);
                    }
                    HttpClientBase.getInstance().disconnect();
                    bStartup = false;
                }
                else{
                    NetPacket.getInstance().GetSendPacket();
                }
            }
            try {
                Thread.sleep( mRepeatInterval );
            } catch (InterruptedException e) {
                Log.i("MyLog", "Thread.sleep..InterruptedException error:" + e.getMessage());
            }
        }
    }
    private void connectServer(){
        if( HttpClientBase.getInstance().startup(AppConstants.AppUrl1) ||
                HttpClientBase.getInstance().startup(AppConstants.AppUrl1) )
           bStartup = true;
    }
}
