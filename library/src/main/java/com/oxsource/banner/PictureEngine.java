package com.oxsource.banner;

import android.widget.ImageView;

/**
 * Created by peng on 2017/7/6.
 */

public interface PictureEngine {

    void load(ImageView view, Object model);

    void clear(ImageView view);
}