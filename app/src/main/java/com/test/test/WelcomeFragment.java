package com.test.test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment implements View.OnClickListener {
    private Button button_import_from_contacts;
    private Button button_import_from_cloud;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WelcomeFragment.class);
        context.startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_layout, container, false);
        button_import_from_contacts = (Button) view.findViewById(R.id.button_import_from_contact);
        button_import_from_cloud = (Button) view.findViewById(R.id.button_import_from_cloud);
        button_import_from_contacts.setOnClickListener(this);
        button_import_from_cloud.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_import_from_contact:
                //TODO: 从通讯录导入逻辑
//                ContactsHelper.fetchAllContacts(this.getActivity());
//                setContentView(R.layout.import_layout);
//                ImportActivity.startActivity(this.getActivity());
                Fragment newFragment = new ImportingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(this);
                transaction.replace(R.id.fragment_progress, newFragment, "ImportingFragment");
//                transaction.addToBackStack(null);
                transaction.commit();
                getFragmentManager().executePendingTransactions();
                break;
            case R.id.button_import_from_cloud:
                //TODO: 从云端导入逻辑
//                SwipeListView swipeListView;
//                swipeListView.setSwipeListViewListener();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
