package com.oxsource.banner.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.oxsource.banner.CarouselView;
import com.oxsource.banner.IndicatorView;
import com.oxsource.banner.PictureAdapter;
import com.oxsource.banner.ViewHolder;

public class MainActivity extends Activity {
    private CarouselView carousel;
    private IndicatorView indicator;

    private Integer[] anims = new Integer[]{
            R.drawable.home_banner_anim_01,
            R.drawable.home_banner_anim_02,
            R.drawable.home_banner_anim_03,
            R.drawable.home_banner_anim_04
    };
    private String[] urls = new String[]{
            "http://img2.3lian.com/2014/f2/36/d/56.jpg",
            "http://img2.3lian.com/2014/f2/36/d/57.jpg",
            "http://img2.3lian.com/2014/f2/36/d/61.jpg",
            "http://img2.3lian.com/2014/f2/36/d/59.jpg"
    };

    private PictureAdapter adapter;

    class MyViewHolder extends ViewHolder {
        public final RequestManager manager;

        public MyViewHolder(Context context) {
            super(context, R.layout.banner_view);
            manager = Glide.with(getBaseContext());
        }

        @Override
        protected <T> void onLoadView(View view, int position, T... model) {
            ImageView bottom = (ImageView) view.findViewById(R.id.bottomImg);
            ImageView top = (ImageView) view.findViewById(R.id.topImg);
            manager.load(model[0]).into(bottom);
            manager.load(model[1]).into(top);
            top.setVisibility(View.GONE);
        }

        @Override
        protected void onClearView(View view) {
            ImageView bottom = (ImageView) view.findViewById(R.id.bottomImg);
            ImageView top = (ImageView) view.findViewById(R.id.topImg);
            manager.clear(bottom);
            manager.clear(top);
            top.setVisibility(View.GONE);
        }

        @Override
        public void release() {
            super.release();
            manager.onDestroy();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carousel = (CarouselView) findViewById(R.id.carousel);
        indicator = (IndicatorView) findViewById(R.id.indicator);

        final MyViewHolder viewHolder = new MyViewHolder(getBaseContext());
        adapter = new PictureAdapter(viewHolder);
        for (int i = 0; i < urls.length; i++) {
            adapter.push(true, urls[i], anims[i]);
        }

        final Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0
        );
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(300);

        carousel.setSelectListener(new CarouselView.OnSelectListener() {
            @Override
            public void select(final int index) {
                for (int i = 0; i < carousel.getChildCount(); ++i) {
                    View child = carousel.getChildAt(i);
                    View tView = child.findViewById(R.id.topImg);
                    if (null != tView) {
                        tView.setVisibility(View.GONE);
                    }
                }
                carousel.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View view = carousel.findViewByIndex(index);
                        View topView = view.findViewById(R.id.topImg);
                        if (null != topView) {
                            topView.setVisibility(View.VISIBLE);
                            if (null == topView.getAnimation()) {
                                topView.setAnimation(animation);
                            }
                            topView.getAnimation().start();
                        }
                    }
                }, 500);
            }
        });
        carousel.setAdapter(adapter);
        carousel.indicator(indicator)
                .setLoopMs(5 * 1000)
                .setLoopAble(true);
    }

    @Override
    protected void onDestroy() {
        adapter.destroy();
        super.onDestroy();
    }
}