package com.example.wallpaper.Screen.SetWallpaper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.amazic.ads.util.AppOpenManager;
import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.R;
import com.example.wallpaper.Model.HdWallpaperModel;
import com.example.wallpaper.Screen.HD_Wallpaper.HDWallpaper;
import com.example.wallpaper.Screen.Home.TransparentWallpaper;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.SetWallpaperViewModel;
import com.example.wallpaper.databinding.ActivitySetWallPaperBinding;
import com.example.wallpaper.databinding.DialogChooseScreenBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetWallPaperActivity extends BaseActivityViewModel<ActivitySetWallPaperBinding, SetWallpaperViewModel> {

    private List<HdWallpaperModel> list;
    private FrameLayout nativeframe_ads;
    NativeManager nativeManager;
    private AppOpenManager appOpenManager;
    @Override
    protected ActivitySetWallPaperBinding createBinding() {
        return ActivitySetWallPaperBinding.inflate(getLayoutInflater());
    }

    @Override
    protected SetWallpaperViewModel setViewModel() {
        return new SetWallpaperViewModel();
    }

    @Override
    protected void viewModel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySetWallPaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();
        list.add(new HdWallpaperModel(1, R.drawable.img_content_1));
        list.add(new HdWallpaperModel(2, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(3, R.drawable.img_content_2));
        list.add(new HdWallpaperModel(4, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(5, R.drawable.img_content_3));
        list.add(new HdWallpaperModel(6, R.drawable.img_content_6));
        list.add(new HdWallpaperModel(7, R.drawable.img_content_2));
        list.add(new HdWallpaperModel(8, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(9, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(10, R.drawable.img_content_3));
        binding.imgBackT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(HDWallpaper.class);
            }
        });

        SetWallViewPager2Adapter adapter = new SetWallViewPager2Adapter(list, binding.viewpage2SetWallpaper);
        binding.viewpage2SetWallpaper.setAdapter(adapter);

        CustomDotsIndicator customDotsIndicator = new CustomDotsIndicator(binding.customDotsIndicator, list.size(), this);
        customDotsIndicator.updateDots(0);
        binding.viewpage2SetWallpaper.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                customDotsIndicator.updateDots(position);

            }
        });



        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        int offsetPx = getResources().getDimensionPixelOffset(R.dimen.offset);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(pageMarginPx));
        compositePageTransformer.addTransformer((page, position) -> {
            float scale = 1 - Math.abs(position) * 0.1f;
            page.setScaleY(scale);
        });

        binding.viewpage2SetWallpaper.setPageTransformer(compositePageTransformer);
        binding.viewpage2SetWallpaper.setPadding(offsetPx, 0, offsetPx, 0);
        binding.viewpage2SetWallpaper.setClipToPadding(false);
        binding.viewpage2SetWallpaper.setClipChildren(false);
        binding.viewpage2SetWallpaper.setOffscreenPageLimit(3);
        binding.viewpage2SetWallpaper.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        binding.viewpage2SetWallpaper.setCurrentItem(position);

        binding.btnSetWallpaper.setOnClickListener(v -> showDialogChoose());

        nativeframe_ads = binding.nativeframeSetWallAds;
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
    }


    private void showDialogChoose() {
        LayoutInflater layoutInflater = getLayoutInflater();
        DialogChooseScreenBinding dialogChooseScreenBinding = DialogChooseScreenBinding.inflate(layoutInflater);
        AlertDialog.Builder builder = new AlertDialog.Builder(SetWallPaperActivity.this);

        builder.setView(dialogChooseScreenBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialogChooseScreenBinding.cstLockScreen.setOnClickListener(v -> {
            dialogChooseScreenBinding.imgradioLockScreen.setVisibility(View.VISIBLE);
            dialogChooseScreenBinding.imgradioHomeScreen.setVisibility(View.GONE);
            dialogChooseScreenBinding.imgradioBothScreen.setVisibility(View.GONE);
        });

        dialogChooseScreenBinding.cstHomeScreen.setOnClickListener(v -> {
            dialogChooseScreenBinding.imgradioLockScreen.setVisibility(View.GONE);
            dialogChooseScreenBinding.imgradioHomeScreen.setVisibility(View.VISIBLE);
            dialogChooseScreenBinding.imgradioBothScreen.setVisibility(View.GONE);
        });

        dialogChooseScreenBinding.cstBothScreen.setOnClickListener(v -> {
            dialogChooseScreenBinding.imgradioLockScreen.setVisibility(View.GONE);
            dialogChooseScreenBinding.imgradioHomeScreen.setVisibility(View.GONE);
            dialogChooseScreenBinding.imgradioBothScreen.setVisibility(View.VISIBLE);
        });

        dialogChooseScreenBinding.btnSelectOptions.setOnClickListener(v -> {
            int currentItem = binding.viewpage2SetWallpaper.getCurrentItem();
            HdWallpaperModel currentModel = list.get(currentItem);

            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(currentModel.getImageUrl());
            Bitmap bitmap = drawable.getBitmap();
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(SetWallPaperActivity.this);
            try {
                if (dialogChooseScreenBinding.imgradioLockScreen.getVisibility() == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                        Toast.makeText(SetWallPaperActivity.this, R.string.lock_screen_wallpaper_set_successfully, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SetWallPaperActivity.this, ActivityDone.class));
                    } else {
                        Toast.makeText(SetWallPaperActivity.this, "Setting lock screen wallpaper requires Android 7.0 or higher", Toast.LENGTH_SHORT).show();
                    }
                } else if (dialogChooseScreenBinding.imgradioHomeScreen.getVisibility() == View.VISIBLE) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    Toast.makeText(SetWallPaperActivity.this, "Home screen wallpaper set successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SetWallPaperActivity.this, ActivityDone.class));
                } else if (dialogChooseScreenBinding.imgradioBothScreen.getVisibility() == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                        Toast.makeText(SetWallPaperActivity.this, "Both home and lock screen wallpapers set successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SetWallPaperActivity.this, ActivityDone.class));
                    } else {
                        wallpaperManager.setBitmap(bitmap);
                        Toast.makeText(SetWallPaperActivity.this, "Home screen wallpaper set successfully (setting lock screen requires Android 7.0 or higher)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetWallPaperActivity.this, "Vui lòng chọn màn hình", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            } catch (IOException e) {
                Toast.makeText(SetWallPaperActivity.this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
