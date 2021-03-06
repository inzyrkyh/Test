package com.test.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.Listener.MySwipeListViewListener;
import com.test.test.Model.Card;
import com.test.test.Model.CardListAdapter;
import com.test.test.Model.ContactsMgr;
import com.test.test.Model.Group;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {
    public static int iCurGroupId;
    private boolean isGroupEditable;
    private boolean isGroupTitleEditable;

    ArrayList<Card> groupList;
    CardListAdapter groupAdapter;

    private SwipeListView swipeListView;

    EditText titleText;

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // add the custom view to the action bar
        actionBar.setCustomView(R.layout.actionbar_view);
        titleText = (EditText) actionBar.getCustomView().findViewById(
                R.id.searchfield);
//        search.
//        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId,
//                                          KeyEvent event) {
//                return false;
//            }
//        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        iCurGroupId = getIntent().getIntExtra("groupIndex", 0);
        //test
        if (iCurGroupId != ContactsMgr.Gnew) {
            iCurGroupId = ContactsMgr.getInstance().getGroups().get(iCurGroupId).GetGId();
            Group currentGroup = ContactsMgr.getInstance().getAGroup(iCurGroupId);
            setTitle(currentGroup.GetGName());
            titleText.setText(currentGroup.GetGName());
            titleText.setEnabled(false);
            groupList = ContactsMgr.getInstance().GetCards(iCurGroupId);
        }
        else {
            groupList = new ArrayList<>();
            ContactsMgr.getInstance().ClearTemp();
        }
        swipeListView = (SwipeListView) findViewById(R.id.id_group_swipelistview);
        groupAdapter = new CardListAdapter(this, R.layout.card_item, groupList);

        if (groupList.size() == 0) {
            MainActivity.adapter.setShowCheckBox(true);
            swipeListView.setAdapter(MainActivity.adapter);
            swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
            isGroupEditable = true;
        } else {
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
//        MenuItem menuItem = menu.findItem(R.id.action_edit_text);
//        EditText searchView = (EditText)menuItem.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rename:
                //Toast.makeText(this, "rename", Toast.LENGTH_SHORT).show();
                isGroupTitleEditable = !isGroupTitleEditable;
                if (isGroupTitleEditable) {
                    titleText.setEnabled(true);
                }
                else {
                    if (titleText.getText().toString().equals("")) {
                        Toast.makeText(this, "分组名不能为空", Toast.LENGTH_SHORT).show();
                        isGroupTitleEditable = true;
                    }
                    else {
                        if( ContactsMgr.getInstance().getAGroup(titleText.getText().toString()) !=null ){
                            Toast.makeText(this, "分组名不能重复", Toast.LENGTH_SHORT).show();
                            isGroupTitleEditable = true;
                            return true;
                        }
                        ContactsMgr.getInstance().getAGroup(iCurGroupId).SetGName(titleText.getText().toString());
                    }
                }
                return true;
            case R.id.action_edit:
                isGroupEditable = !isGroupEditable;
                if (isGroupEditable) {
                    MainActivity.adapter.setShowCheckBox(true);
                    swipeListView.setAdapter(MainActivity.adapter);
                    swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
                } else {
                    String groupName = titleText.getText().toString();
                    if (groupName.equals("") ) {
                        Toast.makeText(this, "分组名空", Toast.LENGTH_SHORT).show();
                        isGroupEditable = true;
                        return true;
                    }
                    if( iCurGroupId != ContactsMgr.Gnew )
                        groupList = ContactsMgr.getInstance().GetCards(iCurGroupId);
                    else{
                        if (ContactsMgr.getInstance().getAGroup(groupName)!=null) {
                            Toast.makeText(this, "分组名重复", Toast.LENGTH_SHORT).show();
                            isGroupEditable = true;
                            return true;
                        }
                        if( ContactsMgr.getInstance().GetTempSize() > 0 ){
                            groupList = ContactsMgr.getInstance().GetTempAll();
                            ContactsMgr.getInstance().ClearTemp();
                        }
                    }
                    if ( groupList == null || groupList.size() == 0 ) {
                        Toast.makeText(this, "必须至少选中一个联系人", Toast.LENGTH_SHORT).show();
                        isGroupEditable = true;
                    } else {
                        if( iCurGroupId == ContactsMgr.Gnew ) {
                            Group currentGroup = ContactsMgr.getInstance().newGroup(groupName);
                            for (Card card : groupList)
                                card.AddToGroup(currentGroup);
                        }
                        groupAdapter = new CardListAdapter(this, R.layout.card_item, groupList);
                        MainActivity.adapter.setShowCheckBox(false);
                        swipeListView.setAdapter(groupAdapter);
                        swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
                    }
                }
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}