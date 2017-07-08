package com.oxsource.banner.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
            R.drawable.home_banner_anim_01,
            R.drawable.home_banner_anim_01,
            R.drawable.home_banner_anim_01
    };
    private String[] urls = new String[]{
            "https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lvpics/w%3D1000/sign=c5813c0a8d1001e94e3c100f883e7aec/574e9258d109b3defacf918bccbf6c81810a4ce4.jpg",
            "https://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/lvpics/h%3D800/sign=7c232598c1134954611ee564664f92dd/a5c27d1ed21b0ef439290547d7c451da80cb3ee1.jpg",
            "https://gss0.baidu.com/7LsWdDW5_xN3otqbppnN2DJv/lvpics/h%3D800/sign=40bce4117d1ed21b66c923e59d6fddae/4b90f603738da977fe3af3c8b051f8198618e338.jpg",
            "https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lvpics/h%3D800/sign=4ae6d98caec27d1eba2636c42bd4adaf/b8014a90f603738d37ae670ab91bb051f819ec36.jpg"
    };

    private PictureAdapter<BannerView> adapter;

    class MyViewHolder extends ViewHolder<BannerView> {
        final RequestManager manager;

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
        carousel.setSelectListener(new CarouselView.OnSelectListener() {
            @Override
            public void select(View view, int index) {
                if (view instanceof BannerView) {
                    Log.d("ppp", "index--------------->"+index);
                    BannerView bv = (BannerView) view;
                    bv.showAnimation(500, 300);
                }
            }
        });
        carousel.setAdapter(adapter);
        carousel.indicator(indicator);
        carousel.setLoopMs(5 * 1000);
        carousel.setLoopAble(true);
    }

    @Override
    protected void onDestroy() {
        adapter.destroy();
        super.onDestroy();
    }
}