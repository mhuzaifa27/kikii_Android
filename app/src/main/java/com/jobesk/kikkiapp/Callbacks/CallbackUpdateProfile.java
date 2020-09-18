
package com.jobesk.kikkiapp.Callbacks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jobesk.kikkiapp.Model.Data;
import com.jobesk.kikkiapp.Model.UserVerifyOTP;

public class CallbackUpdateProfile {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private UserVerifyOTP user;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public UserVerifyOTP getUser() {
        return user;
    }

    public void setUser(UserVerifyOTP user) {
        this.user = user;
    }
}
