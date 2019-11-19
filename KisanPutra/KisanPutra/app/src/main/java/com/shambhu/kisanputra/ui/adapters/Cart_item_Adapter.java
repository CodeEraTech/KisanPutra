package com.shambhu.kisanputra.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.Cart_item_Model;
import com.shambhu.kisanputra.data.models.response.Add_Cart_Response;
import com.shambhu.kisanputra.data.models.response.Datum;
import com.shambhu.kisanputra.data.models.response.Delete_Cart;
import com.shambhu.kisanputra.data.models.response.getCart_list_model;
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient;
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface;
import com.shambhu.kisanputra.data.temp.temp_data;
import com.shambhu.kisanputra.ui.activities.Add_to_Cart_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Cart_item_Adapter extends RecyclerView.Adapter<Cart_item_Adapter.ViewHolder>
{

    private final Add_to_Cart_Activity add_to_cart_activity;
    private List<Datum> cart_item_list;
    //    private final ArrayList<Cart_item_Model> cart_item_list;
    private  int cartTotal;
    private String token;
    private TextView txt_total_rupis;


    //    public Cart_item_Adapter(Add_to_Cart_Activity add_to_cart_activity, ArrayList<Cart_item_Model> cart_item_list)
//    {
//        this.add_to_cart_activity = add_to_cart_activity;
//        this.cart_item_list = cart_item_list;
//    }
    public Cart_item_Adapter(Add_to_Cart_Activity add_to_cart_activity,
                             List<Datum> cart_item_list,
                             int cartTotal,
                             String token,TextView txt_total_rupis)
    {
        this.add_to_cart_activity = add_to_cart_activity;
        this.cart_item_list = cart_item_list;
        this.cartTotal = cartTotal;
        this.token = token;
        this.txt_total_rupis = txt_total_rupis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
//        Glide.with(add_to_cart_activity).load(cart_item_list.get(position).getItem_img_url()).into(holder.img_item_image);
        holder.txt_item_name.setText(cart_item_list.get(position).getProductName());
        holder.txt_item_prise.setText(""+"\u20B9"+" "+cart_item_list.get(position).getPrice());
        holder.txt_count_item.setText(""+cart_item_list.get(position).getQuantity());

        holder.img_incre_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int qty= Integer.parseInt(cart_item_list.get(position).getQuantity().toString());
                qty=qty+1;


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Map<String, String> params = new HashMap<>();
                params.put("product_id", String.valueOf(cart_item_list.get(position).getProductId()));
                params.put("quantity", String.valueOf(qty));

                Call<Add_Cart_Response> call = apiService.addtocart(token,params);
                call.enqueue(new Callback<Add_Cart_Response>()
                {

                    @Override
                    public void onResponse(Call<Add_Cart_Response> call, retrofit2.Response<Add_Cart_Response> response)
                    {
//                        String msg = response.body().getMessage();
                        notifyDataSetChanged();

                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                        Call<getCart_list_model> call2 = apiService.getcartdata(token);
                        call2.enqueue(new Callback<getCart_list_model>()
                        {

                            @Override
                            public void onResponse(Call<getCart_list_model> call, retrofit2.Response<getCart_list_model> response)
                            {
//                        String msg = response.body().getMessage();
                                cart_item_list = response.body().getData();
                                txt_total_rupis.setText( add_to_cart_activity.getString(R.string.Rs) + response.body().getCartTotal());
                                holder.txt_count_item.setText(""+cart_item_list.get(position).getQuantity());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<getCart_list_model>call, Throwable t) {
                                // Log error here since request failed
                                Toast.makeText(add_to_cart_activity, "Fail", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Add_Cart_Response>call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(add_to_cart_activity, "Fail", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        holder.img_dic_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cart_item_list.get(position).getQuantity()>1)
                {
                    int qty= Integer.parseInt(cart_item_list.get(position).getQuantity().toString());
                    qty=qty-1;


                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Map<String, String> params = new HashMap<>();
                    params.put("product_id", String.valueOf(cart_item_list.get(position).getProductId()));
                    params.put("quantity", String.valueOf(qty));

                    Call<Add_Cart_Response> call = apiService.addtocart(token,params);
                    call.enqueue(new Callback<Add_Cart_Response>()
                    {

                        @Override
                        public void onResponse(Call<Add_Cart_Response> call, retrofit2.Response<Add_Cart_Response> response)
                        {
//                        String msg = response.body().getMessage();
                            notifyDataSetChanged();

                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                            Call<getCart_list_model> call2 = apiService.getcartdata(token);
                            call2.enqueue(new Callback<getCart_list_model>()
                            {

                                @Override
                                public void onResponse(Call<getCart_list_model> call, retrofit2.Response<getCart_list_model> response)
                                {
//                        String msg = response.body().getMessage();
                                    cart_item_list = response.body().getData();
                                    txt_total_rupis.setText( add_to_cart_activity.getString(R.string.Rs) + response.body().getCartTotal());
                                    holder.txt_count_item.setText(""+cart_item_list.get(position).getQuantity());
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<getCart_list_model>call, Throwable t) {
                                    // Log error here since request failed
                                    Toast.makeText(add_to_cart_activity, "Fail", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<Add_Cart_Response>call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(add_to_cart_activity, "Fail", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cart_item_list.get(position).getQuantity()>0)
                {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Map<String, String> params = new HashMap<>();
                    params.put("cart_key", String.valueOf(cart_item_list.get(position).getCartKey()));

                    Call<Delete_Cart> call = apiService.removeProductCart(token,params);
                    call.enqueue(new Callback<Delete_Cart>()
                    {

                        @Override
                        public void onResponse(Call<Delete_Cart> call, retrofit2.Response<Delete_Cart> response)
                        {
                            Boolean status = response.body().getStatus();
                            if (status){
                                notifyDataSetChanged();

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                                Call<getCart_list_model> call2 = apiService.getcartdata(token);
                                call2.enqueue(new Callback<getCart_list_model>()
                                {

                                    @Override
                                    public void onResponse(Call<getCart_list_model> call, retrofit2.Response<getCart_list_model> response)
                                    {
//                        String msg = response.body().getMessage();
                                        cart_item_list = response.body().getData();
                                        txt_total_rupis.setText( add_to_cart_activity.getString(R.string.Rs) + response.body().getCartTotal());
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<getCart_list_model>call, Throwable t) {
                                        // Log error here since request failed
                                        Toast.makeText(add_to_cart_activity, "Fail", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Delete_Cart>call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(add_to_cart_activity, "Fail", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cart_item_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_item_image,img_dic_item,img_incre_item;
        TextView txt_item_name,txt_item_prise,txt_count_item;
        ImageView img_delete;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            img_item_image=itemView.findViewById(R.id.img_item_image);
            img_dic_item=itemView.findViewById(R.id.img_dic_item);
            img_incre_item=itemView.findViewById(R.id.img_incre_item);
            img_delete=itemView.findViewById(R.id.img_delete);

            txt_item_name=itemView.findViewById(R.id.txt_item_name);
            txt_item_prise=itemView.findViewById(R.id.txt_item_prise);
            txt_count_item=itemView.findViewById(R.id.txt_count_item);

        }
    }
}
