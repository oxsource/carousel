package com.oxsource.banner.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oxsource.banner.CarouselView;
import com.oxsource.banner.IndicatorView;
import com.oxsource.banner.PictureAdapter;
import com.oxsource.banner.PictureEngine;

public class MainActivity extends Activity {
    private CarouselView carousel;
    private IndicatorView indicator;
    private Button btAdd;

    private String[] urls = new String[]{
            "http://img1.gtimg.com/news/pics/hv1/55/89/2222/144508300.jpg",
            "http://tvfiles.alphacoders.com/100/hdclearart-10.png",
            "http://cdn3.nflximg.net/images/3093/2043093.jpg",
            "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg"

    };
    private PictureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carousel = findViewById(R.id.carousel);
        indicator = findViewById(R.id.indicator);
        btAdd = findViewById(R.id.btAdd);

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

        for (int i = 0; i < urls.length; i++) {
            adapter.push(urls[i]);
        }
        carousel.setAdapter(adapter);
        carousel.indicator(indicator);
        carousel.setAutoLoop(true);

        btAdd.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.equals(btAdd)) {
                btAdd.setEnabled(false);
                adapter.push("");
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        adapter.destroy();
        super.onDestroy();
    }
}
