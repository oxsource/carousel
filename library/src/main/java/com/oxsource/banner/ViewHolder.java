package com.oxsource.banner;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图复用ViewHolder
 * Created by peng on 2017/7/6.
 */

public abstract class ViewHolder {
    private final List<View> views = new ArrayList<>();
    private final WeakReference<Context> rfContext;
    private final int layout;

    public ViewHolder(Context context, @LayoutRes int layout) {
        this.rfContext = new WeakReference<>(context);
        this.layout = layout;
    }

    protected View build(Context context, int position) {
        LayoutInflater lf = LayoutInflater.from(context);
        View view = lf.inflate(layout, null, false);
        return view;
    }

    protected abstract <T> void onLoadView(View view, int position, T... model);

    protected abstract void onClearView(View view);

    public final <T> View obtain(int position, T... model) {
        View view;
        if (views.size() > 0) {
            view = views.remove(0);
        } else {
            view = build(rfContext.get(), position);
        }
        onLoadView(view, position, model);
        return view;
    }

    public final void recycle(View view) {
        if (null == view.getParent()) {
            views.add(view);
            onClearView(view);
        }
    }

    @CallSuper
    public void release() {
        rfContext.clear();
        views.clear();
    }
}