package com.oxsource.banner.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
        topView.setVisibility(GONE);
        topView.postDelayed(new Runnable() {
            @Override
            public void run() {
                topView.setVisibility(VISIBLE);
                Animation animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 1,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0
                );
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.setDuration(durationMs);
                topView.setAnimation(animation);
                animation.start();
            }
        }, delayMs);
    }
}