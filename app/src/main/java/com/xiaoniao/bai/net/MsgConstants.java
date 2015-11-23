package com.xiaoniao.bai.net;

import android.content.Context;

import com.xiaoniao.bai.utils.Utils;

/**
 * Created by bai on 2015/11/18.
 */
public class MsgConstants {
    public final static int MsgSeg = 100;
    public final static int MsgSendOk = MsgSeg + 0;
    public final static int MsgSendError = MsgSeg + 1;
    public final static int MsgYao = MsgSeg + 10;
    public final static int MsgYaoMatch = MsgSeg + 11;

    public final static String MsgId = "MsgId";
    public final static String MsgBody = "MsgBody";
    public final static String MacAddr = "MacAddr";
    public final static String FeedBack = "FeedBack";
    public final static String MsgNull = "MsgNull";
}
