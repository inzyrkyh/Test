package com.test.test;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.test.test.Model.Card;
import com.test.test.Model.CardAdapter;
import com.test.test.Model.ContactsHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CreateCardFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener {

    public static List<Card> dataList = new ArrayList<Card>();

    static AppCompatActivity context;

    public static ListView lv;
    public static CardAdapter adapter;

    ImageButton ib_all_calls;
    ImageButton ib_recent_calls;

    public static View blurView;
    public static View blurCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        context = this;
        setContentView(R.layout.activity_main);
        blurCover = findViewById(R.id.blur_cover);
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
        editText_searchBar.clearFocus();
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
        blurView = findViewById(R.id.blur_layout);
        ((DrawerLayout) findViewById(R.id.id_drawerLayout)).setDrawerListener(this);
    }

    public static AppCompatActivity getInstance() {
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void blur(Bitmap bkg, View view, float offset) {
        long startMs = System.currentTimeMillis();
        float radius = 2f;
        float scaleFactor = 5;

        float finalRadius = (float) Math.max(0.01, radius * offset);

        Bitmap overlay = Bitmap.createBitmap(
                (int)(view.getMeasuredWidth() / scaleFactor),
                (int)(view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);

        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        RenderScript rs = RenderScript.create(getInstance());

        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(finalRadius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        blurCover.setBackground(new BitmapDrawable(getInstance().getResources(), overlay));
        rs.destroy();

//        statusText.setText("cost " + (System.currentTimeMillis() - startMs) + "ms");
    }

    @Override
    public void onDrawerSlide(final View drawerView, final float slideOffset) {
        Log.d("drawerView", drawerView.getId() + "");
        MainActivity.blurView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (slideOffset > 0) {
                    MainActivity.blurView.getViewTreeObserver().removeOnPreDrawListener(this);
                    MainActivity.blurView.buildDrawingCache();
                    Bitmap bmp = MainActivity.blurView.getDrawingCache();
                    MainActivity.blur(bmp, MainActivity.blurView, slideOffset);
                    blurView.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        Log.d("drawerView", drawerView.getId() + "closed");
        blurCover.setBackgroundResource(0);
        blurView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        Log.d("drawerView", newState + "");
    }
}
