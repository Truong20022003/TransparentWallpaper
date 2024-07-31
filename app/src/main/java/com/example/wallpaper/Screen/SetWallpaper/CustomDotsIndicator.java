package com.example.wallpaper.Screen.SetWallpaper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.wallpaper.R;

import java.util.ArrayList;
import java.util.List;

public class CustomDotsIndicator {
    private LinearLayout dotsLayout;
    private List<ImageView> dots;
    private Context context;


    public CustomDotsIndicator(LinearLayout dotsLayout, int count, Context context) {
        this.dotsLayout = dotsLayout;
        this.context = context;
        this.dots = new ArrayList<>();
        initializeDots(count);
    }

    private void initializeDots(int count) {
        dotsLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(context);
            dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_unselected));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dotsLayout.addView(dot, params);
            dots.add(dot);
        }
    }

    public void updateDots(int position) {
        int visibleDots = Math.min(dots.size(), 3);

        for (int i = 0; i < dots.size(); i++) {
            if (i >= position - 1 && i <= position + 1) {
                dots.get(i).setVisibility(View.VISIBLE);
            } else {
                dots.get(i).setVisibility(View.GONE);
            }
        }

        if (position == 0) {
            dots.get(1).setVisibility(View.VISIBLE);
            dots.get(2).setVisibility(View.GONE);
        } else if (position == dots.size() - 1) {
            dots.get(dots.size() - 2).setVisibility(View.VISIBLE);
            dots.get(dots.size() - 3).setVisibility(View.GONE);
        } else {
            dots.get(position - 1).setVisibility(View.VISIBLE);
            dots.get(position + 1).setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < dots.size(); i++) {
            if (i == position) {
                dots.get(i).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_selected));
            } else {
                dots.get(i).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_unselected));
            }
        }
    }

}
