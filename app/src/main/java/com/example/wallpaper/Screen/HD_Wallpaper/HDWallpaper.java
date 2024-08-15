package com.example.wallpaper.Screen.HD_Wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.amazic.ads.callback.AdCallback;
import com.amazic.ads.callback.InterCallback;
import com.amazic.ads.util.Admob;
import com.amazic.ads.util.AdsConsentManager;
import com.amazic.ads.util.AdsSplash;
import com.example.wallpaper.Model.HdWallpaperModel;
import com.example.wallpaper.R;
import com.example.wallpaper.Screen.Home.TransparentWallpaper;
import com.example.wallpaper.Screen.SetWallpaper.SetWallPaperActivity;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.HDWallpaperViewModel;
import com.example.wallpaper.databinding.ActivityHdwallpaperBinding;
import com.google.android.gms.ads.LoadAdError;

import java.util.ArrayList;
import java.util.List;

public class HDWallpaper extends BaseActivityViewModel<ActivityHdwallpaperBinding,HDWallpaperViewModel> {

    public List<HdWallpaperModel> list;
    private HdWallpaperAdapter adapter;
    private int currentPosition = -1;

    @Override
    protected ActivityHdwallpaperBinding createBinding() {
        return ActivityHdwallpaperBinding.inflate(getLayoutInflater());
    }

    @Override
    protected HDWallpaperViewModel setViewModel() {
        return new HDWallpaperViewModel();
    }

    @Override
    protected void viewModel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHdwallpaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
binding.imgBackk.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        navigateTo(TransparentWallpaper.class);
    }
});
        list = new ArrayList<>();

        list.add(new HdWallpaperModel(1, R.drawable.img_content_1));
        list.add(new HdWallpaperModel(2, R.drawable.img_content_4));
        list.add(new HdWallpaperModel(3, R.drawable.img_content_2));
        list.add(new HdWallpaperModel(4, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(5, R.drawable.img_content_3));
        list.add(new HdWallpaperModel(6, R.drawable.img_content_6));
        list.add(new HdWallpaperModel(7, R.drawable.img_content_2));
        list.add(new HdWallpaperModel(8, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(9, R.drawable.img_content_5));
        list.add(new HdWallpaperModel(10, R.drawable.img_content_3));


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rcvHdWallpaper.setLayoutManager(layoutManager);

        adapter = new HdWallpaperAdapter(list, this);
        binding.rcvHdWallpaper.setAdapter(adapter);

        adapter.setOnItemClick(new OnclickItem() {
            @Override
            public void Onclick(int position) {
                navigateToAddPosition(SetWallPaperActivity.class,position);
            }
        });


    }
}