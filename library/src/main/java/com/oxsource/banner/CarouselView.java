package com.oxsource.banner;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 轮播图
 * Created by peng on 2017/6/29.
 */

public class CarouselView extends ViewPager {
    public interface OnSelectListener {
        void select(int index);
    }

    private final boolean[] autoLoop = new boolean[]{false, true};
    private final int WHAT_LOOP = 100;
    private final int DEFAULT_LOOP_MS = 3000;
    private long loopTimeMs = DEFAULT_LOOP_MS;
    private CarouselAdapter carouselAdapter;
    private Indicator indicator;
    private OnSelectListener selectListener;

    public CarouselView(Context context) {
        super(context);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (WHAT_LOOP == message.what && isLoopAble()) {
                int index = getCurrentItem() + 1;
                index = index >= getAdapter().getCount() ? 0 : index;
                setCurrentItem(index);
            }
            return false;
        }
    });

    /**
     * 设置轮播间隔时间，单位（ms）
     *
     * @param ms
     */
    public CarouselView setLoopMs(long ms) {
        loopTimeMs = ms > DEFAULT_LOOP_MS ? ms : DEFAULT_LOOP_MS;
        return this;
    }

    /**
     * 设置是否开启自动轮播
     *
     * @param loop
     */
    public CarouselView setLoopAble(boolean loop) {
        autoLoop[0] = loop;
        if (loop) {
            handler.postDelayed(loopRunnable, loopTimeMs);
        } else {
            handler.removeCallbacks(loopRunnable);
        }
        return this;
    }

    public CarouselView indicator(@NonNull Indicator indicator) {
        this.indicator = indicator;
        if (null != carouselAdapter) {
            this.indicator.setAdapter(carouselAdapter.getRealAdapter());
        }
        return this;
    }

    public boolean isLoopAble() {
        return autoLoop[0] && autoLoop[1];
    }

    @Override
    public void setAdapter(@NonNull final PagerAdapter adapter) {
        if (null != indicator) {
            indicator.setAdapter(adapter);
        }
        carouselAdapter = new CarouselAdapter(this, adapter);
        super.setAdapter(carouselAdapter);
        removeOnPageChangeListener(changeListener);
        carouselAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                addOnPageChangeListener(changeListener);
                carouselAdapter.unregisterDataSetObserver(this);
            }
        });
        carouselAdapter.notifyDataSetChanged();
    }

    public void setSelectListener(OnSelectListener listener) {
        selectListener = listener;
    }

    public View findViewByIndex(int index) {
        View view = findViewWithTag(Integer.valueOf(index));
        return view;
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

    private Runnable loopRunnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(WHAT_LOOP);
            handler.postDelayed(loopRunnable, loopTimeMs);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacks(loopRunnable);
        super.onDetachedFromWindow();
    }

    private OnPageChangeListener changeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = position % carouselAdapter.getRealCount();
            if (null != indicator) {
                indicator.onSelect(index);
            }
            if (null != selectListener) {
                selectListener.select(index);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}