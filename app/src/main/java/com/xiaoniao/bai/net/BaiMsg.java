package com.xiaoniao.bai.net;

import android.content.Context;

import com.xiaoniao.bai.mingpianjia.AppMain;

import static com.xiaoniao.bai.net.MsgConstants.*;

/**
 * Created by bai on 2015/11/18.
 */
public class BaiMsg {
    private static BaiMsg me = null;
    private BaiMsg(){}
    public static BaiMsg getInstance(){
        if ( me == null )
            me = new BaiMsg();
        return me;
    }
    static class Msg{
        int msgId;
        String MacAddr;
        String msgBody;
    }
    private Msg msg;
    public String CreateMsgYao(String info){
        return MsgId+"="+ MsgYao+"&"+
                MacAddr+"="+ AppMain.MacAddr +"&"+
                MsgBody+"="+info;
    }
    public String CreateMsgYaoMatch(){
        return MsgId+"="+ MsgYaoMatch+"&"+
                MacAddr+"="+ AppMain.MacAddr+"&"+
                MsgBody+"=NULL";
    }
}
