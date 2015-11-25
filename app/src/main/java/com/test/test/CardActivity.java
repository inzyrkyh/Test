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
    CardStackAdapter cardStackAdapter;
    private static CardActivity me;
    private List<Fragment> mViewPagerFragments;
    private int enterFromListPosition;
    private ShakeListener mShake;
    private SensorManager mSensorManager;
    private int mPage = -1;

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

        final FlippableStackView stack = (FlippableStackView) findViewById(R.id.stackview_card);
        stack.initStack(2, StackPageTransformer.Orientation.HORIZONTAL);
        createViewPagerFragments();
        cardStackAdapter = new CardStackAdapter(getSupportFragmentManager(), mViewPagerFragments);
        stack.setAdapter(cardStackAdapter);
        stack.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        stack,
                        null,
                        new SwipeDismissTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(Object token) {
                                return true;
                            }

                            @Override
                            public void onDismiss(View view, Object token) {
                                NetPacket.getInstance().AddSendPacket(BaiMsg.getInstance().CreateMsgBroadcast(GetCurCard().getName()));
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
        stack.setOnTouchListener(touchListener);
    }

    public Card GetCurCard(){
        Card card = ContactsMgr.getInstance().GetContactItem(enterFromListPosition);
        if( mPage > 0 )
            card = card.GetOtherOne(mPage-1);
        return card;
    }
    private void createViewPagerFragments() {
        mViewPagerFragments = new ArrayList<>();

//        int startColor = getResources().getColor(R.color.emerald);
//        int startR = Color.red(startColor);
//        int startG = Color.green(startColor);
//        int startB = Color.blue(startColor);
//
//        int endColor = getResources().getColor(R.color.wisteria);
//        int endR = Color.red(endColor);
//        int endG = Color.green(endColor);
//        int endB = Color.blue(endColor);
//
//        ValueInterpolator interpolatorR = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endR, startR);
//        ValueInterpolator interpolatorG = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endG, startG);
//        ValueInterpolator interpolatorB = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endB, startB);

        //demo 如果是自己的名片,展示多张名片 如果是别人的 展示一张
        Card mainCard = ContactsMgr.getInstance().GetContactItem(enterFromListPosition);
        if ( mainCard!=null ) {
            mPage = 0;
            mViewPagerFragments.add(CardStackFragment.newInstance("1", mainCard ) );
            String name = this.getIntent().getStringExtra(AppConstants.TestKey);
            if( name != null )
                mainCard.setName(name);
            for (int i = 0; i < mainCard.OtherSize(); ++i) {
                mPage +=1;
                mViewPagerFragments.add(CardStackFragment.newInstance("1", mainCard.GetOtherOne(i) ));
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MainActivity.getInstance().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
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
    protected void onResume()
    {
        super.onResume();
        if( mSensorManager==null || mShake==null )
            return;
        mSensorManager.registerListener(mShake,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
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
