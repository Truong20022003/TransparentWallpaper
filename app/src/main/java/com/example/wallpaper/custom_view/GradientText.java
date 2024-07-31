package com.example.wallpaper.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.wallpaper.R;


public class GradientText extends AppCompatTextView {

    private int startColor = 0;
    private int endColor = 0;
    private float angle = 0f;

    public GradientText(Context context) {
        super(context);
    }

    public GradientText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GradientText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientText, 0, 0);
        startColor = a.getColor(R.styleable.GradientText_startColor, Color.WHITE);
        endColor = a.getColor(R.styleable.GradientText_endColor, Color.WHITE);
        angle = a.getFloat(R.styleable.GradientText_angle, 0f);
        a.recycle();

        setTextColor(Color.WHITE);

        setGradient();
    }

    private void setGradient() {
        post(new Runnable() {
            @Override
            public void run() {
                int length = getMeasuredWidth();

                Shader textShader = new LinearGradient(
                        0f,
                        0f,
                        (float) (Math.sin(Math.PI * angle / 180) * length),
                        (float) (Math.cos(Math.PI * angle / 180) * length),
                        new int[]{startColor, endColor},
                        null,
                        Shader.TileMode.CLAMP
                );
                getPaint().setShader(textShader);
                invalidate();
            }
        });
    }
}