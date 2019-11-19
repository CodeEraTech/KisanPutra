package com.shambhu.kisanputra.data.models;

public class My_subscription_Model
{
    String img_url;
    String item_name,item_qnty,item_prise;
    String item_subscrip_start_date,item_subscrip_end_date,item_subscrip_duration,item_subscrip_type;

    public My_subscription_Model(String img_url, String item_name, String item_qnty, String item_prise, String item_subscrip_start_date, String item_subscrip_end_date, String item_subscrip_duration, String item_subscrip_type) {
        this.img_url = img_url;
        this.item_name = item_name;
        this.item_qnty = item_qnty;
        this.item_prise = item_prise;
        this.item_subscrip_start_date = item_subscrip_start_date;
        this.item_subscrip_end_date = item_subscrip_end_date;
        this.item_subscrip_duration = item_subscrip_duration;
        this.item_subscrip_type = item_subscrip_type;
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

    public String getItem_qnty() {
        return item_qnty;
    }

    public void setItem_qnty(String item_qnty) {
        this.item_qnty = item_qnty;
    }

    public String getItem_prise() {
        return item_prise;
    }

    public void setItem_prise(String item_prise) {
        this.item_prise = item_prise;
    }

    public String getItem_subscrip_start_date() {
        return item_subscrip_start_date;
    }

    public void setItem_subscrip_start_date(String item_subscrip_start_date) {
        this.item_subscrip_start_date = item_subscrip_start_date;
    }

    public String getItem_subscrip_end_date() {
        return item_subscrip_end_date;
    }

    public void setItem_subscrip_end_date(String item_subscrip_end_date) {
        this.item_subscrip_end_date = item_subscrip_end_date;
    }

    public String getItem_subscrip_duration() {
        return item_subscrip_duration;
    }

    public void setItem_subscrip_duration(String item_subscrip_duration) {
        this.item_subscrip_duration = item_subscrip_duration;
    }

    public String getItem_subscrip_type() {
        return item_subscrip_type;
    }

    public void setItem_subscrip_type(String item_subscrip_type) {
        this.item_subscrip_type = item_subscrip_type;
    }
}
