package com.test.test.Imported;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.bartoszlipinski.flippablestackview.FlippableStackView;
import com.bartoszlipinski.flippablestackview.StackPageTransformer;

/**
 * Created by MiJiefei on 2015/11/10.
 */
public class MyStackView extends FlippableStackView {

    private static final float DEFAULT_CURRENT_PAGE_SCALE = 0.8f;
    private static final float DEFAULT_TOP_STACKED_SCALE = 0.7f;
    private static final float DEFAULT_OVERLAP_FACTOR = 0.4f;

    public MyStackView(Context context) {
        super(context);
    }

    public MyStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Used to create a simple <code>FlippableStackView</code> (only the number of stacked views
     * is being set by the user, other parameters are set to default values).
     *
     * @param numberOfStacked Number of pages stacked under the current page.
     */
    public void initStack(int numberOfStacked) {
        initStack(numberOfStacked, StackPageTransformer.Orientation.VERTICAL, DEFAULT_CURRENT_PAGE_SCALE, DEFAULT_TOP_STACKED_SCALE, DEFAULT_OVERLAP_FACTOR, StackPageTransformer.Gravity.CENTER);
    }

    /**
     * Used to create a simple <code>FlippableStackView</code> (only the number of stacked views
     * is being set by the user, other parameters are set to default values).
     *
     * @param numberOfStacked Number of pages stacked under the current page.
     * @param orientation     Orientation of the stack.
     */
    public void initStack(int numberOfStacked, StackPageTransformer.Orientation orientation) {
        initStack(numberOfStacked, orientation, DEFAULT_CURRENT_PAGE_SCALE, DEFAULT_TOP_STACKED_SCALE, DEFAULT_OVERLAP_FACTOR, StackPageTransformer.Gravity.CENTER);
    }

    /**
     * Used to create <code>FlippableStackView</code> with all customizable parameters defined.
     *
     * @param numberOfStacked  Number of pages stacked under the current page.
     * @param orientation      Orientation of the stack.
     * @param currentPageScale Scale of the current page. Must be a value from (0, 1].
     * @param topStackedScale  Scale of the top stacked page. Must be a value from
     *                         (0, <code>currentPageScale</code>].
     * @param overlapFactor    Defines the usage of available space for the overlapping by stacked
     *                         pages. Must be a value from [0, 1]. Value 1 means that the whole
     *                         available space (obtained due to the scaling with
     *                         <code>currentPageScale</code>) will be used for the purpose of displaying
     *                         stacked views. Value 0 means that no space will be used for this purpose
     *                         (in other words - no stacked views will be visible).
     * @param gravity          Specifies the alignment of the stack (vertically) withing <code>View</code>
     *                         bounds.
     */
    public void initStack(int numberOfStacked, StackPageTransformer.Orientation orientation, float currentPageScale, float topStackedScale, float overlapFactor, StackPageTransformer.Gravity gravity) {
        setOrientation(orientation.getViewPagerOrientation());
        setPageTransformer(false, new StackPageTransformer(numberOfStacked, orientation, currentPageScale, topStackedScale, overlapFactor, gravity));
        setOffscreenPageLimit(numberOfStacked + 1);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        setCurrentItem(adapter.getCount() - 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean flag = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                flag = super.onTouchEvent(ev);
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                // Not else! Note that mIsBeingDragged can be set above.
        }
        return false;
    }
}
