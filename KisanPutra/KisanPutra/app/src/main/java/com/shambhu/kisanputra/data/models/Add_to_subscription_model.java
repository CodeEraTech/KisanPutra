package com.shambhu.kisanputra.data.models;

public class Add_to_subscription_model
{
    String img_url;
    String item_name,item_prise,item_qnty,item_count,item_subscri_date;

    public Add_to_subscription_model(String img_url,
                                     String item_name,
                                     String item_prise,
                                     String item_qnty,
                                     String item_count,
                                     String item_subscri_date) {
        this.img_url = img_url;
        this.item_name = item_name;
        this.item_prise = item_prise;
        this.item_qnty = item_qnty;
        this.item_count = item_count;
        this.item_subscri_date = item_subscri_date;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_prise() {
        return item_prise;
    }

    public void setItem_prise(String item_prise) {
        this.item_prise = item_prise;
    }

    public String getItem_qnty() {
        return item_qnty;
    }

    public void setItem_qnty(String item_qnty) {
        this.item_qnty = item_qnty;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public String getItem_subscri_date() {
        return item_subscri_date;
    }

    public void setItem_subscri_date(String item_subscri_date) {
        this.item_subscri_date = item_subscri_date;
    }
}
