package com.oxsource.banner;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by peng on 2017/6/29.
 */

public class CarouselAdapter extends PagerAdapter {
    private final PagerAdapter adapter;
    private ViewPager viewPager;
    private final int EVEN = 50;

    public CarouselAdapter(ViewPager viewPager, PagerAdapter adapter) {
        this.adapter = adapter;
        this.viewPager = viewPager;
    }

    public PagerAdapter getRealAdapter() {
        return adapter;
    }

    @Override
    public int getCount() {
        return getRealCount() * EVEN;
    }

    public int getRealCount() {
        return getRealAdapter().getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return getRealAdapter().isViewFromObject(view, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (0 == getRealCount()) {
            return null;
        }
        int index = position % getRealCount();
        return getRealAdapter().instantiateItem(container, index);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (0 == getRealCount()) {
            return;
        }
        getRealAdapter().destroyItem(container, position, object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        getRealAdapter().startUpdate(container);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = getRealCount() * EVEN / 2;
            viewPager.setCurrentItem(position, false);
        } else if (position == getCount() - 1) {
            position = getRealCount() - 1;
            viewPager.setCurrentItem(position, false);
        }
    }

    @Override
    public Parcelable saveState() {
        return getRealAdapter().saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        getRealAdapter().restoreState(state, loader);
    }
}