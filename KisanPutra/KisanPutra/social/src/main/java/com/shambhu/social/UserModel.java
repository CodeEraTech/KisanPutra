package com.shambhu.social;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 3/1/2018.
 */

public class UserModel implements Parcelable {

    public UserModel(){

    }

    protected UserModel(Parcel in) {
        profile_picture = in.readString();
        id = in.readString();
        name = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        isVerified = in.readByte() != 0;
        mobile_no= in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SocialLoginType getSocialLoginType() {
        return mSocialLoginType;
    }

    public void setSocialLoginType(SocialLoginType socialLoginType) {
        mSocialLoginType = socialLoginType;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    String profile_picture;
    String id,name,first_name,last_name,email,mobile_no;
    SocialLoginType mSocialLoginType;
    boolean isVerified;



    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profile_picture);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeByte((byte) (isVerified ? 1 : 0));
        dest.writeString(mobile_no);
    }
}
