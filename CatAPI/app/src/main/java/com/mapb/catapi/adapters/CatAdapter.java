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
import com.mapb.catapi.models.CatGetModel;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {
    public OnItemClickListener onItemClickListener;
    ArrayList<CatGetModel> catList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView catImage;
        public Button favButton;

        public MyViewHolder(View parent) {
            super(parent);
            catImage = parent.findViewById(R.id.image_view_cat_item);
            favButton = parent.findViewById(R.id.button_favorite_cat_item);
        }
    }

    public CatAdapter(ArrayList<CatGetModel> catList) {
        this.catList = catList;
    }

    @NonNull
    @Override
    public CatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(CatAdapter.MyViewHolder holder, int position) {

        CatGetModel catImage = catList.get(holder.getAdapterPosition());
        Glide.with(holder.itemView.getContext())
                .load(catImage.getUrl())
                .into(holder.catImage);
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // obtenemos la posicion del item
                    onItemClickListener.onItemClick(v, catList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, CatGetModel cat, int posicion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}