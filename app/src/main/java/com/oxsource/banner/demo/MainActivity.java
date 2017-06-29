package com.oxsource.banner.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.oxsource.banner.CarouselView;
import com.oxsource.banner.IndicatorView;
import com.oxsource.banner.ViewAdapter;

public class MainActivity extends Activity {
    private CarouselView carousel;
    private IndicatorView indicator;
    private Button btAdd;

    private int[] colors = new int[]{Color.YELLOW, Color.GREEN, Color.RED, Color.CYAN};
    private ViewAdapter viewAdapter = new ViewAdapter();
    private View addView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carousel = findViewById(R.id.carousel);
        indicator = findViewById(R.id.indicator);
        btAdd = findViewById(R.id.btAdd);

        addView = newBackgroundColorView(Color.BLUE);

        for (int i = 0; i < colors.length; i++) {
            View view = newBackgroundColorView(colors[i]);
            viewAdapter.push(view);
        }
        carousel.setAdapter(viewAdapter);
        carousel.bindIndicator(indicator);
        carousel.setAutoLoop(true);

        btAdd.setOnClickListener(clickListener);
    }

    private View newBackgroundColorView(int color) {
        View view = new View(getBaseContext());
        int wh = LinearLayout.LayoutParams.MATCH_PARENT;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wh, wh);
        view.setLayoutParams(params);
        view.setBackgroundColor(color);
        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.equals(btAdd)) {
                btAdd.setEnabled(false);
                viewAdapter.push(addView);
                viewAdapter.notifyDataSetChanged();
            }
        }
    };
}
