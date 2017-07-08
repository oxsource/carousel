package com.oxsource.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 指示器
 * Created by peng on 2017/6/29.
 */

public class IndicatorView extends LinearLayout implements Indicator {
    private LayoutParams params;
    private Drawable selectDrawable;
    private Drawable normalDrawable;
    private PagerAdapter adapter;

    private final int DEFAULT_WH_DP = 15;
    private final int DEFAULT_MARGIN_DP = 5;

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        int width = (int) ta.getDimension(R.styleable.IndicatorView_child_width, dp2px(DEFAULT_WH_DP));
        int height = (int) ta.getDimension(R.styleable.IndicatorView_child_height, dp2px(DEFAULT_WH_DP));
        int front = (int) ta.getDimension(R.styleable.IndicatorView_child_front, dp2px(DEFAULT_MARGIN_DP));
        int behind = (int) ta.getDimension(R.styleable.IndicatorView_child_behind, dp2px(DEFAULT_MARGIN_DP));
        params = new LayoutParams(width, height);
        if (getOrientation() == VERTICAL) {
            params.topMargin = front;
            params.bottomMargin = behind;
        } else {
            setOrientation(HORIZONTAL);
            params.leftMargin = front;
            params.rightMargin = behind;
        }
        selectDrawable = ta.getDrawable(R.styleable.IndicatorView_child_select);
        normalDrawable = ta.getDrawable(R.styleable.IndicatorView_child_normal);
        ta.recycle();
    }

    private float dp2px(int dp) {
        DisplayMetrics dm = new DisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        removeObserver();
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(observer);
        adapter.notifyDataSetChanged();
    }

    private DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            removeAllViews();
            for (int i = 0; i < adapter.getCount(); i++) {
                View view = new View(getContext());
                addView(view, params);
            }
            onSelect(0);
        }
    };

    @Override
    public void onSelect(int index) {
        if (index < 0 || index >= getChildCount()) {
            return;
        }
        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).setBackground(i == index ? selectDrawable : normalDrawable);
        }
    }

    private void removeObserver() {
        if (null != this.adapter) {
            this.adapter.unregisterDataSetObserver(observer);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        removeObserver();
        super.onDetachedFromWindow();
    }
}