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
import com.shambhu.kisanputra.ui.activities.subscription_History_Activity;

import java.util.ArrayList;

public class subscription_history_Adpater extends RecyclerView.Adapter<subscription_history_Adpater.ViewHolder> {

    private final subscription_History_Activity activity;
    private final ArrayList<My_subscription_Model> My_subscription_list;

    public subscription_history_Adpater(subscription_History_Activity activity, ArrayList<My_subscription_Model> My_subscription_list) {
        this.activity = activity;
        this.My_subscription_list = My_subscription_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subscription_history, parent, false);
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

        holder.btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "btn_Renew Click", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return My_subscription_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_my_subscription;
        Button btn_renew;
        TextView txt_item_name, txt_item_prise, txt_item_qnty;
        TextView txt_subscri_date, txt_subscri_end_date, txt_subscri_duration, txt_subscri_type;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_my_subscription = itemView.findViewById(R.id.img_my_subscription);
            btn_renew = itemView.findViewById(R.id.btn_renew);
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
