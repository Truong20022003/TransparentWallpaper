package com.example.wallpaper.Screen.Language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.amazic.ads.callback.NativeCallback;

import com.amazic.ads.util.manager.native_ad.NativeBuilder;
import com.amazic.ads.util.manager.native_ad.NativeManager;
import com.example.wallpaper.Model.ModelLanguage;
import com.example.wallpaper.R;
import com.example.wallpaper.Screen.Home.TransparentWallpaper;
import com.example.wallpaper.Until.SharePrefRemote;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.databinding.ActivityLanguageScreenBinding;
import com.example.wallpaper.ViewModel.LanguageViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageScreen extends BaseActivityViewModel<ActivityLanguageScreenBinding, LanguageViewModel> implements AdapterLanguage.OnLanguageSelectedListener {

    private AdapterLanguage adapter;
    private LanguageViewModel languageViewModel;
    private String selectedLanguage;

    private FrameLayout native_ads;
    NativeManager nativeManager;


    @Override
    protected ActivityLanguageScreenBinding createBinding() {
        return ActivityLanguageScreenBinding.inflate(getLayoutInflater());
    }

    @Override
    protected LanguageViewModel setViewModel() {
        return new LanguageViewModel();
    }

    @Override
    protected void viewModel() {
        // Implement logic if needed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        languageViewModel = new LanguageViewModel();

        setupRecyclerView();
        observeViewModel();
        native_ads = binding.nativeAds;



        try {
            if (SharePrefRemote.get_config(this, SharePrefRemote.native_language)) {
                native_ads.setVisibility(View.VISIBLE);
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.admob_native_id));
                NativeBuilder builder = new NativeBuilder(this, native_ads,
                        R.layout.ads_shimmer_language_layout, R.layout.ads_native_language_layout);
                builder.setListIdAd(list);
                nativeManager = new NativeManager(this, this, builder);
//                NativeBuilder builder = new NativeBuilder(this, fr_ads,
//                        R.layout.ads_shimmer_language_layout, R.layout.ads_native_language_layout);
//                builder.setListIdAd( getString(R.string.native_language));
//                 nativeManager = new NativeManager(this, this, builder);

            } else {
                native_ads.removeAllViews();
            }

        } catch (Exception e) {
            e.printStackTrace();
            native_ads.removeAllViews();
            native_ads.setVisibility(View.INVISIBLE);
        }



        binding.btnSaveLanguage.setOnClickListener(v -> {
            if (selectedLanguage != null) {
                languageViewModel.setSelectedLanguage(this,
                        languageViewModel.getLanguages().getValue().stream()
                                .filter(lang -> lang.getIdLanguage().equals(selectedLanguage))
                                .findFirst()
                                .orElse(null)
                );
                languageViewModel.setLocale(this, selectedLanguage);
                restartActivity();
                startActivity(new Intent(this, TransparentWallpaper.class));
            }
        });
    }

    private void setupRecyclerView() {
        languageViewModel.language(this); // Initialize the list of languages
        languageViewModel.getLanguages().observe(this, languages -> {
            setAdapterLanguage(languages, this, this);
        });
    }

    private void observeViewModel() {
        languageViewModel.getSelectedLanguage().observe(this, language -> {
            if (language != null) {
                selectedLanguage = language.getIdLanguage();
            }
        });
    }

    @Override
    public void onLanguageSelected(String language) {
        selectedLanguage = language;
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void setAdapterLanguage(List<ModelLanguage> languages, Context context, AdapterLanguage.OnLanguageSelectedListener listener) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvFrag.setLayoutManager(layoutManager);
        adapter = new AdapterLanguage(languages, context, listener);
        binding.rcvFrag.setAdapter(adapter);
    }
}
