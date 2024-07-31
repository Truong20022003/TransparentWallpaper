package com.example.wallpaper.ViewModel.BaseViewModel;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivityViewModel<VB extends ViewBinding, V extends ViewModel> extends AppCompatActivity {

    protected VB binding;
    protected V viewModel;

    protected abstract VB createBinding();
    protected abstract V setViewModel();

    protected void initView() {}
    protected void bindView() {}
    protected abstract void viewModel();
    protected void initData() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = createBinding();
        setContentView(binding.getRoot());
        viewModel = setViewModel();

        initView();
        viewModel();
        hideNavigationBar();
    }

    protected void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }


}

