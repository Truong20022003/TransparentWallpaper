package com.example.wallpaper.Until;

import android.content.Context;
import android.content.SharedPreferences;

import com.amazic.ads.util.AdsConsentManager;


public class SharePrefRemote {
    public static String open_splash = "open_splash";

    public static String inter_splash = "inter_splash";

    public static String Collapsible_banner = "Collapsible_banner";

    public static String rewarded_vip = "rewarded_vip";
    public static String appopen_resume = "appopen_resume";
    public static String native_language = "native_language";
    public static String native_intro = "native_intro";
    public static String native_permission = "native_permission";
    public static String native_view = "native_view";
    public static String native_done = "native_done";
    public static String inter_category = "inter_category";
    public static String inter_done = "inter_done";
    public static String inter_my_wallpaper = "inter_my_wallpaper";

    public static String inter_intro = "inter_intro";
    public static String inter_preview = "inter_preview";
    //   public static String banner_all = "banner_all";
    public static String banner_all = "collapse_all";

    public static String interval_between_interstitial = "interval_between_interstitial";
    public static String interval_interstitial_from_start = "interval_interstitial_from_start";

    public static String rate_aoa_inter_splash = "30_70";
    public static String native_live = "native_live";
    public static String native_top = "native_top";

    public static String interval_between_inter_and_collap = "interval_between_inter_and_collap";
    public static String reload_collap = "reload_collap";
    public static String interstitial_from_start = "interstitial_from_start";

    public static String rating_popup = "rating_popup";


    public static boolean get_config(Context context, String name_config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        if (name_config.equals("style_screen"))
            return pre.getBoolean(name_config, false);
        else
            return (pre.getBoolean(name_config, true) && AdsConsentManager.getConsentResult(context)) ;
    }

    public static void set_config(Context context, String name_config, boolean config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean(name_config, config);
        editor.apply();
    }

    public static void set_config(Context context, String name_config, long config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putLong(name_config, config);
        editor.apply();
    }

    public static long get_config_long(Context context, String name_config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        return pre.getLong(name_config, 0)*1000;
    }

    public static String get_config_string(Context context, String name_config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        return pre.getString(name_config, "");
    }

    public static void set_config_string(Context context, String name_config, String config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString(name_config, config);
        editor.apply();
    }

    public static void set_config_long(Context context, String name_config, long config) {
        SharedPreferences pre = context.getSharedPreferences("remote_fill", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putLong(name_config, config);
        editor.apply();
    }
}
