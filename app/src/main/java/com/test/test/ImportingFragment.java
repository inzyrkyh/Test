package com.test.test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.test.CardListFragment;
import com.test.test.Model.ContactsHelper;
import com.test.test.R;

/**
 * Created by MiJiefei on 2015/11/2.
 */
public class ImportingFragment extends Fragment implements View.OnClickListener {

    public static final int MSG_PROGRESS_UPDATE = 0x110;
    public static final int MSG_LOAD_FRAGMENT = 0x111;
    public static final int MSG_IMPORT_OVER = 0x112;
    public static final int MSG_IMPORT_COMPLETE = 0x113;
    Fragment context;
    TextView textViewProgress;
    TextView textViewImportSuccess;
    Button button_backup_on_cloud;
    Button button_close;
    ImageView imageIndicate;

    public Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_PROGRESS_UPDATE:
                    if (progressBar != null) {
                        int progress = progressBar.getProgress();
                        progressBar.setProgress(msg.arg1);
                        if (msg.arg1 >= 100) {
                            mHandler.removeMessages(MSG_PROGRESS_UPDATE);
                            mHandler.sendEmptyMessage(MSG_IMPORT_COMPLETE);
                        }
                        else {
//                            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 10);
                            textViewProgress.setText(progress + "%");
                        }
                    }
                    break;
                case MSG_LOAD_FRAGMENT:
                    mHandler.sendEmptyMessage(MSG_IMPORT_OVER);
                    break;
                case MSG_IMPORT_OVER:
                    break;
                case MSG_IMPORT_COMPLETE:
                    textViewProgress.setVisibility(View.INVISIBLE);
                    textViewImportSuccess.setVisibility(View.VISIBLE);
                    button_backup_on_cloud.setVisibility(View.VISIBLE);
                    imageIndicate.setVisibility(View.VISIBLE);
                    button_close.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(MSG_IMPORT_OVER);
                    break;
            }
        };
    };

    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.import_layout, container, false);
        imageIndicate = (ImageView) view.findViewById(R.id.imageIndicate);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_importing);
        textViewProgress = (TextView) view.findViewById(R.id.textViewProgress);
        textViewProgress.setText("0%");
        textViewImportSuccess = (TextView) view.findViewById(R.id.textViewImportComplete);
        button_backup_on_cloud = (Button) view.findViewById(R.id.button_backup_on_cloud);
        button_close = (Button) view.findViewById(R.id.button_close);
        button_backup_on_cloud.setOnClickListener(this);
        button_close.setOnClickListener(this);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
        ContactsHelper.fetchAllContacts(MainActivity.getInstance());
        ContactsHelper.removeDuplicate(MainActivity.dataList);
        MainActivity.adapter.notifyDataSetChanged();
//        ContactsHelper.fetchAllContacts(this.getActivity());
//        ContactsHelper.removeDuplicate(MainActivity.dataList);
//        MainActivity.adapter.notifyDataSetChanged();
        context = this;
        MainActivity.hideBlurCover();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_backup_on_cloud:
                Log.d("1", "backup on cloud");
                break;
            case R.id.button_close:
                Log.d("1", "button close");
                CardListFragment.createCardListFragmentFrom(this);
//                Fragment newFragment = new CardListFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.hide(this);
//                transaction.replace(R.id.fragment_list, newFragment);
////                transaction.addToBackStack(null);
//                transaction.commit();
//                getFragmentManager().executePendingTransactions();
                break;
        }
    }
}
