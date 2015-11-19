package com.test.test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewTouchListener;

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

//            MainActivity.lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

            MainActivity.lv.setSwipeListViewListener(baseSwipeListViewListener);
//            MainActivity.lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    CardActivity.startActivity(getActivity(), position);
//                    return false;
//                }
//            });
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
    BaseSwipeListViewListener baseSwipeListViewListener = new BaseSwipeListViewListener() {
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
          int wantedPosition = position; // Whatever position you're looking for
            int firstPosition = MainActivity.lv.getFirstVisiblePosition() - MainActivity.lv.getHeaderViewsCount(); // This is the same as child #0
            int wantedChild = wantedPosition - firstPosition;
// Say, first visible position is 8, you want position 10, wantedChild will now be 2
// So that means your view is child #2 in the ViewGroup:
            if (wantedChild < 0 || wantedChild >= MainActivity.lv.getChildCount()) {
                Log.w("Test view", "Unable to get view for desired position, because it's not being displayed on screen.");
                return;
            }
// Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
            View wantedView = MainActivity.lv.getChildAt(wantedChild);
            wantedView.findViewById(R.id.id_card_item_back).setAlpha(1);
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

        @Override
        public void onClosed(int position, boolean fromRight) {
            Log.d("swipe", String.format("onClosed %d - fromRight %b", position, fromRight));
            int wantedPosition = position; // Whatever position you're looking for
            int firstPosition = MainActivity.lv.getFirstVisiblePosition() - MainActivity.lv.getHeaderViewsCount(); // This is the same as child #0
            int wantedChild = wantedPosition - firstPosition;
// Say, first visible position is 8, you want position 10, wantedChild will now be 2
// So that means your view is child #2 in the ViewGroup:
            if (wantedChild < 0 || wantedChild >= MainActivity.lv.getChildCount()) {
                Log.w("Test view", "Unable to get view for desired position, because it's not being displayed on screen.");
                return;
            }
// Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
            View wantedView = MainActivity.lv.getChildAt(wantedChild);
            wantedView.findViewById(R.id.id_card_item_back).setAlpha(0);
        }

        @Override
        public void onListChanged() {
            Log.d("swipe", String.format("onListChanged"));
        }

        @Override
        public void onMove(int position, float x) {
            Log.d("swipe", String.format("onMove %d, %f", position, x));
            int wantedPosition = position; // Whatever position you're looking for
            int firstPosition = MainActivity.lv.getFirstVisiblePosition() - MainActivity.lv.getHeaderViewsCount(); // This is the same as child #0
            int wantedChild = wantedPosition - firstPosition;
// Say, first visible position is 8, you want position 10, wantedChild will now be 2
// So that means your view is child #2 in the ViewGroup:
            if (wantedChild < 0 || wantedChild >= MainActivity.lv.getChildCount()) {
                Log.w("Test view", "Unable to get view for desired position, because it's not being displayed on screen.");
                return;
            }
// Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
            View wantedView = MainActivity.lv.getChildAt(wantedChild);
            wantedView.findViewById(R.id.id_card_item_back).setAlpha((float) (x / 100.0));
        }

        @Override
        public void onStartOpen(int position, int action, boolean right) {
            Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
        }

        @Override
        public void onClickBackView(int position) {
            Log.d("swipe", String.format("onClickBackView %d", position));
        }

    };
}
