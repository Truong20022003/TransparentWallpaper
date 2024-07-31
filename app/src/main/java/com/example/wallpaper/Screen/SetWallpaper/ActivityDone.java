package com.example.wallpaper.Screen.SetWallpaper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.wallpaper.R;

public class ActivityDone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_done);
        // Tìm LottieAnimationView trong layout
        LottieAnimationView animationView = findViewById(R.id.lottie_animation_view);

        // Tùy chỉnh hoạt ảnh
        animationView.setAnimation(R.raw.animation);
        animationView.playAnimation(); // Bắt đầu phát hoạt ảnh
        Button btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }
}