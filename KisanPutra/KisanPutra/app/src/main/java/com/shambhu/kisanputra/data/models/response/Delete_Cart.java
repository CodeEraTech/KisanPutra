package com.shambhu.kisanputra.data.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Delete_Cart {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("data")
    @Expose
    private Integer data;
    @SerializedName("cart_subtotal")
    @Expose
    private Integer cartSubtotal;
    @SerializedName("applied_coupons")
    @Expose
    private String appliedCoupons;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("cart_total")
    @Expose
    private Integer cartTotal;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Integer getCartSubtotal() {
        return cartSubtotal;
    }

    public void setCartSubtotal(Integer cartSubtotal) {
        this.cartSubtotal = cartSubtotal;
    }

    public String getAppliedCoupons() {
        return appliedCoupons;
    }

    public void setAppliedCoupons(String appliedCoupons) {
        this.appliedCoupons = appliedCoupons;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Integer cartTotal) {
        this.cartTotal = cartTotal;
    }

}
