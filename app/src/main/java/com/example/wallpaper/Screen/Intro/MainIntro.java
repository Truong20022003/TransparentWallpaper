package com.example.wallpaper.Screen.Intro;

import android.content.Intent;
import android.os.Bundle;

import com.example.wallpaper.R;
import com.example.wallpaper.Model.ModelIntro;
import com.example.wallpaper.Screen.Language.LanguageScreen;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseActivityViewModel;
import com.example.wallpaper.ViewModel.IntroViewModel;
import com.example.wallpaper.databinding.ActivityMainIntroBinding;

import java.util.ArrayList;
import java.util.List;

public class MainIntro extends BaseActivityViewModel<ActivityMainIntroBinding, IntroViewModel> {

    ActivityMainIntroBinding binding;
    private List<ModelIntro> listItemSlide;

    @Override
    protected ActivityMainIntroBinding createBinding() {
        return ActivityMainIntroBinding.inflate(getLayoutInflater());
    }

    @Override
    protected IntroViewModel setViewModel() {
        return new IntroViewModel();
    }

    @Override
    protected void viewModel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listItemSlide = new ArrayList<>();
        listItemSlide.add(new ModelIntro(R.drawable.img_intro1, R.string.transparency, R.string.reveal_your_screens_beauty));
        listItemSlide.add(new ModelIntro(R.drawable.img_intro2, R.string.crystal, R.string.clarity_and_style));
        listItemSlide.add(new ModelIntro(R.drawable.img_intro3, R.string.clearvision, R.string.elevate_your_screens_allure));
        binding.viewpageIntro.setAdapter(new AdapterSlideIntro(listItemSlide, binding.viewpageIntro, this));
        binding.dotsIndicator.attachTo(binding.viewpageIntro);
        binding.txtNext.setOnClickListener(v -> {
            int currentItem = binding.viewpageIntro.getCurrentItem();
            if (currentItem < listItemSlide.size() - 1) {
                binding.viewpageIntro.setCurrentItem(currentItem + 1);
            } else {
                startActivity(new Intent(this, LanguageScreen.class));
            }
        });

    }
}