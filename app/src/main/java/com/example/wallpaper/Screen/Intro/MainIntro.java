package com.example.wallpaper.Screen.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.amazic.ads.util.Admob;
import com.amazic.ads.util.AppOpenManager;
import com.amazic.ads.util.manager.banner.BannerBuilder;
import com.amazic.ads.util.manager.banner.BannerManager;
import com.amazic.ads.callback.InterCallback;
import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.R;
import com.example.wallpaper.Model.ModelIntro;
import com.example.wallpaper.Screen.Home.TransparentWallpaper;
import com.example.wallpaper.Screen.Language.LanguageScreen;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.IntroViewModel;
import com.example.wallpaper.databinding.ActivityMainIntroBinding;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;

import java.util.ArrayList;
import java.util.List;

public class MainIntro extends BaseActivityViewModel<ActivityMainIntroBinding, IntroViewModel> {

    private InterstitialAd mInterstitialAd;
    private List<ModelIntro> listItemSlide;
//    private BannerManager bannerManager;
private FrameLayout nativeframe_ads;
    NativeManager nativeManager;
    private AppOpenManager appOpenManager;
    private long lastAdTime = 0;
    private static final long AD_INTERVAL = 20000; // 20 giây
    private boolean canShowInterstitialAd() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastAdTime) >= AD_INTERVAL;
    }


    @Override
    protected ActivityMainIntroBinding createBinding() {
        return ActivityMainIntroBinding.inflate(getLayoutInflater());
    }

    @Override
    protected IntroViewModel setViewModel() {
        return new IntroViewModel();
    }

    @Override
    protected void viewModel() {
        // Implement your ViewModel logic here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listItemSlide = new ArrayList<>();
        listItemSlide.add(new ModelIntro(R.drawable.img_intro1, R.string.transparency, R.string.reveal_your_screens_beauty));
        listItemSlide.add(new ModelIntro(R.drawable.img_intro2, R.string.crystal, R.string.clarity_and_style));
        listItemSlide.add(new ModelIntro(R.drawable.img_intro3, R.string.clearvision, R.string.elevate_your_screens_allure));
        binding.viewpageIntro.setAdapter(new AdapterSlideIntro(listItemSlide, binding.viewpageIntro, this));
        binding.dotsIndicator.attachTo(binding.viewpageIntro);
        nativeframe_ads = binding.nativeframeAds;
        appOpenManager = AppOpenManager.getInstance();

        try {
            if (SharePrefRemote.get_config(this, SharePrefRemote.native_language)) {
                nativeframe_ads.setVisibility(View.VISIBLE);
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.admob_native_id));
                NativeBuilder builder = new NativeBuilder(this, nativeframe_ads,
                        R.layout.ads_shimmer_intro_layout, R.layout.ads_native_intro_layout);
                builder.setListIdAd(list);
                nativeManager = new NativeManager(this, this, builder);

            } else {
                nativeframe_ads.removeAllViews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            nativeframe_ads.removeAllViews();
            nativeframe_ads.setVisibility(View.INVISIBLE);
        }

        // Load interstitial ad
        loadInterstitialAd();

        binding.txtNext.setOnClickListener(v -> {
            int currentItem = binding.viewpageIntro.getCurrentItem();
            if (currentItem < listItemSlide.size() - 1) {
                binding.viewpageIntro.setCurrentItem(currentItem + 1);
            } else {
                navigateTo(TransparentWallpaper.class);
            }
        });
    }





}
