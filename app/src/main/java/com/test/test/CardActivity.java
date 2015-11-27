package com.test.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bartoszlipinski.flippablestackview.FlippableStackView;
import com.bartoszlipinski.flippablestackview.StackPageTransformer;
import com.test.test.Listener.SwipeDismissTouchListener;
import com.test.test.Model.Card;
import com.test.test.Model.CardStackAdapter;
import com.test.test.Model.ContactsMgr;
import com.xiaoniao.bai.mingpianjia.ShakeListener;
import com.xiaoniao.bai.net.BaiMsg;
import com.xiaoniao.bai.net.NetPacket;
import com.xiaoniao.bai.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    private CardStackAdapter cardStackAdapter;
    private static CardActivity me;
    private List<Fragment> mViewPagerFragments;
    private int enterFromListPosition;
    private ShakeListener mShake;
    private SensorManager mSensorManager;
    private int mPage = -1;
    private FlippableStackView mStackView;

    public static void startActivity(Context lastContext, int position) {
        Intent intent = new Intent(lastContext, CardActivity.class);
        intent.putExtra("cardPosition", position);
        lastContext.startActivity(intent);
        MainActivity.getInstance().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enterFromListPosition = getIntent().getIntExtra("cardPosition", 0);
        setContentView(R.layout.activity_card);
        me = this;
        mShake = new ShakeListener(this);
        mSensorManager = (SensorManager)getSystemService(Service.SENSOR_SERVICE);

        mStackView = (FlippableStackView) findViewById(R.id.stackview_card);
        mStackView.initStack(2, StackPageTransformer.Orientation.HORIZONTAL);
        createViewPagerFragments();
        cardStackAdapter = new CardStackAdapter(getSupportFragmentManager(), mViewPagerFragments);
        mStackView.setAdapter(cardStackAdapter);
        mStackView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mPage = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        SwipeDismissTouchListener touchListener =
                new SwipeDismissTouchListener(
                        mStackView,
                        null,
                        new SwipeDismissTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(Object token) {
                                return true;
                            }

                            @Override
                            public void onDismiss(View view, Object token) {
                                NetPacket.getInstance().AddSendPacket(BaiMsg.getInstance().CreateMsgBroadcast(GetInfo()));
                            }

//                            @Override
//                            public boolean canDismiss(int position) {
//                                return true;
//                            }
//
//                            @Override
//                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
//                                for (int position : reverseSortedPositions) {
//                                    cardStackAdapter.remove(cardStackAdapter.getItem(position));
//                                }
//                                cardStackAdapter.notifyDataSetChanged();
//                            }
                        });
        touchListener.setOrientation(SwipeDismissTouchListener.SWIPE_VERTICAL);
        mStackView.setOnTouchListener(touchListener);
    }

    private Card GetCurCard(){
        Card card = ContactsMgr.getInstance().GetContactItem(enterFromListPosition);
        if( mPage > 0 )
            card = card.GetOtherOne(mPage-1);
        return card;
    }
    public String GetInfo(){
        return GetCurCard().getName()+"#"+mPage;
    }
    private void createViewPagerFragments() {
        mViewPagerFragments = new ArrayList<>();
        Card mainCard = null;
        String name = this.getIntent().getStringExtra(AppConstants.TestKey);
        String lpage = "";
        if( name != null ) {
            mainCard = new Card(name.split("#")[0], "");
            lpage  = name.split("#")[1];
        }else
            mainCard = ContactsMgr.getInstance().GetContactItem(enterFromListPosition);
        if ( mainCard!=null ) {
            mPage = 0;
            if( name == null ) lpage = ""+mPage;
            mViewPagerFragments.add(CardStackFragment.newInstance(lpage, mainCard ) );
            for (int i = 0; i < mainCard.OtherSize(); ++i) {
                mPage +=1;
                if( name == null ) lpage = ""+mPage;
                mViewPagerFragments.add(CardStackFragment.newInstance(lpage, mainCard.GetOtherOne(i) ));
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.getInstance().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
    public void updateUI(String s){
        if( s!=null && s!="" ) {
            CardStackFragment fragment = (CardStackFragment)mViewPagerFragments.get(mPage);
            fragment.updateUI(s);
            UninstallListener();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if( mSensorManager==null || mShake==null )
            return;
        mSensorManager.registerListener(mShake,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        UninstallListener();
    }
    public static CardActivity GetInstance(){
        return me;
    }
    private void UninstallListener(){
        if( mSensorManager==null || mShake==null )
            return;
        mSensorManager.unregisterListener(mShake);
    }
}
