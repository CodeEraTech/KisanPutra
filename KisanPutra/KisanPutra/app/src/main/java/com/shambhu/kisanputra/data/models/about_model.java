package com.shambhu.kisanputra.data.models;

public class about_model
{
    boolean expande=false;
    String item_about_name,item_about_detail_1,item_about_detail_2;

    public about_model(boolean expande, String item_about_name, String item_about_detail_1, String item_about_detail_2)
    {
        this.expande = expande;
        this.item_about_name = item_about_name;
        this.item_about_detail_1 = item_about_detail_1;
        this.item_about_detail_2 = item_about_detail_2;
    }

    public boolean isExpande() {
        return expande;
    }

    public void setExpande(boolean expande) {
        this.expande = expande;
    }

    public String getItem_about_name() {
        return item_about_name;
    }

    public void setItem_about_name(String item_about_name) {
        this.item_about_name = item_about_name;
    }

    public String getItem_about_detail_1() {
        return item_about_detail_1;
    }

    public void setItem_about_detail_1(String item_about_detail_1) {
        this.item_about_detail_1 = item_about_detail_1;
    }

    public String getItem_about_detail_2() {
        return item_about_detail_2;
    }

    public void setItem_about_detail_2(String item_about_detail_2) {
        this.item_about_detail_2 = item_about_detail_2;
    }
}
