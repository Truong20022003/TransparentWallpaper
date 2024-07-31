package com.example.wallpaper.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.R;
import com.example.wallpaper.Screen.Intro.MainIntro;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.SplashViewModel;
import com.example.wallpaper.databinding.ActivitySplashScreenBinding;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends BaseActivityViewModel<ActivitySplashScreenBinding, SplashViewModel> {
    private FrameLayout native_ads;
    NativeManager nativeManager;
    @Override
    protected ActivitySplashScreenBinding createBinding() {
        return ActivitySplashScreenBinding.inflate(getLayoutInflater());
    }

    @Override
    protected SplashViewModel setViewModel() {
        return new SplashViewModel();
    }

    @Override
    protected void viewModel() {

    }

    @Override
    protected void initView() {
        super.initView();
        native_ads = binding.nativeAds;
        try {
            if (SharePrefRemote.get_config(this, SharePrefRemote.native_language)) {
                native_ads.setVisibility(View.VISIBLE);
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.admob_native_id));
                NativeBuilder builder = new NativeBuilder(this, native_ads,
                        R.layout.ads_shimmer_language_layout, R.layout.ads_native_language_layout);
                builder.setListIdAd(list);
                nativeManager = new NativeManager(this, this, builder);

//                nativeManager.setReloadAds();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        startActivity(new Intent(SplashScreen.this, MainIntro.class));


                    }
                }, 50000);

            } else {
                native_ads.removeAllViews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            native_ads.removeAllViews();
            native_ads.setVisibility(View.INVISIBLE);
        }

    }


}