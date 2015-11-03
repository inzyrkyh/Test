package com.test.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by MiJiefei on 2015/11/3.
 */
public class CardListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        MainActivity.lv = (ListView) view.findViewById(R.id.listViewNameCard);
        if (MainActivity.lv != null) {
            MainActivity.lv.setAdapter(MainActivity.adapter);
        }

        return view;
    }
}
