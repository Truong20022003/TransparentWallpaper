package com.example.wallpaper.ADS;

import androidx.annotation.NonNull;


import com.amazic.ads.util.AdsApplication;
import com.amazic.ads.util.AppOpenManager;
import com.example.wallpaper.Screen.SplashScreen;

public class MyApplication extends AdsApplication {

    public static String PRODUCT_ID_MONTH = "android.test.purchased";
    @Override
    public void onCreate() {
        super.onCreate();
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashScreen.class);
        //init Appsflyer
        //AppsflyerEvent.getInstance().init(this, "1233", true);
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
        return null;
    }

    @Override
    protected boolean isSetUpAdjust() {
        return false;
    }

//    // set id app open resume
//    @Override
//    protected String initAppOpenResume() {
//        return "ca-app-pub-3940256099942544/9257395921";
//    }
//
//    // yêu cầu dùng Adjust thì set = true không dùng thì set = false
//    @Override
//    protected boolean isSetUpAdjust() {
//        return true;
//    }
}
