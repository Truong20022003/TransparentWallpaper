package com.example.wallpaper.Screen;

import android.adservices.AdServicesState;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.amazic.ads.util.Admob;
import com.amazic.ads.util.AdsConsentManager;
import com.amazic.ads.util.AdsSplash;
import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.ADS.Constants;
import com.example.wallpaper.R;
import com.example.wallpaper.Screen.Intro.MainIntro;
import com.example.wallpaper.Screen.Language.LanguageScreen;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.SplashViewModel;
import com.example.wallpaper.databinding.ActivitySplashScreenBinding;
import com.amazic.ads.callback.AdCallback;
import com.amazic.ads.callback.InterCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;


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

        Constants.timeFromStart = System.currentTimeMillis();
        AdsConsentManager adsConsentManager = new AdsConsentManager(this);
        adsConsentManager.requestUMP(!AdsConsentManager.getConsentResult(SplashScreen.this), result -> {
            // accept ump
            if (result) {
                //init sdk
                Admob.getInstance().initAdmod(SplashScreen.this);
                //funtion use to show ads splash
                initShowAdsSplash();
                // trường hợp project tích hợp với remote config thì cần init remote config sau đó mới initShowAdsSplash()
            } else {
                startActivity(new Intent(SplashScreen.this, LanguageScreen.class));
                finish();
            }
        });

    }

    private final AdCallback adCallback = new AdCallback() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            startMainActivity();
        }

        @Override
        public void onAdFailedToLoad(@Nullable LoadAdError i) {
            super.onAdFailedToLoad(i);
            startMainActivity();
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            startMainActivity();
        }
    };
    private final InterCallback interCallback = new InterCallback() {
        @Override
        public void onAdLoaded() {
            // Gọi khi quảng cáo được tải thành công
            startMainActivity();
        }

        @Override
        public void onAdFailedToLoad(LoadAdError i) {
            super.onAdFailedToLoad(i);
            startMainActivity();
        }

        @Override
        public void onAdClosed() {

            startMainActivity();
        }
    };

    private void initShowAdsSplash() {
        Admob.getInstance().setTimeInterval(
            SharePrefRemote.get_config_long(
                    getBaseContext(),
                    SharePrefRemote.interval_interstitial_from_start
            )
        );
        AdsSplash adsSplash = AdsSplash.init(true, true, "30_70");
        List idsOpen = new ArrayList();
        idsOpen.add(getString(R.string.admob_app_open_id));
        List idsInter = new ArrayList();
        idsInter.add(getString(R.string.admob_Interstitial_id));
        adsSplash.showAdsSplash(SplashScreen.this, idsOpen, idsInter, adCallback, interCallback);
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashScreen.this, LanguageScreen.class));
        finish();
    }
}