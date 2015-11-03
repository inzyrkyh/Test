package com.test.test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.test.test.Model.Card;
import com.test.test.Model.CardAdapter;
import com.test.test.Model.ContactsHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CreateCardFragment.OnFragmentInteractionListener {

    public static List<Card> dataList = new ArrayList<Card>();

    static Activity context;

    public static ListView lv;
    public static CardAdapter adapter;

    ImageButton ib_all_calls;
    ImageButton ib_recent_calls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        adapter = new CardAdapter(this, R.layout.card_item, dataList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //暂定导入通讯录
                ContactsHelper.fetchAllContacts(getInstance());
                ContactsHelper.removeDuplicate(dataList);
                adapter.notifyDataSetChanged();
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //启动导入通讯录界面
//                WelcomeFragment.startActivity(getInstance());
                Fragment newFragment = new WelcomeFragment();
                FragmentTransaction transaction =getFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_importing,newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return false;
            }
        });

        ImageButton ib_gongneng = (ImageButton) findViewById(R.id.button_gongneng);
        final ImageButton ib_all_people = (ImageButton) findViewById(R.id.button_all_people);
        ImageButton ib_search = (ImageButton) findViewById(R.id.button_search);
        ImageButton ib_edit = (ImageButton) findViewById(R.id.button_edit);
        EditText editText_searchBar = (EditText) findViewById(R.id.editTextSearchBar);
        ib_all_calls = (ImageButton) findViewById(R.id.button_all_calls);
        ib_all_calls.setSelected(true);
        ib_recent_calls = (ImageButton) findViewById(R.id.button_recent_calls);

        ib_all_calls.setOnClickListener(this);
        ib_recent_calls.setOnClickListener(this);
        ib_gongneng.setOnClickListener(this);
        ib_search.setOnClickListener(this);
        ib_edit.setOnClickListener(this);
//        editText_searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        });

//        lv = (ListView) findViewById(R.id.listViewNameCard);
//
//        //从数据库获取联系人列表/名片列表
//        if (lv.getCount() <= 0) {
//            WelcomeFragment.startActivity(this);
//        }
//        lv.setAdapter(adapter);
    }

    public static Activity getInstance() {
        return context;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_all_calls:
                ib_all_calls.setSelected(true);
                ib_recent_calls.setSelected(false);
                break;
            case R.id.button_recent_calls:
                ib_all_calls.setSelected(false);
                ib_recent_calls.setSelected(true);
                break;
            case R.id.button_gongneng:
                ((DrawerLayout) findViewById(R.id.id_drawerLayout)).openDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
