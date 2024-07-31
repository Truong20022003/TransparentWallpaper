package com.example.wallpaper.Screen.SetWallpaper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wallpaper.Model.HdWallpaperModel;
import com.example.wallpaper.databinding.ItemRcvSetwallpaperBinding;

import java.util.List;

public class SetWallViewPager2Adapter extends RecyclerView.Adapter<SetWallViewPager2Adapter.ViewPager2ViewHolder> {

private List<HdWallpaperModel> list;
private ViewPager2 viewPager2;

    public SetWallViewPager2Adapter(List<HdWallpaperModel> list, ViewPager2 viewPager2) {
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewPager2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvSetwallpaperBinding binding = ItemRcvSetwallpaperBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SetWallViewPager2Adapter.ViewPager2ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewPager2ViewHolder holder, int position) {
        holder.binding.imageslide.setImageResource(list.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewPager2ViewHolder extends RecyclerView.ViewHolder {

        ItemRcvSetwallpaperBinding binding;
        public ViewPager2ViewHolder(ItemRcvSetwallpaperBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
