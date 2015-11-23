package com.xiaoniao.bai.mingpianjia;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.test.test.Model.ContactsMgr;
import com.test.test.R;
import com.xiaoniao.bai.utils.AppConstants;

/**
 * Created by bai on 2015/11/4.
 */
public class testActivity extends Activity{
    private TextView mtextView;
//    private ShakeListener mShake;
//    private SensorManager mSensorManager;
    private static testActivity me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);

        mtextView = (TextView)this.findViewById(R.id.tvTest);
        mtextView.setText(this.getIntent().getStringExtra(AppConstants.TestKey));

        me = this;
        updateUI(ContactsMgr.getInstance().GetCurUserInfo());
//        mShake = new ShakeListener(this);
//        mSensorManager = (SensorManager)getSystemService(Service.SENSOR_SERVICE);
    }
    public void updateUI(String s){
        if( s!=null && s!="" && mtextView!=null )
            mtextView.setText(s);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
//        mSensorManager.registerListener(mShake,
//                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        //mSensorManager.unregisterListener(mShake);
        super.onPause();
    }
    public static testActivity GetInstance(){
        return me;
    }
}
