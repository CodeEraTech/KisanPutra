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
import com.shambhu.kisanputra.ui.activities.Notification_Activity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Notification_item_Adapter extends RecyclerView.Adapter<Notification_item_Adapter.ViewHolder> {

    private final Notification_Activity notification_activity;
    private final ArrayList<notification_model> notification_list;

    public Notification_item_Adapter(Notification_Activity notification_activity, ArrayList<notification_model> notification_list) {
        this.notification_activity = notification_activity;
        this.notification_list = notification_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notiification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        /*

         CircleImageView profile_image;
        TextView img_close;
        TextView txt_name, txt_date, txt_notification_head1, txt_notification_head2, txt_notification_add;

        */

        Glide.with(notification_activity).load(notification_list.get(position).getImg_url()).into(holder.profile_image);

        holder.txt_name.setText(notification_list.get(position).getName());
        holder.txt_date.setText(notification_list.get(position).getDate());
        holder.txt_notification_head1.setText(notification_list.get(position).getHeading1());
        holder.txt_notification_head2.setText(notification_list.get(position).getHeading2());
        holder.txt_notification_add.setText(notification_list.get(position).getAddress());


        holder.img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_list.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        ImageView img_close;
        TextView txt_name, txt_date, txt_notification_head1, txt_notification_head2, txt_notification_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            img_close = itemView.findViewById(R.id.img_close);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_notification_head1 = itemView.findViewById(R.id.txt_notification_head1);
            txt_notification_head2 = itemView.findViewById(R.id.txt_notification_head2);
            txt_notification_add = itemView.findViewById(R.id.txt_notification_add);

        }
    }
}
