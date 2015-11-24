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

    public static Group currentGroup;
    private boolean isGroupEditable;

    ArrayList<Card> groupList;
    CardListAdapter groupAdapter;

    private SwipeListView swipeListView;

    public static void startActivity(Context lastContext, int groupIndex) {
        Intent intent = new Intent(lastContext, GroupActivity.class);
        intent.putExtra("groupIndex", groupIndex);
        lastContext.startActivity(intent);
        MainActivity.getInstance().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        int index = getIntent().getIntExtra("groupIndex", 0);
        //test
        if (index != -1) {
            currentGroup = ContactsMgr.getInstance().getGroups().get(index);
//        currentGroup = ContactsMgr.getInstance().newGroup("Friends");
            setTitle(currentGroup.GetGName());
        }
        else {

        }
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        swipeListView = (SwipeListView) findViewById(R.id.id_group_swipelistview);

        groupList = ContactsMgr.getInstance().GetCards(currentGroup.GetGId());
        groupAdapter = new CardListAdapter(this, R.layout.card_item, groupList);


        if (groupList.size() == 0) {
            MainActivity.adapter.setShowCheckBox(true);
            swipeListView.setAdapter(MainActivity.adapter);
            swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
            isGroupEditable = true;
        }
        else {
            swipeListView.setAdapter(groupAdapter);
            swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
            isGroupEditable = false;
        }

        swipeListView.setSwipeListViewListener(new MySwipeListViewListener(swipeListView));
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
                    groupList = ContactsMgr.getInstance().GetCards(currentGroup.GetGId());
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