package com.oxsource.banner;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图适配器
 * Created by peng on 2017/7/6.
 */

public class PictureAdapter<V extends View> extends PagerAdapter {
    private final List<Object[]> modelList = new ArrayList<>();
    private final ViewHolder<V> viewHolder;

    public PictureAdapter(@NonNull ViewHolder<V> holder) {
        this.viewHolder = holder;
    }

    /**
     * @param group  if as one group
     * @param models
     * @param <T>
     */
    public <T> void push(boolean group, @NonNull T... models) {
        if (models.length == 0) {
            return;
        }
        if (group) {
            Object[] objects = new Object[models.length];
            for (int i = 0; i < models.length; i++) {
                objects[i] = models[i];
            }
            modelList.add(objects);
        } else {
            for (int i = 0; i < models.length; i++) {
                modelList.add(new Object[]{models[i]});
            }
        }
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        V view = viewHolder.obtain(position, modelList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        V view = (V) object;
        container.removeView(view);
        viewHolder.recycle(view);
    }

    @CallSuper
    public void destroy() {
        viewHolder.release();
    }
}