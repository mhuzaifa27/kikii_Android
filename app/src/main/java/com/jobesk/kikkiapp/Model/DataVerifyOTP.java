
package com.jobesk.kikkiapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataVerifyOTP {

    @SerializedName("user")
    @Expose
    private UserVerifyOTP user;

    public UserVerifyOTP getUser() {
        return user;
    }

    public void setUser(UserVerifyOTP user) {
        this.user = user;
    }

}
