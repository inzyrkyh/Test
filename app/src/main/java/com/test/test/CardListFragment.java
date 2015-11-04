package com.test.test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.Model.Card;

import java.util.Objects;

/**
 * Created by MiJiefei on 2015/11/3.
 */
public class CardListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static void createCardListFragmentFrom(Fragment lastFragment) {
        Fragment newFragment = new CardListFragment();
        FragmentTransaction transaction = lastFragment.getFragmentManager().beginTransaction();
        transaction.hide(lastFragment);
        transaction.replace(R.id.fragment_list, newFragment, FragmentTags.FRAGMENT_CREATE_CARDLIST);
//                transaction.addToBackStack(null);
        transaction.commit();
        lastFragment.getFragmentManager().executePendingTransactions();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        MainActivity.lv = (ListView) view.findViewById(R.id.listViewNameCard);
        if (MainActivity.lv != null) {
            MainActivity.lv.setAdapter(MainActivity.adapter);
            MainActivity.lv.setOnItemClickListener(this);
        }
//        SwipeListView swipeListView = new SwipeListView();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getAdapter().getItem(position);
        if (item instanceof Card) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ((Card) item).getPhoneNumber()));
            startActivity(intent);
        }

    }
}
