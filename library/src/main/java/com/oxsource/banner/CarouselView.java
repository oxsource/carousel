package com.oxsource.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 轮播图
 * Created by peng on 2017/6/29.
 */

public class CarouselView extends ViewPager implements Runnable {
    private final boolean[] autoLoop = new boolean[]{false, true};
    private final int WHAT_LOOP = 100;
    private final int DEFAULT_LOOP_MS = 3000;
    private long loopTimeMs = DEFAULT_LOOP_MS;
    private IndicatorView indicator;
    private CarouselAdapter carouselAdapter;

    public CarouselView(Context context) {
        super(context);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (WHAT_LOOP == message.what && isAutoLoop()) {
                int index = getCurrentItem() + 1;
                index = index >= getAdapter().getCount() ? 0 : index;
                setCurrentItem(index);
            }
            return false;
        }
    });

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (null != getIndicator()) {
                int index = position % carouselAdapter.getRealCount();
                getIndicator().current(index);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 设置轮播间隔时间，单位（ms）
     *
     * @param ms
     */
    public void setAutoLoopTime(long ms) {
        loopTimeMs = ms > DEFAULT_LOOP_MS ? ms : DEFAULT_LOOP_MS;
    }

    /**
     * 设置是否开启自动轮播
     *
     * @param loop
     */
    public void setAutoLoop(boolean loop) {
        autoLoop[0] = loop;
        if (loop) {
            handler.postDelayed(CarouselView.this, loopTimeMs);
        } else {
            handler.removeCallbacks(CarouselView.this);
        }
    }

    public boolean isAutoLoop() {
        return autoLoop[0] && autoLoop[1];
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        carouselAdapter = new CarouselAdapter(this, adapter);
        super.setAdapter(carouselAdapter);
    }

    /**
     * 配置IndicatorView
     *
     * @param view
     */
    public void indicator(@NonNull IndicatorView view) {
        indicator = view;
        indicator.setAdapter(carouselAdapter.getRealAdapter());
        addOnPageChangeListener(pageChangeListener);
    }

    public IndicatorView getIndicator() {
        return indicator;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                autoLoop[1] = false;
                break;
            case MotionEvent.ACTION_UP:
                autoLoop[1] = true;
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(WHAT_LOOP);
        handler.postDelayed(CarouselView.this, loopTimeMs);
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacks(CarouselView.this);
        super.onDetachedFromWindow();
    }
}