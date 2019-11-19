package com.shambhu.kisanputra.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.response.detail_address.Datum;
import com.shambhu.kisanputra.ui.activities.Addres_add_Activity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Address_item_Adapter extends RecyclerView.Adapter<Address_item_Adapter.ViewHolder>
{

    @NotNull
    private final Addres_add_Activity activity;
    @NotNull
    private final List<? extends Datum> datalist;
    @NotNull
    private final String token;
    @NotNull
    private  AddressClickedListener mListener;

    public Address_item_Adapter(@NotNull Addres_add_Activity activity, @NotNull List<? extends Datum> datalist, @NotNull String token,
    AddressClickedListener listener)
    {

        this.activity = activity;
        this.datalist = datalist;
        this.token = token;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new Address_item_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.txt_address.setText(datalist.get(position).getAddress());
        holder.img_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.deleteAddress(datalist.get(position),position);

            }
        });
        holder.layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addDefaultAddress(datalist.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_address;
        ImageView img_delete;
        LinearLayout layoutAddress;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txt_address=itemView.findViewById(R.id.txt_address);
            img_delete=itemView.findViewById(R.id.img_delete);
            layoutAddress=itemView.findViewById(R.id.layoutAddress);
        }
    }
    public interface AddressClickedListener{
        void deleteAddress(Datum datum, int position);
        void addDefaultAddress(Datum datum, int position);
    }
}
