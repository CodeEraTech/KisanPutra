package com.shambhu.kisanputra.ui.adapters;

import android.content.Intent;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.models.Add_to_subscription_model;
import com.shambhu.kisanputra.data.models.Cart_item_Model;
import com.shambhu.kisanputra.data.models.response.Add_Cart_Response;
import com.shambhu.kisanputra.data.models.response.HomeProductResponse;
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient;
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface;
import com.shambhu.kisanputra.data.rest_api.v_api;
import com.shambhu.kisanputra.data.temp.temp_data;
import com.shambhu.kisanputra.ui.activities.Add_subscription_Activity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class essential_item_Adapter extends RecyclerView.Adapter<essential_item_Adapter.ViewHolder> {


    @Nullable
    private FragmentActivity activity;
    @NotNull
    private ArrayList<HomeProductResponse.Others.Essential.EssentialData> list;
    private String token;

    public essential_item_Adapter(@Nullable FragmentActivity activity,
                                  @NotNull ArrayList<HomeProductResponse.Others.Essential.EssentialData> list,
                                  String token) {

        this.activity = activity;
        this.list = list;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.essentail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            Glide.with(activity).load(list.get(position).getImage().get(0).getUrl()).into(holder.img_ese_images);
            holder.txt_name.setText(" " + list.get(position).getName());
            holder.txt_qnty.setText(" " + list.get(position).getQty());
            holder.txt_mrp.setText(" " + list.get(position).getPrice());

        } catch (Exception e) {
            Toast.makeText(activity, "" + e, Toast.LENGTH_SHORT).show();
        }
        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

//                StringRequest request = new StringRequest(Request.Method.POST, v_api.add_to_cart, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (!response.equals(null)) {
//                            try {
//                                JSONObject obj = new JSONObject(response);
//                                if (obj.has("status"))
//                                {
//                                    String status = obj.getString("status");
//                                    if (status.equals("true"))
//                                    {
//                                        temp_data.Cart_item_list.add(new Cart_item_Model(list.get(position).getName(),
//                                                " " + list.get(position).getPrice()
//                                                , "" + list.get(position).getImage().get(0).getUrl(),
//                                                1));
//                                        Toast.makeText(activity, " Item Added to cart..", Toast.LENGTH_SHORT).show();
//                                    }else
//                                        {
//                                            Toast.makeText(activity, " Item not Added to cart..", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            Log.e("Your Array Response", response);
//                        } else {
//                            Log.e("Your Array Response", "Data Null");
//                            Toast.makeText(activity, " something wrong ..", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("error is ", "" + error);
//                    }
//                }) {
//
//                    //This is for Headers If You Needed
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
////                        params.put("Content-Type", "application/json; charset=utf-8");
//                        params.put("Content-Type", "multipart/form-data;boundary=" + "ANY_STRING");
//                        params.put("Authorization", "Bearer " + token);
//                        return params;
//                    }
//
//                    //                    //Pass Your Parameters here
////                    @Override
////                    protected Map<String, String> getParams() {
////                        Map<String, String> params = new HashMap<String, String>();
////                        params.put("product_id", list.get(position).getCatid());
////                        params.put("quantity", "1");
////                        return params;
////                    }
//                    @Override
//                    public byte[] getBody() {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("product_id", list.get(position).getCatid());
//                        params.put("quantity", "1");
//                        String postBody = createPostBody(params);
//                        return postBody.getBytes();
//                    }
//                };
//                RequestQueue queue = Volley.newRequestQueue(activity);
//                queue.add(request);

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Map<String, String> params = new HashMap<>();
                params.put("product_id", list.get(position).getCatid());
                params.put("quantity", "1");

                Call<Add_Cart_Response> call = apiService.addtocart("Bearer " + token,params);
                call.enqueue(new Callback<Add_Cart_Response>()
                {

                    @Override
                    public void onResponse(Call<Add_Cart_Response> call, retrofit2.Response<Add_Cart_Response> response)
                    {
                        String msg = response.body().getMessage();
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Add_Cart_Response>call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(activity, "Fail", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        holder.btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String todayString = formatter.format(todayDate);
//                Toast.makeText(activity, "btn subscribe  click", Toast.LENGTH_SHORT).show();
                temp_data.subsription_item_list = new ArrayList<>();
                temp_data.subsription_item_list.add(new Add_to_subscription_model(list.get(position).getImage().get(0).getUrl(),
                        list.get(position).getName(),
                        list.get(position).getPrice().toString(),
                        list.get(position).getQty(),
                        "1", todayString));

                Intent intent = new Intent(activity, Add_subscription_Activity.class);
                activity.startActivity(intent);

            }
        });
    }

    private String createPostBody(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                sb.append("\r\n" + "--" + "ANY_STRING" + "\r\n");
                sb.append("Content-Disposition: form-data; name=\""
                        + key + "\"" + "\r\n\r\n");
                sb.append(params.get(key));
            }
        }

        return sb.toString();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn_add_to_cart, btn_subscribe;
        ImageView img_ese_images;
        TextView txt_name, txt_qnty, txt_mrp;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_add_to_cart=itemView.findViewById(R.id.btn_add_to_cart);
            btn_subscribe=itemView.findViewById(R.id.btn_subscribe);
            img_ese_images=itemView.findViewById(R.id.img_ese_images);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_qnty=itemView.findViewById(R.id.txt_qnty);
            txt_mrp=itemView.findViewById(R.id.txt_mrp);

        }
    }
}
