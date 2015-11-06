package com.test.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.StackView;

import com.bartoszlipinski.flippablestackview.FlippableStackView;
import com.test.test.Model.Card;
import com.test.test.Model.CardStackAdapter;
import com.test.test.Model.ContactsHelper;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {

//    StackView stackView;
    CardStackAdapter cardStackAdapter;
    List<Card> dataList = new ArrayList<Card>();
    private List<Fragment> mViewPagerFragments;

    public static void startActivity(Context lastContext) {
        Intent intent = new Intent(lastContext, CardActivity.class);
        lastContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
//        stackView = (StackView) findViewById(R.id.stackview_card);

        ContactsHelper.fetchAllContacts(this);
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

        FlippableStackView stack = (FlippableStackView) findViewById(R.id.stackview_card);
        stack.initStack(2);
        createViewPagerFragments();
        cardStackAdapter = new CardStackAdapter(getSupportFragmentManager(), mViewPagerFragments);
        stack.setAdapter(cardStackAdapter);
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

        for (int i = 0; i < 4; ++i) {
            mViewPagerFragments.add(CardStackFragment.newInstance("1","2"));
        }
    }
}
