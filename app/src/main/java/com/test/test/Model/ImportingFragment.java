package com.test.test.Model;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.test.test.R;

/**
 * Created by MiJiefei on 2015/11/2.
 */
public class ImportingFragment extends Fragment {

    private static final int MSG_PROGRESS_UPDATE = 0x110;

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if(progressBar != null) {
                int progress = progressBar.getProgress();
                progressBar.setProgress(++progress);
                if (progress >= 100) {
                    mHandler.removeMessages(MSG_PROGRESS_UPDATE);

                }
                mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
            }
        };
    };

    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_layout, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_importing);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
        ContactsHelper.fetchAllContacts(this.getActivity());
        return view;
    }
}
