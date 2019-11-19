package com.shambhu.kisanputra.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.notification_model;
import com.shambhu.kisanputra.data.models.response.detail_product.Image;
import com.shambhu.kisanputra.ui.activities.Detail_product_Activity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class about_item_img_Adapter extends RecyclerView.Adapter<about_item_img_Adapter.ViewHolder> {

    private final Detail_product_Activity detail_product_activity;
    private final List<Image> img_list;

    public about_item_img_Adapter(Detail_product_Activity detail_product_activity, List<Image> img_list) {
        this.detail_product_activity = detail_product_activity;
        this.img_list = img_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Glide.with(detail_product_activity).load(img_list.get(position).getUrl()).into(holder.img_item_pic);
    }

    @Override
    public int getItemCount() {
        return img_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_item_pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_pic = itemView.findViewById(R.id.img_item_pic);
        }
    }
}
