package com.xiaoniao.bai.net;

import com.xiaoniao.bai.mingpianjia.AppMain;

import static com.xiaoniao.bai.net.MsgConstants.*;

/**
 * Created by bai on 2015/11/18.
 * 1. 发送msg的格式 json对象还是class对象？
 * 2. 消息返回的格式？
 * 3. 如何附带回调接口？
 * 4. 是否要指定发包的大小？
 * 5. 没有发的msg如何处理？
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
        int callbackId;// 回调 -1不回调
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
    public String CreateMsgBroadcast(String info){
        return MsgId+"="+ MsgBroadcast+"&"+
                MacAddr+"="+ AppMain.MacAddr +"&"+
                MsgBody+"="+info;
    }
    public String CreateMsgBroadcastRcv(){
        return MsgId+"="+ MsgBroadcastRcv+"&"+
                MacAddr+"="+ AppMain.MacAddr +"&"+
                MsgBody+"=NULL";
    }
}
