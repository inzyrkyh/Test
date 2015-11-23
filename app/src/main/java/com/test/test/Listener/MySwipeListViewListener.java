package com.test.test.Listener;

import android.util.Log;
import android.view.View;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.MainActivity;
import com.test.test.R;

/**
 * Created by Hyk on 2015/11/23.
 */
public class MySwipeListViewListener extends BaseSwipeListViewListener {

    private SwipeListView swipeListView;

    public MySwipeListViewListener(SwipeListView listView) {
        swipeListView = listView;
    }

    int openItem = -1;
    int lastOpenedItem = -1;
    int lastClosedItem = -1;

    @Override
    public void onOpened(int position, boolean toRight) {
        lastOpenedItem = position;
        if (openItem > -1 && lastOpenedItem != lastClosedItem) {
            swipeListView.closeAnimate(openItem);
        }
        openItem = position;
        int wantedPosition = position; // Whatever position you're looking for
        int firstPosition = swipeListView.getFirstVisiblePosition() - swipeListView.getHeaderViewsCount(); // This is the same as child #0
        int wantedChild = wantedPosition - firstPosition;
// Say, first visible position is 8, you want position 10, wantedChild will now be 2
// So that means your view is child #2 in the ViewGroup:
        if (wantedChild < 0 || wantedChild >= swipeListView.getChildCount()) {
            Log.w("Test view", "Unable to get view for desired position, because it's not being displayed on screen.");
            return;
        }
// Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
        View wantedView = swipeListView.getChildAt(wantedChild);
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
        int firstPosition = swipeListView.getFirstVisiblePosition() - swipeListView.getHeaderViewsCount(); // This is the same as child #0
        int wantedChild = wantedPosition - firstPosition;
// Say, first visible position is 8, you want position 10, wantedChild will now be 2
// So that means your view is child #2 in the ViewGroup:
        if (wantedChild < 0 || wantedChild >= swipeListView.getChildCount()) {
            Log.w("Test view", "Unable to get view for desired position, because it's not being displayed on screen.");
            return;
        }
// Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
        View wantedView = swipeListView.getChildAt(wantedChild);
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
        int firstPosition = swipeListView.getFirstVisiblePosition() - swipeListView.getHeaderViewsCount(); // This is the same as child #0
        int wantedChild = wantedPosition - firstPosition;
// Say, first visible position is 8, you want position 10, wantedChild will now be 2
// So that means your view is child #2 in the ViewGroup:
        if (wantedChild < 0 || wantedChild >= swipeListView.getChildCount()) {
            Log.w("Test view", "Unable to get view for desired position, because it's not being displayed on screen.");
            return;
        }
// Could also check if wantedPosition is between listView.getFirstVisiblePosition() and listView.getLastVisiblePosition() instead.
        View wantedView = swipeListView.getChildAt(wantedChild);
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
}
