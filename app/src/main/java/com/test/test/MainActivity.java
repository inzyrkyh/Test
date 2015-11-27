package com.test.test;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.DevsmartLib.HorizontalListView;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.CardActivity;
import com.test.test.CreateCardFragment;
import com.test.test.FragmentTags;
import com.test.test.GroupActivity;
import com.test.test.Model.CardListAdapter;
import com.test.test.Model.ContactsMgr;
import com.test.test.Model.GroupAdapter;
import com.test.test.R;
import com.test.test.WelcomeFragment;
import com.xiaoniao.bai.mingpianjia.AppMain;
import com.xiaoniao.bai.net.NetPacket;
import com.xiaoniao.bai.utils.AppConstants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CreateCardFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener, View.OnTouchListener,
        NavigationView.OnNavigationItemSelectedListener {

    //public static List<Card> dataList2 = new ArrayList<Card>();

    static MainActivity context;

    public static SwipeListView lv;
    public static CardListAdapter adapter;

    Button ib_all_calls;
    Button ib_recent_calls;

    public static View blurView;
    public static View blurCover;

    private PopupWindow popupWindow;
    private View view;
    private HorizontalListView lv_group;
    private ImageButton button_addGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        context = this;
        setContentView(R.layout.activity_main);
        AppMain.getInstance().Init(this);
        blurCover = findViewById(R.id.blur_cover);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        drawer.setScrimColor(Color.parseColor("#64000000"));

        adapter = new CardListAdapter(this, R.layout.card_item, ContactsMgr.getInstance().GetContacts());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //启动导入通讯录界面
//                WelcomeFragment.startActivity(getInstance());
                Fragment newFragment = new WelcomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_importing,newFragment);
//                transaction.addToBackStack(null);
                transaction.commit();
                ((DrawerLayout) findViewById(R.id.id_drawerLayout)).closeDrawer(Gravity.RIGHT);
                return false;
            }
        });

        ImageButton ib_gongneng = (ImageButton) findViewById(R.id.button_gongneng);
        final ImageButton ib_all_people = (ImageButton) findViewById(R.id.button_all_people);
        ImageButton ib_search = (ImageButton) findViewById(R.id.button_search);
        ImageButton ib_edit = (ImageButton) findViewById(R.id.button_edit);
        EditText editText_searchBar = (EditText) findViewById(R.id.editTextSearchBar);
        editText_searchBar.clearFocus();
        ib_all_calls = (Button) findViewById(R.id.button_all_calls);
        ib_all_calls.setSelected(true);
        ib_recent_calls = (Button) findViewById(R.id.button_recent_calls);

        ib_all_calls.setOnClickListener(this);
        ib_all_calls.setOnTouchListener(this);
        ib_recent_calls.setOnClickListener(this);
        ib_recent_calls.setOnTouchListener(this);
        ib_gongneng.setOnClickListener(this);
        ib_search.setOnClickListener(this);
        ib_edit.setOnClickListener(this);
        ib_all_people.setOnClickListener(this);
        blurView = findViewById(R.id.blur_layout);
        ((DrawerLayout) findViewById(R.id.id_drawerLayout)).setDrawerListener(this);
    }

    public static MainActivity getInstance() {
        return context;
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetPacket.getInstance().startRcvBroadcast();
    }

    public void showCard(String info){
        Intent intent = new Intent(MainActivity.this, CardActivity.class);
        intent.putExtra("cardPosition", 0);
        intent.putExtra(AppConstants.TestKey, info);
        startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        NetPacket.getInstance().stopRcvBroadcast();
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
            case R.id.button_all_people:
                //                GroupActivity.startActivity(this);
                showWindow(v);
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
        float scaleFactor = 8;

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
//        Log.d("drawerView", drawerView.getId() + "");
//        MainActivity.blurView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                if (slideOffset > 0) {
//                    MainActivity.blurView.getViewTreeObserver().removeOnPreDrawListener(this);
//                    MainActivity.blurView.buildDrawingCache();
//                    Bitmap bmp = MainActivity.blurView.getDrawingCache();
//                    MainActivity.blur(bmp, MainActivity.blurView, slideOffset);
//                    blurView.setVisibility(View.INVISIBLE);
//                }
//                return true;
//            }
//        });
//        if (slideOffset == 0) {
//            hideBlurCover();
//        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
//        Log.d("drawerView", drawerView.getId() + "closed");
        hideBlurCover();
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        Log.d("drawerView", newState + "");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.button_all_calls:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ib_all_calls.setSelected(true);
                    ib_recent_calls.setSelected(false);
                }
                break;
            case R.id.button_recent_calls:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ib_all_calls.setSelected(false);
                    ib_recent_calls.setSelected(true);
                }
                break;
        }
        return false;
    }

    public static void hideBlurCover() {
        blurCover.setBackgroundResource(0);
        blurView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_personal_detail) {
            Fragment newFragment = new CreateCardFragment();
            Bundle args = new Bundle();
            args.putBoolean("isTheCardMine", true);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.create_card, newFragment, FragmentTags.FRAGMENT_CREATE_CARD);
            transaction.commit();
            System.gc();
            ((DrawerLayout) findViewById(R.id.id_drawerLayout)).setDrawerListener(null);
            ((DrawerLayout) findViewById(R.id.id_drawerLayout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                        MainActivity.hideBlurCover();
            MainActivity.blurView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
            public boolean onPreDraw() {
                MainActivity.blurView.getViewTreeObserver().removeOnPreDrawListener(this);
                MainActivity.blurView.buildDrawingCache();
                Bitmap bmp = MainActivity.blurView.getDrawingCache();
                MainActivity.blur(bmp, MainActivity.blurView, 1);
                blurView.setVisibility(View.INVISIBLE);
                return true;
            }
            });
            // Handle the camera action
        } else if (id == R.id.nav_add_contact) {

        } else if (id == R.id.nav_import_contact) {
            //ContactsHelper.fetchAllContacts(this);
            //ContactsHelper.removeDuplicate(MainActivity.dataList);
            MainActivity.adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_friend_group) {
            if (adapter.getShowCheckBox()) {
                adapter.setShowCheckBox(false);
                adapter.notifyDataSetChanged();
            }
            else {
                adapter.setShowCheckBox(true);
                adapter.notifyDataSetChanged();
            }
        } else if (id == R.id.nav_sync_with_cloud) {

        } else if (id == R.id.nav_position_share) {

        } else if (id == R.id.nav_navigation) {

        } else if (id == R.id.nav_birthday_remind) {

        } else if (id == R.id.nav_notification) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showWindow(View parent) {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.group_list, null);

            button_addGroup = (ImageButton) view.findViewById(R.id.id_add_group);

            lv_group = (HorizontalListView) view.findViewById(R.id.id_group_listview);
            // 加载数据


            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, ((RelativeLayout)parent.getParent()).getMeasuredWidth(), 200);
        }
        GroupAdapter groupAdapter = new GroupAdapter(this, ContactsMgr.getInstance().getGroups());
        lv_group.setAdapter(groupAdapter);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        Log.i("coder", "xPos:" + xPos);

        popupWindow.showAsDropDown(parent, xPos, -(int) ((RelativeLayout) parent.getParent()).getHeight());

        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                GroupActivity.startActivity(MainActivity.getInstance(), position);
            }
        });

        button_addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("group", "addgroup");

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                GroupActivity.startActivity(MainActivity.getInstance(), ContactsMgr.Gnew);
            }
        });
    }
}
