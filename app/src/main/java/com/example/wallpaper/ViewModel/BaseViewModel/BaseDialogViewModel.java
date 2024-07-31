package com.example.wallpaper.ViewModel.BaseViewModel;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.viewbinding.ViewBinding;

import com.example.wallpaper.R;

public abstract class BaseDialogViewModel<T extends ViewBinding> extends Dialog  {



        protected T bindingNew;

        public BaseDialogViewModel(Context context) {
            super(context, R.style.Base_Theme_Wallpaper);
        }

        private View getInflatedLayoutNew(LayoutInflater inflater) {
            bindingNew = setBindingNew(inflater);
            return bindingNew.getRoot();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getInflatedLayoutNew(getLayoutInflater()));
        }

        protected abstract T setBindingNew(LayoutInflater layoutInflater);
    }


