package com.oxsource.banner;

import android.widget.ImageView;

/**
 * 图片加载引擎接口
 * Created by peng on 2017/7/6.
 */

public interface PictureEngine {

    <T> void load(ImageView view, T model);

    void clear(ImageView view);
}