package com.example.wallpaper.Screen.SetWallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.amazic.ads.callback.AdCallback;
import com.amazic.ads.callback.InterCallback;
import com.amazic.ads.util.Admob;
import com.amazic.ads.util.AdsConsentManager;
import com.amazic.ads.util.AdsSplash;
import com.amazic.ads.util.AppOpenManager;
import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.R;
import com.example.wallpaper.Screen.HD_Wallpaper.HDWallpaper;
import com.example.wallpaper.Screen.Home.TransparentWallpaper;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.DoneViewModel;
import com.example.wallpaper.databinding.ActivityDoneBinding;
import com.google.android.gms.ads.LoadAdError;

import java.util.ArrayList;
import java.util.List;

public class ActivityDone extends BaseActivityViewModel<ActivityDoneBinding, DoneViewModel> {

    private FrameLayout native_ads;
    private NativeManager nativeManager;

    @Override
    protected ActivityDoneBinding createBinding() {
        return ActivityDoneBinding.inflate(getLayoutInflater());
    }

    @Override
    protected DoneViewModel setViewModel() {
        return new DoneViewModel();
    }

    @Override
    protected void viewModel() {
        // Implement viewModel logic if needed
    }



    @Override
    protected void initView() {
        super.initView();
        EdgeToEdge.enable(this);
        // Tìm LottieAnimationView trong layout
        LottieAnimationView animationView = binding.lottieAnimationView;

        // Tùy chỉnh hoạt ảnh
        animationView.setAnimation(R.raw.animation);
        animationView.playAnimation(); // Bắt đầu phát hoạt ảnh


        native_ads = binding.nativeDoneActivityAds;

        // Load native ads
        loadNativeAds();

        binding.btnDone.setOnClickListener(v -> {
            navigateTo(TransparentWallpaper.class);
        });
    }

    private void loadNativeAds() {
        try {
            if (SharePrefRemote.get_config(this, SharePrefRemote.native_language)) {
                native_ads.setVisibility(View.VISIBLE);
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.admob_native_id));
                NativeBuilder builder = new NativeBuilder(this, native_ads,
                        R.layout.ads_shimmer_done_layout, R.layout.ads_native_done_layout);
                builder.setListIdAd(list);
                nativeManager = new NativeManager(this, this, builder);
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
