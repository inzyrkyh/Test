package com.test.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private CardStackFragment mStackFragment;
    private Card mCard;

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
//        stackView = (StackView) findViewById(R.id.stackview_card);

//        ContactsHelper.fetchAllContacts(this);
//        List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
//        此处应调用自己的名片数据
//        for(int i=0;i < 4;i++)
//        {
////            Map<String,Object> listItem=new HashMap<String,Object>();
////            listItem.put("image",MainActivity.dataList.get(i));
////            listItems.add(listItem);
//            dataList.add(MainActivity.dataList.get(i));
//        }
//        cardStackAdapter = new CardStackAdapter(this, R.layout.fragment_card, dataList);
////        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.id.stackview_card, new String[]{"image"},new int[]{R.id.stackview_card} );
//        stackView.setAdapter(cardStackAdapter);

        final FlippableStackView stack = (FlippableStackView) findViewById(R.id.stackview_card);
        stack.initStack(2, StackPageTransformer.Orientation.HORIZONTAL);
        createViewPagerFragments();
        cardStackAdapter = new CardStackAdapter(getSupportFragmentManager(), mViewPagerFragments);
        stack.setAdapter(cardStackAdapter);

//        RelativeLayout layout = (RelativeLayout) stack.findViewById(R.id.card_layout);

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
//                                stack.removeView(stack);
                                //used to do send action, do some http request, etc.
                                NetPacket.getInstance().AddSendPacket(BaiMsg.getInstance().CreateMsgBroadcast(GetCard().getName()));
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
        if (ContactsMgr.getInstance().GetContacts().get(enterFromListPosition).getMyCard()) {
            for (int i = 0; i < Math.min(ContactsMgr.getInstance().GetSize(), 4); ++i) {
                mViewPagerFragments.add(CardStackFragment.newInstance("1", ContactsMgr.getInstance().GetContactItem(0)));
            }
        }
        else {
            mCard = ContactsMgr.getInstance().GetContactItem(enterFromListPosition);
            String name = this.getIntent().getStringExtra(AppConstants.TestKey);
            if( name != null )
                mCard.setName(name);
            mStackFragment = CardStackFragment.newInstance("1", mCard);
                mViewPagerFragments.add(mStackFragment);
        }
    }
    public Card GetCard(){
        return mCard;
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
        if( s!=null && s!="" && mStackFragment!=null ) {
            mStackFragment.updateUI(s);
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
