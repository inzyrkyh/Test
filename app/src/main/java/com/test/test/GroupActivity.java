package com.test.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fortysevendeg.swipelistview.SwipeListView;

public class GroupActivity extends AppCompatActivity {

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

        swipeListView = (SwipeListView) findViewById(R.id.id_group_swipelistview);
        MainActivity.adapter.setShowCheckBox(true);
        swipeListView.setAdapter(MainActivity.adapter);
    }
}
