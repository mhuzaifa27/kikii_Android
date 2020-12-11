
package com.example.kikkiapp.Callbacks;

import com.example.kikkiapp.Model.SinglePost;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallbackSinglePost {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("post")
    @Expose
    private SinglePost post;

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

    public SinglePost getPost() {
        return post;
    }

    public void setPost(SinglePost post) {
        this.post = post;
    }

}
