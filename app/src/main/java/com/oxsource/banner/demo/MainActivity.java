package com.oxsource.banner.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

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

    private PictureAdapter<BannerView> adapter;

    class MyViewHolder extends ViewHolder<BannerView> {
        public final RequestManager manager;

        public MyViewHolder(Context context) {
            super(context);
            manager = Glide.with(getBaseContext());
        }

        @Override
        protected BannerView build(Context context, int position) {
            return new BannerView(context);
        }

        @Override
        protected <T> void onLoadView(BannerView view, int position, T... model) {
            manager.load(model[0]).into(view.getBottomView());
            manager.load(model[1]).into(view.getTopView());
            view.getTopView().setVisibility(View.GONE);
        }

        @Override
        protected void onClearView(BannerView view) {
            manager.clear(view.getBottomView());
            manager.clear(view.getTopView());
            view.getTopView().setVisibility(View.GONE);
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
        adapter = new PictureAdapter<>(viewHolder);
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
                    if (child instanceof BannerView) {
                        BannerView bv = (BannerView) child;
                        bv.getTopView().setVisibility(View.GONE);
                    }
                }
                carousel.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View view = carousel.findViewByIndex(index);
                        if (view instanceof BannerView) {
                            BannerView bv = (BannerView) view;
                            bv.getTopView().setVisibility(View.VISIBLE);
                            if (null == bv.getTopView().getAnimation()) {
                                bv.getTopView().setAnimation(animation);
                            }
                            bv.getTopView().getAnimation().start();
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