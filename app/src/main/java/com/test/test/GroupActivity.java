package com.test.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.Listener.MySwipeListViewListener;
import com.test.test.Model.Card;
import com.test.test.Model.CardListAdapter;
import com.test.test.Model.ContactsMgr;
import com.test.test.Model.Group;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    public static Group testGroup;
    private boolean isGroupEditable;

    ArrayList<Card> groupList;
    CardListAdapter groupAdapter;

    private SwipeListView swipeListView;

    public static void startActivity(Context lastContext) {
        Intent intent = new Intent(lastContext, GroupActivity.class);
//        intent.putExtra("cardPosition", position);
        lastContext.startActivity(intent);
        MainActivity.getInstance().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        //test
        testGroup = new Group();
        testGroup.SetGId(ContactsMgr.GStart + 2);
        testGroup.SetGName("Friends");

        groupList = ContactsMgr.getInstance().GetCards(ContactsMgr.GStart + 2);
        groupAdapter = new CardListAdapter(this, R.layout.card_item, groupList);

        if (groupList.size() == 0) {
            swipeListView = (SwipeListView) findViewById(R.id.id_group_swipelistview);
            MainActivity.adapter.setShowCheckBox(true);
            swipeListView.setAdapter(MainActivity.adapter);
            swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
            isGroupEditable = true;
        }
        else {
            swipeListView = (SwipeListView) findViewById(R.id.id_group_swipelistview);
            swipeListView.setAdapter(groupAdapter);
            swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
            isGroupEditable = false;
        }

        swipeListView.setSwipeListViewListener(new MySwipeListViewListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.adapter.setShowCheckBox(false);
        swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_group_actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rename:
                Toast.makeText(this, "rename", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_edit:
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                isGroupEditable = !isGroupEditable;
                if (isGroupEditable) {
                    MainActivity.adapter.setShowCheckBox(true);
                    swipeListView.setAdapter(MainActivity.adapter);
                    swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
                }
                else {
                    groupList = ContactsMgr.getInstance().GetCards(ContactsMgr.GStart + 2);
                    groupAdapter = new CardListAdapter(this, R.layout.card_item, groupList);
                    MainActivity.adapter.setShowCheckBox(false);
                    swipeListView.setAdapter(groupAdapter);
                    swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
                }
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
