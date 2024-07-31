package com.example.wallpaper.Screen.Language;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaper.Model.ModelLanguage;
import com.example.wallpaper.R;
import com.example.wallpaper.databinding.ItemLanguagescreenBinding;

import java.util.List;

public class AdapterLanguage extends RecyclerView.Adapter<AdapterLanguage.ViewHolder> {
    private final List<ModelLanguage> list;
    private final Context context;
    private int selectedPosition = -1;
    private OnLanguageSelectedListener listener;

    public AdapterLanguage(List<ModelLanguage> list, Context context, OnLanguageSelectedListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguagescreenBinding binding = ItemLanguagescreenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.imgFrag.setImageResource(list.get(position).getFrag());
        holder.binding.txtCountry.setText(list.get(position).getCountryName());
        holder.binding.rdoButtton.setChecked(list.get(position).isStatus());
        holder.binding.getRoot().setOnClickListener(v -> updateSelection(position));
        holder.binding.rdoButtton.setOnClickListener(v -> updateSelection(position));
        if (list.get(position).isStatus()) {
            Drawable selectedDrawable = ContextCompat.getDrawable(context, R.drawable.custom_item_frag_selected);
            holder.itemView.setBackground(selectedDrawable);
        } else {
            Drawable defaultDrawable = ContextCompat.getDrawable(context, R.drawable.custom_item_frag_unselected);
            holder.itemView.setBackground(defaultDrawable);
        }
    }

    private void updateSelection(int position) {
        if (selectedPosition != -1) {
            list.get(selectedPosition).setStatus(false);
        }
        list.get(position).setStatus(true);
        selectedPosition = position;
        listener.onLanguageSelected(list.get(position).getIdLanguage());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLanguagescreenBinding binding;

        public ViewHolder(ItemLanguagescreenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnLanguageSelectedListener {
        void onLanguageSelected(String language);
    }
}
