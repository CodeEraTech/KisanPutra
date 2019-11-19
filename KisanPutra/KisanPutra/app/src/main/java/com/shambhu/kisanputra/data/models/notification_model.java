package com.shambhu.kisanputra.data.models;

public class notification_model
{
    String img_url;
    String name,date,heading1,heading2,address;

    public notification_model(String img_url, String name, String date, String heading1, String heading2, String address) {
        this.img_url = img_url;
        this.name = name;
        this.date = date;
        this.heading1 = heading1;
        this.heading2 = heading2;
        this.address = address;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeading1() {
        return heading1;
    }

    public void setHeading1(String heading1) {
        this.heading1 = heading1;
    }

    public String getHeading2() {
        return heading2;
    }

    public void setHeading2(String heading2) {
        this.heading2 = heading2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
