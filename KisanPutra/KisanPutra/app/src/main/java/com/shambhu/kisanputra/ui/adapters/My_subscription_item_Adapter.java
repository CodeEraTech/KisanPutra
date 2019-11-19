package com.shambhu.kisanputra.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.My_subscription_Model;
import com.shambhu.kisanputra.ui.activities.My_subscription_Activity;

import java.util.ArrayList;

public class My_subscription_item_Adapter extends RecyclerView.Adapter<My_subscription_item_Adapter.ViewHolder> {

    private final My_subscription_Activity activity;
    private final ArrayList<My_subscription_Model> My_subscription_list;

    public My_subscription_item_Adapter(My_subscription_Activity activity, ArrayList<My_subscription_Model> My_subscription_list) {
        this.activity = activity;
        this.My_subscription_list = My_subscription_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_subscription, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*
        ImageView img_my_subscription;
        Button btn_subscrip_pause,btn_subscrip_end;
        TextView txt_item_name,txt_item_prise,txt_item_qnty;
        TextView txt_subscri_date,txt_subscri_end_date,txt_subscri_duration,txt_subscri_type;
         */


        Glide.with(activity).load(My_subscription_list.get(position).getImg_url()).into(holder.img_my_subscription);

        holder.txt_item_name.setText(My_subscription_list.get(position).getItem_name());
        holder.txt_item_prise.setText(My_subscription_list.get(position).getItem_prise());
        holder.txt_item_qnty.setText(My_subscription_list.get(position).getItem_qnty());
        holder.txt_subscri_date.setText(My_subscription_list.get(position).getItem_subscrip_start_date());
        holder.txt_subscri_end_date.setText(My_subscription_list.get(position).getItem_subscrip_end_date());
        holder.txt_subscri_duration.setText(My_subscription_list.get(position).getItem_subscrip_duration());
        holder.txt_subscri_type.setText(My_subscription_list.get(position).getItem_subscrip_type());

        holder.btn_subscrip_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "btn_subscrip_end Click", Toast.LENGTH_SHORT).show();

            }
        });
        holder.btn_subscrip_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "btn_subscrip_pause Click", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return My_subscription_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_my_subscription;
        Button btn_subscrip_pause, btn_subscrip_end;
        TextView txt_item_name, txt_item_prise, txt_item_qnty;
        TextView txt_subscri_date, txt_subscri_end_date, txt_subscri_duration, txt_subscri_type;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_my_subscription = itemView.findViewById(R.id.img_my_subscription);
            btn_subscrip_pause = itemView.findViewById(R.id.btn_subscrip_pause);
            btn_subscrip_end = itemView.findViewById(R.id.btn_subscrip_end);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_prise = itemView.findViewById(R.id.txt_item_prise);
            txt_item_qnty = itemView.findViewById(R.id.txt_item_qnty);
            txt_subscri_date = itemView.findViewById(R.id.txt_subscri_date);
            txt_subscri_end_date = itemView.findViewById(R.id.txt_subscri_end_date);
            txt_subscri_duration = itemView.findViewById(R.id.txt_subscri_duration);
            txt_subscri_type = itemView.findViewById(R.id.txt_subscri_type);


        }
    }
}
