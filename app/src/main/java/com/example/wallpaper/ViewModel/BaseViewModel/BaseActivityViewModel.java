package com.example.wallpaper.ViewModel.BaseViewModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;
import com.amazic.ads.util.AppOpenManager;
import com.amazic.ads.util.Admob;
import com.amazic.ads.callback.InterCallback;
import com.amazic.ads.util.remote_config.RemoteConfig;
import com.example.wallpaper.ADS.Constants;
import com.example.wallpaper.Screen.HD_Wallpaper.HDWallpaper;
import com.example.wallpaper.Screen.SetWallpaper.SetWallPaperActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.example.wallpaper.R;
import com.example.wallpaper.Until.SharePrefRemote;

public abstract class BaseActivityViewModel<VB extends ViewBinding, V extends ViewModel> extends AppCompatActivity {

    protected VB binding;
    protected V viewModel;
    private boolean isShowingAds = false;

    private InterstitialAd mInterstitialAd;
    private long lastAdTime = 0;

    // Default values, can be overridden by remote config
    private long intervalBetweenInterstitial; // Will be loaded from remote config
    private static final long INTERVAL_INTERSTITIAL_FROM_START = 15 * 1000; // 15 seconds

    protected abstract VB createBinding();
    protected abstract V setViewModel();

    protected void initView() {}
    protected void bindView() {}
    protected abstract void viewModel();
    protected void initData() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = createBinding();
        setContentView(binding.getRoot());
        viewModel = setViewModel();

        // Initialize AppOpenManager if needed
        AppOpenManager.getInstance().init(getApplication(), getString(R.string.admob_app_open_id));

        // Retrieve the interval from remote config
        intervalBetweenInterstitial = SharePrefRemote.get_config_long(this, SharePrefRemote.interval_between_interstitial);

        initView();
        viewModel();
        hideNavigationBar();
    }

    protected void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isShowingAds) {
            disableAdsResume();
            if (SharePrefRemote.get_config(this, SharePrefRemote.appopen_resume)) {
                enableAdsResume();
            }
            isShowingAds = true;
        } else {
            // Show App Open Ad when returning to the app from the background
            AppOpenManager.getInstance().showAdIfAvailable(true);
        }
    }

    public void enableAdsResume() {
        AppOpenManager.getInstance().enableAppResumeWithActivity(getClass());
        AppOpenManager.getInstance().showAdIfAvailable(true);
    }

    public void disableAdsResume() {
        AppOpenManager.getInstance().disableAppResumeWithActivity(getClass());
    }

    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.admob_Interstitial_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });
    }

    private boolean canShowInterstitialAd() {
        long timeInterStart = SharePrefRemote.get_config_long(this, SharePrefRemote.interval_interstitial_from_start);
        long currentTime = System.currentTimeMillis();
        boolean hasEnoughTimePassedSinceStart = (currentTime - Constants.timeFromStart) >= INTERVAL_INTERSTITIAL_FROM_START;
        boolean hasEnoughTimePassedSinceLastAd = (currentTime - lastAdTime) >= intervalBetweenInterstitial;

        return hasEnoughTimePassedSinceStart && hasEnoughTimePassedSinceLastAd;
    }

    public void showInterstitialAd(final Class<?> activityClass) {
        if (canShowInterstitialAd() && mInterstitialAd != null) {
            Admob.getInstance().setOpenActivityAfterShowInterAds(false);
            Admob.getInstance().showInterAds(this, mInterstitialAd, new InterCallback() {
                @Override
                public void onNextAction() {
                    super.onNextAction();
                    mInterstitialAd = null;
                    lastAdTime = System.currentTimeMillis();
                    Admob.getInstance().setOpenActivityAfterShowInterAds(true);

                    startActivity(new Intent(BaseActivityViewModel.this, activityClass));
                    finish();
                }

                @Override
                public void onAdClosedByUser() {
                    super.onAdClosedByUser();
                    lastAdTime = System.currentTimeMillis();
                    Admob.getInstance().setOpenActivityAfterShowInterAds(true);

                    startActivity(new Intent(BaseActivityViewModel.this, activityClass));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(this, activityClass));
            finish();
        }
    }

    protected void navigateTo(Class<?> activityClass) {
        if (canShowInterstitialAd()) {
            showInterstitialAd(activityClass);
        } else {
            startActivity(new Intent(this, activityClass));
            finish();
        }
    }
    public void showInterstitialAdAddPosition(final Class<?> activityClass, int pos) {
        if (canShowInterstitialAd() && mInterstitialAd != null) {
            Admob.getInstance().setOpenActivityAfterShowInterAds(false);
            Admob.getInstance().showInterAds(this, mInterstitialAd, new InterCallback() {
                @Override
                public void onNextAction() {
                    super.onNextAction();
                    mInterstitialAd = null;
                    lastAdTime = System.currentTimeMillis();
                    Admob.getInstance().setOpenActivityAfterShowInterAds(true);
                    Intent intent = new Intent(BaseActivityViewModel.this, activityClass);
                    intent.putExtra("position",pos);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAdClosedByUser() {
                    super.onAdClosedByUser();
                    lastAdTime = System.currentTimeMillis();
                    Admob.getInstance().setOpenActivityAfterShowInterAds(true);
                    Intent intent = new Intent(BaseActivityViewModel.this, activityClass);
                    intent.putExtra("position",pos);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            startActivity(new Intent(this, activityClass));
            finish();
        }
    }

    protected void navigateToAddPosition(Class<?> activityClass, int pos) {
        if (canShowInterstitialAd()) {
            showInterstitialAdAddPosition(activityClass,pos);
        } else {
            Intent intent = new Intent(BaseActivityViewModel.this, activityClass);
            intent.putExtra("position",pos);
            startActivity(intent);
            finish();
        }
    }



}
