package com.test.test.Network;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.test.test.utils.AppConstants;
import com.test.test.utils.AppHandler;
import com.test.test.utils.Utils;

/**
 * Created by bai on 2015/11/4.
 */
public class NetThread extends Thread{
    private boolean bRun = true;
    private boolean bStartup = false;
    private static NetThread me = null;
    private NetThread(){

    }
    public static NetThread getInstance(){
        if ( me == null )
            me = new NetThread();
        return me;
    }
    public void Init(Activity activity){
        if( Utils.isNetStateOK(activity) == 1 )
           me.start();
    }
    public void NetStop(){
        bRun = false;
        HttpClientBase.getInstance().disconnect();
    }

    @Override
    public synchronized void run() {
        while (bRun){
            if (NetPacket.getInstance().GetSendSize() > 0 ) {
                if ( !bStartup) {
                    connectServer();
                } else {
                    String info = HttpClientBase.getInstance().read();
                    if (info != null && info != "") {
                        Message msg = AppHandler.getInstance().obtainMessage();
                        Bundle b = new Bundle();
                        b.putString(AppConstants.NetGetKey, info);
                        msg.setData(b);
                        AppHandler.getInstance().sendMessage(msg);
                    }
                    HttpClientBase.getInstance().disconnect();
                    bStartup = false;
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void connectServer(){
        if( HttpClientBase.getInstance().startup(AppConstants.AppUrl1) ||
                HttpClientBase.getInstance().startup(AppConstants.AppUrl2) )
           bStartup = true;
    }
}
