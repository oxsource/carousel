package com.oxsource.banner.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oxsource.banner.CarouselView;
import com.oxsource.banner.IndicatorView;
import com.oxsource.banner.PictureAdapter;
import com.oxsource.banner.PictureEngine;

public class MainActivity extends Activity {
    private CarouselView carousel;
    private IndicatorView indicator;

    private String[] urls = new String[]{
            "https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lvpics/w%3D1000/sign=c5813c0a8d1001e94e3c100f883e7aec/574e9258d109b3defacf918bccbf6c81810a4ce4.jpg",
            "https://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/lvpics/h%3D800/sign=7c232598c1134954611ee564664f92dd/a5c27d1ed21b0ef439290547d7c451da80cb3ee1.jpg",
            "https://gss0.baidu.com/7LsWdDW5_xN3otqbppnN2DJv/lvpics/h%3D800/sign=40bce4117d1ed21b66c923e59d6fddae/4b90f603738da977fe3af3c8b051f8198618e338.jpg",
            "https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/lvpics/h%3D800/sign=4ae6d98caec27d1eba2636c42bd4adaf/b8014a90f603738d37ae670ab91bb051f819ec36.jpg"
    };

    private PictureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carousel = findViewById(R.id.carousel);
        indicator = findViewById(R.id.indicator);
        adapter = new PictureAdapter(getBaseContext(), new PictureEngine() {
            @Override
            public void load(ImageView view, Object model) {
                Glide.with(getBaseContext()).load(model).into(view);
            }

            @Override
            public void clear(ImageView view) {
                Glide.with(getBaseContext()).clear(view);
            }
        });
        adapter.pushAll(urls);
        carousel.setAdapter(adapter);
        carousel.indicator(indicator);
        carousel.setAutoLoop(true);
    }


    @Override
    protected void onDestroy() {
        adapter.destroy();
        super.onDestroy();
    }
}