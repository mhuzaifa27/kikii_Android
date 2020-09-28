package com.example.kikkiapp.Netwrok;

import com.example.kikkiapp.Callbacks.CallbackAddComment;
import com.example.kikkiapp.Callbacks.CallbackFacebookLogin;
import com.example.kikkiapp.Callbacks.CallbackGetCommunityPosts;
import com.example.kikkiapp.Callbacks.CallbackGetPostComments;
import com.example.kikkiapp.Callbacks.CallbackInstagramFields;
import com.example.kikkiapp.Callbacks.CallbackInstagramLogin;
import com.example.kikkiapp.Callbacks.CallbackInstagramOAuth;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.google.gson.JsonElement;
import com.example.kikkiapp.Callbacks.CallbackSentOTP;
import com.example.kikkiapp.Callbacks.CallbackUpdateProfile;
import com.example.kikkiapp.Callbacks.CallbackVerifyOTP;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    //* News Info API ---------------------------------------------------- *//*

    @POST("continue/with-phone")
    @FormUrlEncoded
    Call<CallbackSentOTP> sendOTP(@FieldMap Map<String, String> params);

    @POST("continue/with-facebook")
    @FormUrlEncoded
    Call<CallbackFacebookLogin> facebookLogin(@FieldMap Map<String, String> params);

    @POST("continue/with-instagram")
    @FormUrlEncoded
    Call<CallbackInstagramLogin> instagramLogin(@FieldMap Map<String, String> params);

    @POST("update/profile")
    @FormUrlEncoded
    Call<CallbackUpdateProfile> register(@Header("Authorization") String auth,
                                         @FieldMap Map<String, String> params);

    @POST("update/profile")
    @FormUrlEncoded
    Call<CallbackUpdateProfile> updateProfile(@Header("Authorization") String auth,
                                              @FieldMap Map<String, String> params);

    @POST("access_token")
    @FormUrlEncoded
    Call<CallbackInstagramOAuth> instagramAccessToken(@FieldMap Map<String, String> params);

    @POST("verify/phone")
    @FormUrlEncoded
    Call<CallbackVerifyOTP> verifyOTP(@Header("Authorization") String auth,
                                      @FieldMap Map<String, String> params);

    @POST("create/post")
    @FormUrlEncoded
    Call<CallbackStatus> createPost(@Header("Authorization") String auth,
                                    @FieldMap Map<String, String> params);

    @Multipart
    @POST("update/profile")
    Call<CallbackUpdateProfile> updateProfilePhoto(
            @Header("Authorization") String auth,
            @Part MultipartBody.Part file);

    @POST("add/comment")
    @FormUrlEncoded
    Call<CallbackAddComment> addComment(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @Multipart
    @POST("update/profile")
    Call<JsonElement> uploadFile(@Part MultipartBody.Part file, @Header("Authorization") String authorization);

    @Multipart
    @POST("update/profile")
    Call<JsonElement> uploadFile(@Part MultipartBody.Part file);


    @GET("me")
    Call<CallbackInstagramFields> instagramGetFields(@Query("fields") String fields,
                                                     @Query("access_token") String access_token);

    @GET("resend/phone-verification-code")
    Call<CallbackStatus> resendOTP(@Header("Authorization") String auth);


    @GET("like/post")
    Call<CallbackStatus> likePost(@Header("Authorization") String auth,
                                  @Query("id") String id);

    @GET("dislike/post")
    Call<CallbackStatus> dislikePost(@Header("Authorization") String auth,
                                     @Query("id") String id);

    @GET("post/comments")
    Call<CallbackGetPostComments> getPostComments(@Header("Authorization") String auth,
                                     @Query("post_id") String id);
    @GET("community")
    Call<CallbackGetCommunityPosts> getAllPosts(@Header("Authorization") String auth,
                                               @Query("offset") String next_offset);

   /* @POST("login")
    @FormUrlEncoded
    Call<CallbackLogin> loginUser(@Field("email") String email,
                                  @Field("password") String password,
                                  @Field("latitude") String lat,
                                  @Field("longitude") String lng);

    @POST("updateFCM")
    @FormUrlEncoded
    Call<CallbackUpdateFCM> updateFCM(@Field("fcm_token") String fcm_token,
                                      @Field("token") String access_token);

    @POST("comment")
    @FormUrlEncoded
    Call<CallbackCommentAdded> addComment(@Field("comment") String comment,
                                          @Field("profile_id") String profile_id,
                                          @Field("tags") String tags,
                                          @Field("ids") String ids,
                                          @Field("token") String access_token);

    @Multipart
    @POST("comment")
    Call<CallbackCommentAdded> addCommentWithImage(@PartMap() Map<String, RequestBody> partMap,
                                                   @Part MultipartBody.Part file);

    @POST("comment/edit")
    @FormUrlEncoded
    Call<CallbackCommentUpdate> editComment(@Field("comment") String comment,
                                            @Field("comment_id") String comment_id,
                                            @Field("token") String access_token);

    @POST("comment/edit")
    @FormUrlEncoded
    Call<CallbackCommentUpdate> editComment(@Field("comment") String comment,
                                            @Field("comment_id") String comment_id,
                                            @Field("tags") String tags,
                                            @Field("ids") String ids,
                                            @Field("token") String access_token);

    @POST("comment/edit")
    @FormUrlEncoded
    Call<CallbackCommentUpdate> editCommentWithRemoveImage(@Field("comment") String comment,
                                                           @Field("comment_id") String comment_id,
                                                           @Field("tags") String tags,
                                                           @Field("ids") String ids,
                                                           @Field("action") String action,
                                                           @Field("token") String access_token);

    @POST("comment/edit")
    @FormUrlEncoded
    Call<CallbackCommentUpdate> editCommentWithRemoveImage(@Field("comment") String comment,
                                                           @Field("comment_id") String comment_id,
                                                           @Field("action") String action,
                                                           @Field("token") String access_token);

    @Multipart
    @POST("comment/edit")
    Call<CallbackCommentUpdate> editCommentWithImage(@PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part file);


    @POST("reply")
    @FormUrlEncoded
    Call<CallbackReplyAdded> addReply(@Field("reply") String reply,
                                      @Field("tags") String tags,
                                      @Field("ids") String ids,
                                      @Field("comment_id") String comment_id,
                                      @Field("token") String access_token);

    @POST("reply/edit")
    @FormUrlEncoded
    Call<CallEditReply> editReply(@Query("token") String access_token,
                                  @Field("reply_id") String reply_id,
                                  @Field("reply") String reply,
                                  @Field("tags") String tags,
                                  @Field("ids") String ids);


    @POST("stream/create")
    @FormUrlEncoded
    Call<CallbackCreateStream> createStream(
            @Field("category_id") String category_id,
            @Field("subcategory_id") String subcategory_id,
            @Field("title") String title,
            @Field("paid") String paid,
            @Field("location") String location,
            @Field("amount") String amount,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("token") String access_token);

    @POST("event/create")
    @FormUrlEncoded
    Call<CallbackCreateEvent> createEventWithOutImage(
            @Field("title") String title,
            @Field("category_id") String category_id,
            @Field("starts_at") String starts_at,
            @Field("ends_at") String ends_at,
            @Field("paid") String paid,
            @Field("limited") String limited,
            @Field("type") String type,
            @Field("tickets") String tickets,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("token") String access_token);

    @Multipart
    @POST("updateProfilePhoto")
    Call<CallbackProfilePhotoUpdate> updateProfilePhoto(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("event/create")
    Call<CallbackCreateEvent> createEventWithImage(@PartMap() Map<String, RequestBody> partMap,
                                                   @Part MultipartBody.Part file);


    //////////////////////update Cover////////////////////

    @Multipart
    @POST("updateCover")
    Call<CallbackCoverPhoto> updateCoverPhoto(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part file);
    //////////////////////update Cover////////////////////


    ///////////////////////////////////////////social login////////////////////////////////////////
    @POST("login")
    @FormUrlEncoded
    Call<CallbackSocialRegister> socialRegisterUser(@Field("email") String email,
                                                    @Field("name") String name,
                                                    @Field("phone") String phone,
                                                    @Field("fb_id") String fb_id,
                                                    @Field("photo") String photo,
                                                    @Field("latitude") String latitude,
                                                    @Field("longitude") String longitude);

    @POST("updatePassword")
    @FormUrlEncoded
    Call<CallbackUpdatePassword> updatePassword(@Field("password") String password,
                                                @Field("password_confirmation") String password_confirmation,
                                                @Field("old_password") String old_password,
                                                @Field("token") String token);

    @POST("resetPassword")
    @FormUrlEncoded
    Call<CallbackResetPassword> resetPassword(@Field("password") String password,
                                              @Field("password_confirmation") String password_confirmation,
                                              @Field("otp") String old_password);

    @POST("updateProfile")
    @FormUrlEncoded
    Call<CallbackUpdateProfile> updateProfile(@Field("name") String name,
                                              @Field("username") String username,
                                              @Field("email") String email,
                                              @Field("address") String address,
                                              @Field("phone") String phone,
                                              @Field("withdrawl_account") String withdrawl_account,
                                              @Field("token") String token);

    @POST("forgetPassword")
    @FormUrlEncoded
    Call<CallbackForgotPassword> forgotPassword(@Field("email") String email);


    @POST("event/buy")
    @FormUrlEncoded
    Call<CallbackBuyEvent> buyEvent(@Field("event_id") String event_id,
                                    @Field("amount") String amount,
                                    @Field("nonce") String nonce,
                                    @Field("token") String token);

    @POST("event/checkin")
    @FormUrlEncoded
    Call<CallbackCheckInEvent> checkInEvent(@Field("event_id") String event_id,
                                            @Field("token") String token);

//    @POST("login")
//    @FormUrlEncoded
//    Call<CallbackSocialRegister> socialRegisterUser(@Field("email") String email,
//                                                    @Field("name") String name,
//                                                    @Field("photo") String photo,
//                                                    @Field("phone") String phone,
//                                                    @Field("fb_id") String fb_id);

    @POST("stream/buy")
    @FormUrlEncoded
    Call<CallbackPaymentForStream> streamPayment(@Field("stream_id") String stream_id,
                                                 @Field("amount") String amount,
                                                 @Field("nonce") String nonce,
                                                 @Field("token") String token);

    @POST("stream/donate")
    @FormUrlEncoded
    Call<CallbackPaymentForTip> tipPayment(@Field("stream_id") String stream_id,
                                           @Field("amount") String amount,
                                           @Field("nonce") String nonce,
                                           @Field("token") String token);


    @POST("block")
    @FormUrlEncoded
    Call<CallbackBlockUser> blockUser(@Field("blocked_id") String id,
                                      @Query("token") String access_token);

//field is use for form data
//path is use for path find eg {id}
///////////////////////////////////////////////////////////////////////////////////////////

    *//**
     * GET API's
     **//*
///////////////////////////////////////////////////////////////////////////////////////////
    @GET("unblock/{id}")
    Call<CallbackUnblockUser> unBlockUser(@Path("id") String id,
                                          @Query("token") String access_token);


//
//    @POST("login")
//    @FormUrlEncoded
//    Call<CallbackLogin> loginUser(@Field("email") String email,
//                                  @Field("password") String password);
///////////////////////////////////social login//////////////////////////////////////////

/////////////////////////for terms and conditions/////////////////////////////

    @GET("setting")
    Call<CallBackSettingData> getSettingsData(@Query("token") String access_token);

    /////////////////////////for terms and conditions/////////////////////////////

    /////////////////////////for get folllowers/////////////////////////////
    @GET("followers")
    Call<CallBackFollowers> getFollowers(@Query("token") String access_token);

    @GET("followers/{id}")
    Call<CallBackFollowers> getSpecificPersonFollowers(@Path("id") String id,
                                                       @Query("token") String access_token);

    /////////////////////////for terms and conditions/////////////////////////////


    @GET("profile")
    Call<CallbackUserProfile> getUserProfile(@Query("token") String access_token);

    @GET("categories")
    Call<CallbackCategories> getCategories(@Query("token") String access_token);

    @GET("comments/profile/{id}")
    Call<CallbackCommentsDetail> getComments(@Path("id") String profile,
                                             @Query("token") String access_token);

    @GET("comment/delete/{id}")
    Call<CallbackDeleteComment> deleteComment(@Path("id") String id,
                                              @Query("token") String access_token);

    @GET("comment/{comment_id}/reaction/{id}")
    Call<CallbackCommentReaction> react(@Path("comment_id") String comment_id,
                                        @Path("id") String reaction_value,
                                        @Query("token") String access_token);

    @GET("search")
    Call<CallbackSearchResults> getSearchResults(@Query("user") String user,
                                                 @Query("category_id") String category_id,
                                                 @Query("subcategory_id") String subcategory_id,
                                                 @Query("latitude") String latitude,
                                                 @Query("longitude") String longitude,
                                                 @Query("token") String access_token);

    @GET("profile/{id}")
    Call<CallbackUserProfile> getSpecificUserProfile(@Path("id") String id,
                                                     @Query("token") String access_token);

    @GET("event/{id}")
    Call<CallbackEventDetail> getEventDetail(@Path("id") String id,
                                             @Query("token") String access_token);

    @GET("streams/global")
    Call<CallbackAllStreams> getGlobalStreams(
            @Query("category_id") String category_id,
            @Query("token") String access_token);

    @GET("streams/following")
    Call<CallbackAllStreams> getFollowingStreams(
            @Query("token") String access_token
    );

    @GET("streams/global")
    Call<CallbackAllStreams> getGlobalStreams(
            @Query("token") String access_token);

    @GET("streams/following")
    Call<CallbackAllStreams> getFollowingStreams(
            @Query("category_id") String category_id,
            @Query("token") String access_token
    );

    @GET("stream/start/{id}")
    Call<CallbackStreamStatus> startStream(@Path("id") String id,
                                           @Query("token") String access_token);

    @GET("stream/complete/{id}")
    Call<CallbackStreamStatus> completeStream(@Path("id") String id,
                                              @Query("token") String access_token);

    @GET("follow/{id}")
    Call<CallbackFollowUnfollow> follow(@Path("id") String userId,
                                        @Query("token") String access_token);

    @GET("unfollow/{id}")
    Call<CallbackFollowUnfollow> unFollow(@Path("id") String userId,
                                          @Query("token") String access_token);

    @GET("events")
    Call<CallbackGetAllFutureEvents> getAllFutureEvents(
            @Query("dateTime") String dateTime,
            @Query("token") String access_token);

    @GET("events")
    Call<CallBackMyEvents> getMyEvents(
            @Query("user_id") String user_id,
            @Query("token") String access_token);

    @GET("events")
    Call<CallBackMyCheckinEvents> getMyCheckedInEvents(
            @Query("status") String status,
            @Query("token") String access_token);

    @GET("notifications")
    Call<CallbackNotifications> getNotifications(@Query("token") String access_token);

    @GET("withdrawls")
    Call<CallbackWithdrawls> getWithdrawls(@Query("token") String access_token);

    @GET("reply/delete/{id}")
    Call<CallbackReplyDeleted> deleteReply(@Path("id") String id,
                                           @Query("token") String access_token);
*/
}
