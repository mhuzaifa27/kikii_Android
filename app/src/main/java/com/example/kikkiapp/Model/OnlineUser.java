
package com.example.kikkiapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
