package com.example.wallpaper.Screen.Settings.Language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wallpaper.Screen.Home.TransparentWallpaper;
import com.example.wallpaper.Screen.Language.AdapterLanguage;
import com.example.wallpaper.Model.ModelLanguage;
import com.example.wallpaper.ViewModel.LanguageViewModel;
import com.example.wallpaper.databinding.ActivityLanguageSettingsScreenBinding;

import java.util.List;

public class LanguageSettingsScreen extends AppCompatActivity implements AdapterLanguage.OnLanguageSelectedListener{

    private ActivityLanguageSettingsScreenBinding binding;
    private AdapterLanguage adapter;
    private LanguageViewModel languageViewModel;
    private String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageSettingsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        languageViewModel = new LanguageViewModel();

        setupRecyclerView();
        observeViewModel();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

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