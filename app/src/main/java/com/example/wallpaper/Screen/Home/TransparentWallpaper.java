package com.example.wallpaper.Screen.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

import com.amazic.ads.callback.AdCallback;
import com.amazic.ads.callback.InterCallback;
import com.amazic.ads.util.Admob;
import com.amazic.ads.util.AdsConsentManager;
import com.amazic.ads.util.AdsSplash;
import com.amazic.ads.util.AppOpenManager;
import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.R;
import com.example.wallpaper.Model.Rate;
import com.example.wallpaper.Screen.Language.LanguageScreen;
import com.example.wallpaper.Screen.Settings.Feedback.FeedBackActivity;
import com.example.wallpaper.Screen.Settings.Language.LanguageSettingsScreen;
import com.example.wallpaper.Screen.HD_Wallpaper.HDWallpaper;
import com.example.wallpaper.Screen.SplashScreen;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.TranspatentWallpaperViewModel;
import com.example.wallpaper.databinding.ActivityTransparentWallpaperBinding;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class TransparentWallpaper extends BaseActivityViewModel<ActivityTransparentWallpaperBinding, TranspatentWallpaperViewModel> {

    NavigationView navigationView;
    private boolean check = false;
    @Override
    protected ActivityTransparentWallpaperBinding createBinding() {
        return ActivityTransparentWallpaperBinding.inflate(getLayoutInflater());
    }

    @Override
    protected TranspatentWallpaperViewModel setViewModel() {
        return new TranspatentWallpaperViewModel();
    }

    @Override
    protected void viewModel() {

    }
    private FrameLayout native_ads;
    NativeManager nativeManager;
    private AppOpenManager appOpenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransparentWallpaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigationView = binding.nav;
        loadInterstitialAd();
        SharedPreferences sharedPreferences = getSharedPreferences("rated", Context.MODE_PRIVATE);
        Rate.initialize(sharedPreferences);
        if (Rate.isRated()) {
            hideRateMenuItem();
        }
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayoutHome.openDrawer(GravityCompat.START);
            }
        });
        native_ads = binding.nativeframeHomeAds;
        appOpenManager = AppOpenManager.getInstance();
        try {
            if (SharePrefRemote.get_config(this, SharePrefRemote.native_language)) {
                native_ads.setVisibility(View.VISIBLE);
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.admob_native_id));
                NativeBuilder builder = new NativeBuilder(this, native_ads,
                        R.layout.ads_shimmer_language_layout, R.layout.ads_native_language_layout);
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
        binding.nav.getHeaderView(1);
        TextPaint tpToolbar = binding.txtToolBar.getPaint();
        binding.txtToolBar.setText(R.string.transparent_wallpaper);
        float width = tpToolbar.measureText(getResources().getString(R.string.transparent_wallpaper));
        Shader textShader = new LinearGradient(0, 0, width, binding.txtToolBar.getTextSize(),
                new int[]{
                        Color.parseColor("#6A41FB"),
                        Color.parseColor("#8348D1"),
                        Color.parseColor("#9B4EA8"),
                        Color.parseColor("#AB538D"),
                        Color.parseColor("#C45963"),
                        Color.parseColor("#885F42"),
                        Color.parseColor("#FF6900"),
                }, null, Shader.TileMode.CLAMP);
        binding.txtToolBar.getPaint().setShader(textShader);
        binding.hdWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              navigateTo(HDWallpaper.class);


            }
        });
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_language) {
                navigateTo(LanguageSettingsScreen.class);
            } else if (item.getItemId() == R.id.nav_rate) {
                showRateDialog();
            } else if (item.getItemId() == R.id.nav_share) {
                share();
            } else if (item.getItemId() == R.id.nav_feedback) {
                startActivity(new Intent(TransparentWallpaper.this, FeedBackActivity.class));
            } else if (item.getItemId() == R.id.nav_policy) {
                openPrivacyPolicy();
            }


            binding.txtVersion.setText("Version: 1.2.1");
            binding.txtVersion.setVisibility(View.VISIBLE);
            binding.drawerLayoutHome.closeDrawer(GravityCompat.START);

            return false;
        });
        List listID = new ArrayList();
        listID.add(getString(R.string.admob_Collapsible_id));
        Admob admob = Admob.getInstance();
        if (admob == null) {
            return;
        }

        admob.loadCollapsibleBannerFloor(TransparentWallpaper.this, listID, "bottom");
        admob.loadCollapsibleBannerFloorWithReload(this, listID, getLifecycle());

    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayoutHome.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPrivacyPolicy() {
        String privacyPolicyUrl = "https://github.com/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl));
        startActivity(intent);
    }

    private void share() {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + "https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(intentShare, "Share"));
    }

    private void showRateDialog() {
        if (check) return;
        check = true;

        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ratingDialog.init(new RatingDialog.OnPress() {
            @Override
            public void send(int s) {
                Rate.setRated(true);
                Toast.makeText(
                        TransparentWallpaper.this,
                        "Thank you",
                        Toast.LENGTH_SHORT
                ).show();
                ratingDialog.dismiss();
                hideRateMenuItem();
                check = false;
                hideRateMenuItem();
            }

            @Override
            public void rating(int s) {
                Rate.setRated(true);
                Toast.makeText(
                        TransparentWallpaper.this,
                       "Thank you",
                        Toast.LENGTH_SHORT
                ).show();
                ratingDialog.dismiss();
                hideRateMenuItem();
                check = false;
                hideRateMenuItem();
            }

            @Override
            public void cancel() {
                ratingDialog.dismiss();
                check = false;
            }

            @Override
            public void later() {
                ratingDialog.dismiss();
                check = false;
            }

            @Override
            public void gotIt() {
                ratingDialog.dismiss();
                check = false;
            }
        });

        ratingDialog.show();
        ratingDialog.setOnDismissListener(dialog -> {
            check = false;
            if (Rate.isRated()) {
//                binding.llRating.setVisibility(View.GONE);
                hideRateMenuItem();
            }
        });
    }

    private void hideRateMenuItem() {
        MenuItem rateMenuItem = navigationView.getMenu().findItem(R.id.nav_rate);
        if (rateMenuItem != null) {
            rateMenuItem.setVisible(false);
        }
    }


}