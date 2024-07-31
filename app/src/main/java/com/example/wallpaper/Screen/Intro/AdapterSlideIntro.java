package com.example.wallpaper.Screen.Intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wallpaper.Model.ModelIntro;
import com.example.wallpaper.databinding.ItemSlideIntroBinding;

import java.util.List;

public class AdapterSlideIntro extends RecyclerView.Adapter<AdapterSlideIntro.Viewhodel> {
    private List<ModelIntro> listSlideItems;
    private ViewPager2 viewPager2;
    private Context context;

    public AdapterSlideIntro(List<ModelIntro> slideItems, ViewPager2 viewPager2, Context context) {
        this.listSlideItems = slideItems;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSlideIntroBinding binding = ItemSlideIntroBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewhodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodel holder, int position) {
        ModelIntro slideitem = listSlideItems.get(position);
        if (slideitem == null) {
            return;
        }
        holder.binding.img.setImageResource(slideitem.getImage());
        holder.binding.text1.setText(context.getString(slideitem.getText1()));
        holder.binding.text2.setText(context.getString(slideitem.getText2()));

    }

    @Override
    public int getItemCount() {
        if (listSlideItems != null) {
            return listSlideItems.size();
        }
        return 0;
    }

    public class Viewhodel extends RecyclerView.ViewHolder {
        ItemSlideIntroBinding binding;

        public Viewhodel(ItemSlideIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
