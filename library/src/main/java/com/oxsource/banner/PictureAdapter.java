package com.oxsource.banner;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 轮播图适配器
 * Created by peng on 2017/7/6.
 */

public class PictureAdapter extends PagerAdapter {
    private final List<Object> modelList = new ArrayList<>();
    private final ViewHolder viewHolder;
    private final IPictureEngine engine;

    public PictureAdapter(@NonNull Context context, @NonNull IPictureEngine engine) {
        this.viewHolder = new ViewHolder(context);
        this.engine = engine;
    }

    public <T> void push(@NonNull T model) {
        modelList.add(model);
    }

    public <T> void pushAll(@NonNull T... models) {
        for (int i = 0; i < models.length; i++) {
            modelList.add(models[i]);
        }
    }

    public <T> void pushAll(@NonNull Collection<T> models) {
        modelList.addAll(models);
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
        ImageView view = viewHolder.obtain();
        engine.load(view, modelList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
        engine.clear(view);
        viewHolder.recycle(view);
    }

    @CallSuper
    public void destroy() {
        viewHolder.release();
        engine.destroy();
    }
}