package com.mapb.catapi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mapb.catapi.R;
import com.mapb.catapi.models.FavoriteCatGetModel;

import java.util.ArrayList;

public class FavoriteCatAdapter extends RecyclerView.Adapter<FavoriteCatAdapter.MyViewHolder> {

    public OnItemClickListener onItemClickListener;
    ArrayList<FavoriteCatGetModel> favoriteCats;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView favoriteCatImage;
        public Button deleteFavoriteCatButton;

        public MyViewHolder(View parent) {
            super(parent);
            favoriteCatImage = parent.findViewById(R.id.image_view_favorite_cat_item);
            deleteFavoriteCatButton = parent.findViewById(R.id.button_delete_favorite_cat_item);

        }
    }

    public FavoriteCatAdapter(ArrayList<FavoriteCatGetModel> favoriteCats) {
        this.favoriteCats = favoriteCats;
    }

    @NonNull
    @Override
    public FavoriteCatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_cat_item, parent, false);
        FavoriteCatAdapter.MyViewHolder mvh = new FavoriteCatAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(FavoriteCatAdapter.MyViewHolder holder, int position) {
        FavoriteCatGetModel catImage = favoriteCats.get(holder.getAdapterPosition());
        Glide.with(holder.itemView.getContext())
                .load(catImage.getImage().getUrl())
                .into(holder.favoriteCatImage);
        holder.deleteFavoriteCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, favoriteCats.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteCats.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, FavoriteCatGetModel cat, int posicion);
    }

    public void setOnItemClickListener(FavoriteCatAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}

