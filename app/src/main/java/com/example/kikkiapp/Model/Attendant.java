package com.example.kikkiapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendant {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    public Attendant(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
