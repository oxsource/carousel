package com.oxsource.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 指示器
 * Created by peng on 2017/6/29.
 */

public class IndicatorView extends LinearLayout {
    private LayoutParams params;
    private Drawable selectDrawable;
    private Drawable normalDrawable;

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

    /**
     * 修改选中状态
     *
     * @param index
     */
    public void current(int index) {
        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).setBackground(i == index ? selectDrawable : normalDrawable);
        }
    }

    public void notifyChanged(int size) {
        removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = new View(getContext());
            addView(view, params);
        }
        current(0);
    }
}