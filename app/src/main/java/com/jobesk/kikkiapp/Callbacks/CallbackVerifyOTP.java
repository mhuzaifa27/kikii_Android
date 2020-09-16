
package com.jobesk.kikkiapp.Callbacks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jobesk.kikkiapp.Model.DataVerifyOTP;

public class CallbackVerifyOTP {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataVerifyOTP data;

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

    public DataVerifyOTP getData() {
        return data;
    }

    public void setData(DataVerifyOTP data) {
        this.data = data;
    }

}
