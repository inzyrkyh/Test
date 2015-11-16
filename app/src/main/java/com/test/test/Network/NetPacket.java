package com.test.test.Network;


import com.test.test.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bai on 2015/11/4.
 */
public class NetPacket {
    private Map<String,String> packets = new HashMap<>();
    private ArrayList<String> mSendPackets = new ArrayList<>();
    private static NetPacket me = null;
    private NetPacket(){}
    public static NetPacket getInstance(){
        if ( me == null )
            me = new NetPacket();
        return me;
    }
    public void Init(){

    }
    public void AddPacket(String info){
        if( info==null || info=="")
            return;
        packets.put(AppConstants.NetGetKey,info);
    }
//    public String GetPacket(String key){
//        return packets.get(key);
//    }
    public String UsePacket(String key){
        String s = packets.get(key);
        packets.remove(key);
        return s;
    }
    public synchronized void AddSendPacket(String packet){
        mSendPackets.add(packet);
    }
    public synchronized String GetSendPacket(){
        String packet = mSendPackets.get(0);
        if (packet!=null)
            mSendPackets.remove(0);
        return packet;
    }
    public int GetSendSize(){
        return mSendPackets.size();
    }
}
