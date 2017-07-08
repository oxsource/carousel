package com.oxsource.banner;

import android.support.v4.view.PagerAdapter;

/**
 * @author FengPeng
 * @date 2017/7/8
 */
public interface Indicator {

    void setAdapter(PagerAdapter adapter);

    void onSelect(int index);
}