package com.shambhu.kisanputra.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.about_model;
import com.shambhu.kisanputra.ui.activities.Detail_product_Activity;

import java.util.ArrayList;

public class about_item_detail_Adapter extends RecyclerView.Adapter<about_item_detail_Adapter.ViewHolder> {

    private final Detail_product_Activity detail_product_activity;
    private final ArrayList<about_model> detail_list;

    public about_item_detail_Adapter(Detail_product_Activity detail_product_activity, ArrayList<about_model> detail_list) {
        this.detail_product_activity = detail_product_activity;
        this.detail_list = detail_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_about_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if(detail_list.get(position).isExpande())
        {
            holder.img_minus.setVisibility(View.VISIBLE);
            holder.img_pus.setVisibility(View.GONE);
        }else
            {
            holder.img_minus.setVisibility(View.GONE);
            holder.img_pus.setVisibility(View.VISIBLE);
        }

        holder.txt_about_product_name.setText(detail_list.get(position).getItem_about_name());
        holder.txt_about_product_detail_1.setText(detail_list.get(position).getItem_about_detail_1());
        holder.txt_about_product_detail_2.setText(detail_list.get(position).getItem_about_detail_2());

        holder.img_pus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.txt_about_product_detail_1.setMaxLines(Integer.MAX_VALUE);
                holder.txt_about_product_detail_2.setMaxLines(Integer.MAX_VALUE);
                detail_list.get(position).setExpande(true);
                notifyItemChanged(position);
            }
        });
        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txt_about_product_detail_1.setMaxLines(2);
                holder.txt_about_product_detail_2.setMaxLines(2);
                detail_list.get(position).setExpande(false);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return detail_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView img_pus,img_minus;
        TextView txt_about_product_name;
        TextView txt_about_product_detail_1;
        TextView txt_about_product_detail_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pus = itemView.findViewById(R.id.img_pus);
            img_minus = itemView.findViewById(R.id.img_minus);
            txt_about_product_name = itemView.findViewById(R.id.txt_about_product_name);
            txt_about_product_detail_1 = itemView.findViewById(R.id.txt_about_product_detail_1);
            txt_about_product_detail_2 = itemView.findViewById(R.id.txt_about_product_detail_2);
        }
    }
}
