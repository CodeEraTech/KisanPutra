
package com.shambhu.kisanputra.data.models.response.detail_product;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("catid")
    @Expose
    private Integer catid;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("messurment")
    @Expose
    private String messurment;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("subscription_histroy")
    @Expose
    private Boolean subscriptionHistroy;
    @SerializedName("time")
    @Expose
    private Time time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getMessurment() {
        return messurment;
    }

    public void setMessurment(String messurment) {
        this.messurment = messurment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSubscriptionHistroy() {
        return subscriptionHistroy;
    }

    public void setSubscriptionHistroy(Boolean subscriptionHistroy) {
        this.subscriptionHistroy = subscriptionHistroy;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}
