package com.example.wallpaper.Screen.Home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.wallpaper.R;
import com.example.wallpaper.databinding.DialogRateBinding;

public class RatingDialog  extends  Dialog {

    private OnPress onPress;
    private int s = 5;
    private final DialogRateBinding binding;

    @SuppressLint("ResourceType")
    public RatingDialog(Context context) {
        super(context, R.style.Base_Theme_Wallpaper);
        binding = DialogRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(attributes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding.ratingBar.setRating(5.0f);

        onclick();
        changeRating();
    }

    public interface OnPress {
        void send(int s);
        void rating(int s);
        void cancel();
        void later();
        void gotIt();
    }

    public void init(OnPress onPress) {
        this.onPress = onPress;
    }

    private void changeRating() {
        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                s = (int) rating;
            }
        });
    }

    private void onclick() {
        binding.btnRate.setOnClickListener(v -> {
            if (binding.ratingBar.getRating() == 0f) {
                Toast.makeText(
                        getContext(),
                        getContext().getResources().getString(R.string.Rate),
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            if (binding.ratingBar.getRating() <= 3.0) {
                if (onPress != null) {
                    onPress.send(s);
                }
            } else {
                if (onPress != null) {
                    onPress.rating(s);
                }
            }
        });

        binding.imgClose.setOnClickListener(v -> {
            if (onPress != null) {
                onPress.cancel();
            }
        });
    }
}
