package com.example.wallpaper.ADS;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.amazic.ads.callback.AdCallback;
import com.amazic.ads.callback.InterCallback;
import com.amazic.ads.util.Admob;
import com.amazic.ads.util.AppOpenManager;

import java.util.List;
import java.util.Random;

public class AdsSplash {
    private static final String TAG = "AdsSplash";

    private STATE state;
    public AdsSplash() {
        this.state = AdsSplash.STATE.NO_ADS;
    }

    public static AdsSplash init(boolean showInter, boolean showOpen, String rate) {
        AdsSplash adsSplash = new AdsSplash();
        Log.d("AdsSplash", "init: ");
        if (!Admob.isShowAllAds) {
            adsSplash.setState(AdsSplash.STATE.NO_ADS);
        } else if (showInter && showOpen) {
            adsSplash.checkShowInterOpenSplash(rate);
        } else if (showInter) {
            adsSplash.setState(AdsSplash.STATE.INTER);
        } else if (showOpen) {
            adsSplash.setState(AdsSplash.STATE.OPEN);
        } else {
            adsSplash.setState(AdsSplash.STATE.NO_ADS);
        }

        return adsSplash;
    }

    private void checkShowInterOpenSplash(String rate) {
        int rateInter;
        int rateOpen;
        try {
            rateInter = Integer.parseInt(rate.trim().split("_")[1].trim());
            rateOpen = Integer.parseInt(rate.trim().split("_")[0].trim());
        } catch (Exception var5) {
            Log.d("AdsSplash", "checkShowInterOpenSplash: ");
            rateInter = 0;
            rateOpen = 0;
        }

        Log.d("AdsSplash", "rateInter: " + rateInter + " - rateOpen: " + rateOpen);
        Log.d("AdsSplash", "rateInter: " + rateInter + " - rateOpen: " + rateOpen);
        if (rateInter >= 0 && rateOpen >= 0 && rateInter + rateOpen == 100) {
            boolean isShowOpenSplash = (new Random()).nextInt(100) + 1 < rateOpen;
            this.setState(isShowOpenSplash ? AdsSplash.STATE.OPEN : AdsSplash.STATE.INTER);
        } else {
            this.setState(AdsSplash.STATE.NO_ADS);
        }

    }

    public void setState(AdsSplash.STATE state) {
        this.state = state;
    }

    public AdsSplash.STATE getState() {
        return this.state;
    }

    public void showAdsSplash(AppCompatActivity activity, List<String> idsOpen, List<String> idsInter, AdCallback openCallback, InterCallback interCallback) {
        Log.d("AdsSplash", "state show: " + this.getState());
        if (this.getState() == AdsSplash.STATE.OPEN) {
            Admob.getInstance().loadOpenAppAdSplashFloor(activity, idsOpen, openCallback);
        } else if (this.getState() == AdsSplash.STATE.INTER) {
            Admob.getInstance().loadInterAdSplashFloor(activity, idsInter, 3000, 20000, interCallback, true);
        } else {
            interCallback.onNextAction();
        }

    }

    public void onCheckShowSplashWhenFail(AppCompatActivity activity, AdCallback openCallback, InterCallback interCallback) {
        if (this.getState() == AdsSplash.STATE.OPEN) {
            AppOpenManager.getInstance().onCheckShowSplashWhenFailNew(activity, openCallback, 1000);
        } else if (this.getState() == AdsSplash.STATE.INTER) {
            Admob.getInstance().onCheckShowSplashWhenFail(activity, interCallback, 1000);
        }

    }
    public static enum STATE {
        INTER,
        OPEN,
        NO_ADS;

        private STATE() {
        }
    }

}

