package com.xiaoniao.bai.mingpianjia;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Vibrator;

import com.test.test.CardActivity;
import com.test.test.Model.ContactsMgr;
import com.xiaoniao.bai.net.BaiMsg;
import com.xiaoniao.bai.net.NetPacket;
import com.xiaoniao.bai.utils.Utils;

public class ShakeListener implements SensorEventListener {
    private Activity mContext;
    private final static int mShakevalue = 15;
    private final static long mShakeTimeInterval = 3000;
    private long mShakeLastTime;
    Vibrator vibrator = null; 
  
    public ShakeListener(Activity context) {
        super();  
        this.mContext = context;
        vibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        mShakeLastTime = Utils.GetCurTimeInMillis();
    }
    private boolean IsCanShakeAgain(){
        if( !Utils.isNetStateOK(mContext) )
            return false;
        return Utils.GetCurTimeInMillis() - mShakeLastTime > mShakeTimeInterval?true:false;
    }
    @Override  
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        if (sensorType == Sensor.TYPE_ACCELEROMETER && IsCanShakeAgain() ) {
            float[] values = event.values;
            if ((Math.abs(values[0]) > mShakevalue || Math.abs(values[1]) > mShakevalue || Math.abs(values[2]) > mShakevalue)) {
            	vibrator.vibrate(200);
                String str = CardActivity.GetInstance().GetCurCard().getName();
                NetPacket.getInstance().AddSendPacket(BaiMsg.getInstance().CreateMsgYao(str));
                mShakeLastTime = Utils.GetCurTimeInMillis();
                //NetPacket.getInstance().AddSendPacket("key="+JsonTools.getInstance().GetMsg2(JsonTools.mYao));
                //NetPacket.getInstance().AddSendPacket(JsonTools.getInstance().GetMsg6());
                //NetPacket.getInstance().AddSendPacket(JsonTools.getInstance().GetMsg7());
            }  
        }  
    }
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}