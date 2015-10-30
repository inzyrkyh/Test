package com.test.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ImportActivity extends Activity implements View.OnClickListener {

    private LinearLayout layout_import_complete;
    private ProgressBar progressBar;
    private Button button_backup_on_cloud;
    private Button button_cancel;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ImportActivity.class);
        context.startActivity(intent);
//        ContactsHelper.fetchAllContacts(ImportActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layout_import_complete = (LinearLayout) findViewById(R.id.layout_import_complete);
//        ContactsHelper.fetchAllContacts(this);
        progressBar.setVisibility(View.GONE);
        layout_import_complete.setVisibility(View.VISIBLE);
        button_backup_on_cloud = (Button) findViewById(R.id.button_backup_on_cloud);
        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_backup_on_cloud.setOnClickListener(this);
        button_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_backup_on_cloud:
                //去云端备份逻辑
                Toast.makeText(this, "云端备份", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_cancel:
                //取消逻辑
                LoginActivity.startActivity(this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
