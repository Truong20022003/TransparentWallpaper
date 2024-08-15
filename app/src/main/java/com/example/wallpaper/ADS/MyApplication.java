package com.example.wallpaper.ADS;

import android.app.Application;
import androidx.annotation.NonNull;
import com.amazic.ads.util.AdsApplication;
import com.amazic.ads.util.AppOpenManager;
import com.example.wallpaper.R;
import com.example.wallpaper.Screen.SplashScreen;

public class MyApplication extends AdsApplication {

    public static String PRODUCT_ID_MONTH = "android.test.purchased";

    @Override
    public void onCreate() {
        super.onCreate();
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashScreen.class);

        // Initialize AppOpenManager here
        AppOpenManager.getInstance().init(this, getString(R.string.admob_app_open_id));
    }

    @NonNull
    @Override
    public String getAppTokenAdjust() {
        return null;
    }

    @NonNull
    @Override
    public String getFacebookID() {
        return null;
    }

    @Override
    public Boolean buildDebug() {
        return null;
    }

    @Override
    protected String initAppOpenResume() {
        return getString(R.string.admob_app_open_id);
    }

    @Override
    protected boolean isSetUpAdjust() {
        return true;
    }
}
