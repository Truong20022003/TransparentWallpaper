package com.example.wallpaper.Screen.HD_Wallpaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaper.Model.HdWallpaperModel;
import com.example.wallpaper.databinding.ItemRcvHdwallpaperBinding;

import java.util.List;

public class HdWallpaperAdapter extends RecyclerView.Adapter<HdWallpaperAdapter.ViewHolder> {

    private final List<HdWallpaperModel> list;
    private final Context context;

    public HdWallpaperAdapter(List<HdWallpaperModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private OnclickItem mListener;

    public void setOnItemClick(OnclickItem listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvHdwallpaperBinding binding = ItemRcvHdwallpaperBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HdWallpaperAdapter.ViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.imgHdwallpaper.setImageResource(list.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.Onclick(holder.getAdapterPosition());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRcvHdwallpaperBinding binding;

        public ViewHolder(ItemRcvHdwallpaperBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
