package com.test.test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

/**
 * Created by MiJiefei on 2015/11/3.
 */
public class CardListFragment extends Fragment {

    public static void createCardListFragmentFrom(Fragment lastFragment) {
        Fragment newFragment = new CardListFragment();
        FragmentTransaction transaction = lastFragment.getFragmentManager().beginTransaction();
        transaction.remove(lastFragment);
        transaction.replace(R.id.fragment_list, newFragment, FragmentTags.FRAGMENT_CREATE_CARDLIST);
//                transaction.addToBackStack(null);
        transaction.commit();
        lastFragment.getFragmentManager().executePendingTransactions();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        MainActivity.lv = (SwipeListView) view.findViewById(R.id.listViewNameCard);
        if (MainActivity.lv != null) {
            MainActivity.lv.setAdapter(MainActivity.adapter);
//            MainActivity.lv.setOnItemClickListener(this);
//        SwipeListView swipeListView = new SwipeListView();

            MainActivity.lv.setSwipeListViewListener(new BaseSwipeListViewListener() {
                int openItem = -1;
                int lastOpenedItem = -1;
                int lastClosedItem = -1;

                @Override
                public void onOpened(int position, boolean toRight) {
                    lastOpenedItem = position;
                    if (openItem > -1 && lastOpenedItem != lastClosedItem) {
                        MainActivity.lv.closeAnimate(openItem);
                    }
                    openItem = position;
                }

                @Override
                public void onStartClose(int position, boolean right) {
                    Log.d("swipe", String.format("onStartClose %d", position));
                    lastClosedItem = position;
                }

                @Override
                public void onClickFrontView(int position) {
                    super.onClickFrontView(position);
                }

            });
            MainActivity.lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    CardActivity.startActivity(getActivity(), position);
                    return false;
                }
            });
        }
        return view;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Object item = parent.getAdapter().getItem(position);
//        if (item instanceof Card) {
////            phone call
////            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ((Card) item).getPhoneNumber()));
////            startActivity(intent);
//            CardActivity.startActivity(getActivity());
//        }
//
//    }
}
