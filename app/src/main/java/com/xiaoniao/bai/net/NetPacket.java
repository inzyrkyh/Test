package com.xiaoniao.bai.net;

import com.test.test.CardActivity;
import com.test.test.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bai on 2015/11/4.
 */
public class NetPacket {
    class InfoReturnT{
        int msgId;
        int feedback;
        String msgBody;
    }
    private InfoReturnT mInfo = null;
    private boolean mbRecvBroadcast = false;
    private Map<String,String> packets = new HashMap<>();
    private ArrayList<String> mSendPackets = new ArrayList<>();// mem leak
    private static NetPacket me = null;
    private static final int mRepeatCount = 6;
    private static int mRepeatSendMsg4 = 0;
    private NetPacket(){}
    public static NetPacket getInstance(){
        if ( me == null )
            me = new NetPacket();
        return me;
    }
    public void Init(){

    }
    public void startRcvBroadcast(){
        mbRecvBroadcast = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mbRecvBroadcast){
                    AddSendPacket(BaiMsg.getInstance().CreateMsgBroadcastRcv());
                    try {
                        Thread.sleep(4200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void stopRcvBroadcast(){
        mbRecvBroadcast = false;
    }
    private void parse(String info){
        if( mInfo == null )
            mInfo = new InfoReturnT();
        mInfo.msgId = -1;
        mInfo.feedback = -1;
        mInfo.msgBody = "bai";
        String[] strs=info.split(";");
        for ( int i=0; i < strs.length; i++ ){
            String[] key_value = strs[i].split("=");
            if( key_value[0].equals(MsgConstants.MsgId))
                mInfo.msgId = Integer.valueOf(key_value[1]);
            else if( key_value[0].equals(MsgConstants.FeedBack))
                mInfo.feedback = Integer.valueOf(key_value[1]);
            else if( key_value[0].equals(MsgConstants.MsgBody))
                mInfo.msgBody = key_value[1];
        }
    }
    public void AddPacket(String info){
        if( info==null || info=="")
            return;
        //packets.put(AppConstants.NetGetKey,info);
        parse(info);
        if( mInfo.feedback == MsgConstants.MsgYao ){
            mRepeatSendMsg4 = 0;
            AddSendPacket(BaiMsg.getInstance().CreateMsgYaoMatch());
        }
        else if( mInfo.feedback == MsgConstants.MsgYaoMatch ){
            if( mInfo.msgId == MsgConstants.MsgSendError ) {
                if (mRepeatSendMsg4 < mRepeatCount) {
                    AddSendPacket(BaiMsg.getInstance().CreateMsgYaoMatch());
                    mRepeatSendMsg4 += 1;
                }
            }
            else if( mInfo.msgId == MsgConstants.MsgSendOk )
                CardActivity.GetInstance().updateUI(mInfo.msgBody);
        }
        else if( mInfo.feedback == MsgConstants.MsgBroadcastRcv ){
            if( mInfo.msgId == MsgConstants.MsgSendOk ) {
                MainActivity.getInstance().showCard(mInfo.msgBody);
            }
        }
    }
    public synchronized void AddSendPacket(String packet){
        mSendPackets.add(packet);
    }
    public synchronized String GetSendPacket(){
        if( mSendPackets.size() <= 0 )
            return null;
        String packet = mSendPackets.get(0);
        if (packet!=null)
            mSendPackets.remove(0);
        return packet;
    }
    public int GetSendSize(){
        return mSendPackets.size();
    }
/*    public String UsePacket(String key){
        String s = packets.get(key);
        if( s!= null )
            packets.remove(key);
        return s;
    }*/
/*    public void process(String info){
        if( info==null || info=="")
            return;
        try {
            JSONObject jsonObject = new JSONObject(info);
            if( jsonObject == null ) return;
            JSONObject JInfo = jsonObject.getJSONObject("JInfo");
            if( JInfo == null ) return;
            String infoCode = JInfo.getString("infoCode");
            if( infoCode.equals("2")){
                JSONObject Data = jsonObject.getJSONObject("Data");
                String id = Data.getString("id");
                if( id!=null ){
                    NetPacket.getInstance().AddSendPacket(JsonTools.getInstance().GetMsg4());
                }
            }
            else if( infoCode.equals("4")){
                JSONObject Data = jsonObject.getJSONObject("Data");
                String var1 = Data.getString("var1");
                String var2 = Data.getString("var2");
                if( var2.equals("1")){ // msg4
                    String otherInfo = var1;
                    CardActivity.GetInstance().updateUI(otherInfo);
                }
                else if( var2.equals("0") ){
                    mRepeatSendMsg4 += 1;
                    if( mRepeatSendMsg4 <= mRepeatCount )
                        NetPacket.getInstance().AddSendPacket(JsonTools.getInstance().GetMsg4());
                }
            }
            else if( infoCode.equals("7")){
                JSONObject Data = jsonObject.getJSONObject("Data");
                if( Data == null ) return;
                String var1 = Data.getString("var1");
                if( var1.equals("1") ){
                    String string = ContactsMgr.getInstance().GetCurUserInfo();
                    JsonTools.getInstance().SetDataValue(JsonTools.var1,string );
                    NetPacket.getInstance().AddSendPacket(JsonTools.getInstance().GetMsg2(JsonTools.mYao));
                }
            }
//            else if( infoCode.equals("6")){
//
//            }
            else{
                Log.i("MyLog", "Error:infoCode=="+infoCode);
                return;
            }
        } catch (JSONException e) {
            Log.i("MyLog", "process error:" + e.getMessage());
        }
    }*/
}
