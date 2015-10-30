package com.test.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.test.Model.Card;
import com.test.test.Model.CardAdapter;
import com.test.test.Model.ContactsHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Card> dataList = new ArrayList<Card>();

    static Activity context;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CardAdapter adapter = new CardAdapter(this, R.layout.card_item, dataList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //启动导入通讯录界面
//                WelcomeActivity.startActivity(getInstance());
                //暂定导入通讯录
                ContactsHelper.fetchAllContacts(getInstance());
                ContactsHelper.removeDuplicate(dataList);
                adapter.notifyDataSetChanged();
            }
        });

        lv = (ListView) findViewById(R.id.listViewNameCard);

        //从数据库获取联系人列表/名片列表
        if (lv.getCount() <= 0) {
            WelcomeActivity.startActivity(this);
        }
        lv.setAdapter(adapter);
    }

    public static Activity getInstance() {
        return context;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
