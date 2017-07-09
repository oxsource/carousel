package com.oxsource.banner.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author FengPeng
 * @date 2017/7/8
 */
public class BannerView extends RelativeLayout {
    private ImageView bottomView;
    private ImageView topView;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.banner_view, this);
        int size = LayoutParams.MATCH_PARENT;
        LayoutParams params = new LayoutParams(size, size);
        setLayoutParams(params);

        bottomView = (ImageView) findViewById(R.id.bottomImg);
        topView = (ImageView) findViewById(R.id.topImg);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public ImageView getBottomView() {
        return bottomView;
    }

    public ImageView getTopView() {
        return topView;
    }

    public void showAnimation(int delayMs, final int durationMs) {

    }

    private String viString(View v) {
        int vi = v.getVisibility();
        String viString = "INVISIBLE";
        if (vi == View.VISIBLE) {
            viString = "VISIBLE";
        } else if (vi == View.GONE) {
            viString = "GONE";
        }
        return viString;
    }
}