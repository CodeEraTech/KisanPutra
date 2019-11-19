package com.shambhu.kisanputra.data.models;

public class Cart_item_Model
{
    String item_name,iten_prise;
    String item_img_url;
    int item_count;

    public Cart_item_Model(String item_name, String iten_prise, String item_img_url, int item_count) {
        this.item_name = item_name;
        this.iten_prise = iten_prise;
        this.item_img_url = item_img_url;
        this.item_count = item_count;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getIten_prise() {
        return iten_prise;
    }

    public void setIten_prise(String iten_prise) {
        this.iten_prise = iten_prise;
    }

    public String getItem_img_url() {
        return item_img_url;
    }

    public void setItem_img_url(String item_img_url) {
        this.item_img_url = item_img_url;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }
}
