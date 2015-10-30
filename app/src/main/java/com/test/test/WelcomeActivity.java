package com.test.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.test.Model.ContactsHelper;

public class WelcomeActivity extends Activity implements View.OnClickListener {
    private Button button_import_from_contacts;
    private Button button_import_from_cloud;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        button_import_from_contacts = (Button) findViewById(R.id.button_import_from_contact);
        button_import_from_cloud = (Button) findViewById(R.id.button_import_from_cloud);
        button_import_from_contacts.setOnClickListener(this);
        button_import_from_cloud.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_import_from_contact:
                //TODO: 从通讯录导入逻辑
                ContactsHelper.fetchAllContacts(this);
//                setContentView(R.layout.import_layout);
                ImportActivity.startActivity(this);
                break;
            case R.id.button_import_from_cloud:
                //TODO: 从云端导入逻辑
//                SwipeListView swipeListView;
//                swipeListView.setSwipeListViewListener();
                break;
        }
    }
}
