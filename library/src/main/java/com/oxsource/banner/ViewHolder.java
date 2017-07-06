package com.oxsource.banner;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图复用ViewHolder
 * Created by peng on 2017/7/6.
 */

public class ViewHolder {
    private final List<ImageView> views = new ArrayList<>();
    private final WeakReference<Context> rfContext;

    public ViewHolder(Context context) {
        rfContext = new WeakReference<>(context);
    }

    protected ImageView build(Context context) {
        ImageView view = new ImageView(context);
        int wh = LinearLayout.LayoutParams.MATCH_PARENT;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wh, wh);
        view.setLayoutParams(params);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    public ImageView obtain() {
        ImageView view;
        if (views.size() > 0) {
            view = views.remove(0);
        } else {
            view = build(rfContext.get());
        }
        return view;
    }

    public void recycle(ImageView view) {
        if (null == view.getParent()) {
            views.add(view);
        }
    }

    public void release() {
        rfContext.clear();
        views.clear();
    }
}