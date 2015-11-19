package com.test.test;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.Database.DbBase;
import com.test.test.Model.Card;
import com.test.test.Model.CardListAdapter;
import com.test.test.Model.ContactsHelper;
import com.test.test.Network.NetThread;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CreateCardFragment.OnFragmentInteractionListener, DrawerLayout.DrawerListener, View.OnTouchListener,
        NavigationView.OnNavigationItemSelectedListener {

    public static List<Card> dataList = new ArrayList<Card>();

    static AppCompatActivity context;

    public static SwipeListView lv;
    public static CardListAdapter adapter;

    Button ib_all_calls;
    Button ib_recent_calls;

    public static View blurView;
    public static View blurCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        context = this;
        setContentView(R.layout.activity_main);
        DbBase.getInstance().Init(this);
        NetThread.getInstance().Init(this);
        blurCover = findViewById(R.id.blur_cover);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        drawer.setScrimColor(Color.parseColor("#64000000"));

        adapter = new CardListAdapter(this, R.layout.card_item, dataList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //暂定导入通讯录
//                ContactsHelper.fetchAllContacts(getInstance());
//                ContactsHelper.removeDuplicate(dataList);
//                adapter.notifyDataSetChanged();
                //test http request
                // Create a new HttpClient and Post Header
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpClient httpclient = new DefaultHttpClient();
//                        HttpPost httppost = new HttpPost("http://42.96.138.5/cgi-bin/hello.cgi");
//
//                        try {
//                            // Add your data
//                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
//                            nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
//                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                            // Execute HTTP Post Request
//                            HttpResponse response = httpclient.execute(httppost);
//
//                        } catch (ClientProtocolException e) {
//                            // TODO Auto-generated catch block
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                        }
//                    }
//                });
                // Instantiate the RequestQueue.
                /** async task about send request
                RequestQueue queue = Volley.newRequestQueue(MainActivity.getInstance());
                String url = "http://42.96.138.5:8080/cgi-bin/hello.cgi";
//                String url = "http://"+"www.baidu.com";

// Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                Log.d("Http", "Response is: "+ response.substring(0));
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        mTextView.setText("That didn't work!");
//                        Log.d("HTTP", error.networkResponse.data.toString());
//                    }
//                });

                //JSON request
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", "AbCdEfGh123456");
                params.put("test", "test123");
                final JSONObject jsonBody = new JSONObject(params);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Http", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Http", error.networkResponse.data.toString());
                    }
                });
// Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
                 */
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
            case R.id.button_all_people:
                GroupActivity.startActivity(this);
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
            ContactsHelper.fetchAllContacts(this);
            ContactsHelper.removeDuplicate(MainActivity.dataList);
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
}
