package com.xiaoniao.bai.mingpianjia;

import android.app.Activity;
import android.content.Intent;

import com.xiaoniao.bai.base.DbBase;
import com.xiaoniao.bai.net.NetThread;
import com.xiaoniao.bai.utils.AppConstants;
import com.xiaoniao.bai.utils.JsonTools;
import com.xiaoniao.bai.utils.Utils;

/**
 * Created by bai on 2015/11/4.
 */
public class AppMain {
    public static String MacAddr;
    private static AppMain me = null;
    private Activity mActivity;
    private AppMain(){}
    public static AppMain getInstance(){
        if ( me == null )
            me = new AppMain();
        return me;
    }
    private void InitComm(){
        MacAddr = Utils.GetMacAddress(mActivity).replaceAll(":","-");
    }
    public void Init(Activity activity){
        mActivity = activity;
        InitComm();
        DbBase.getInstance().Init(mActivity);
        NetThread.getInstance().Init(mActivity);
    }
    public void StartTestActivity(String info){
        Intent intent = new Intent(mActivity, testActivity.class);
        intent.putExtra(AppConstants.TestKey,info);
        mActivity.startActivity(intent);
    }
}
