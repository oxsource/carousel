package com.oxsource.banner;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图复用ViewHolder
 * Created by peng on 2017/7/6.
 */

public abstract class ViewHolder<V extends View> {
    private final List<V> views = new ArrayList<>();
    private final WeakReference<Context> rfContext;

    public ViewHolder(Context context) {
        rfContext = new WeakReference<>(context);
    }

    protected abstract V build(Context context, int position);

    protected abstract <T> void onLoadView(V view, int position, T... model);

    protected abstract void onClearView(V view);

    public final <T> V obtain(int position, T... model) {
        V view;
        if (views.size() > 0) {
            view = views.remove(0);
        } else {
            view = build(rfContext.get(), position);
        }
        onLoadView(view, position, model);
        return view;
    }

    public final void recycle(V view) {
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