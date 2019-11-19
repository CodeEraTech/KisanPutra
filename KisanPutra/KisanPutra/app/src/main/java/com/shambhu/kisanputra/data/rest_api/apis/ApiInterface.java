package com.shambhu.kisanputra.data.rest_api.apis;

import com.shambhu.kisanputra.data.models.response.Add_Cart_Response;
import com.shambhu.kisanputra.data.models.response.Delete_Cart;
import com.shambhu.kisanputra.data.models.response.defaultAddress.GetDefaultAddress;
import com.shambhu.kisanputra.data.models.response.defaultAddress.SetDefaultAddress;
import com.shambhu.kisanputra.data.models.response.detail_Add_address.DetailAddAddress;
import com.shambhu.kisanputra.data.models.response.detail_address.DetailAddress;
import com.shambhu.kisanputra.data.models.response.detail_product.DetailProduct;
import com.shambhu.kisanputra.data.models.response.getCart_list_model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface
{

    @GET("api/cartlist")
    Call<getCart_list_model> getcartdata(@Header("Authorization")  String token);

    @GET("api/product_detail/{id}")
    Call<DetailProduct> detailproduct(@Header("Authorization")  String token,@Path("id") int id);

    
    @POST("api/addtocart")
    @FormUrlEncoded
    Call<Add_Cart_Response> addtocart(@Header("Authorization")  String token,@FieldMap Map<String,String> params);

    @POST("api/updateCart")
    @FormUrlEncoded
    Call<Add_Cart_Response> updatecart(@Header("Authorization")  String token,@FieldMap Map<String,String> params);

    @POST("api/removeProductCart")
    @FormUrlEncoded
    Call<Delete_Cart> removeProductCart(@Header("Authorization")  String token, @FieldMap Map<String,String> params);

    @POST("api/add_Address")
    @FormUrlEncoded
    Call<DetailAddAddress> add_Address(@Header("Authorization")  String token, @FieldMap Map<String,String> params);

    @POST("api/setDefaultAddress")
    @FormUrlEncoded
    Call<SetDefaultAddress> setDefaultAddress(@Header("Authorization")  String token, @FieldMap Map<String,String> params);

    @GET("api/getAllAddresses")
    Call<DetailAddress> getAllAddresses(@Header("Authorization")  String token);

    @GET("api/getDefaultAddress")
    Call<GetDefaultAddress> getDefaultAddresses(@Header("Authorization")  String token);
}
