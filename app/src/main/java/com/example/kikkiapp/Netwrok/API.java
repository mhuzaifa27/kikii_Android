package com.example.kikkiapp.Netwrok;

import com.example.kikkiapp.Callbacks.CallbackAddComment;
import com.example.kikkiapp.Callbacks.CallbackContinueWithPhone;
import com.example.kikkiapp.Callbacks.CallbackFacebookLogin;
import com.example.kikkiapp.Callbacks.CallbackGetCategory;
import com.example.kikkiapp.Callbacks.CallbackGetCategoryChip;
import com.example.kikkiapp.Callbacks.CallbackGetCommunityPosts;
import com.example.kikkiapp.Callbacks.CallbackGetConversationMessages;
import com.example.kikkiapp.Callbacks.CallbackGetOnlineUsers;
import com.example.kikkiapp.Callbacks.CallbackGetEvents;
import com.example.kikkiapp.Callbacks.CallbackGetMyFriends;
import com.example.kikkiapp.Callbacks.CallbackGetFilters;
import com.example.kikkiapp.Callbacks.CallbackGetKikiiPosts;
import com.example.kikkiapp.Callbacks.CallbackGetMatch;
import com.example.kikkiapp.Callbacks.CallbackGetMeetUsers;
import com.example.kikkiapp.Callbacks.CallbackGetPendingRequests;
import com.example.kikkiapp.Callbacks.CallbackGetPostComments;
import com.example.kikkiapp.Callbacks.CallbackGetProfile;
import com.example.kikkiapp.Callbacks.CallbackGetSentRequests;
import com.example.kikkiapp.Callbacks.CallbackInstagramFields;
import com.example.kikkiapp.Callbacks.CallbackInstagramLogin;
import com.example.kikkiapp.Callbacks.CallbackInstagramOAuth;
import com.example.kikkiapp.Callbacks.CallbackSendMessage;
import com.example.kikkiapp.Callbacks.CallbackSinglePost;
import com.example.kikkiapp.Callbacks.CallbackSpecificUserPosts;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.google.gson.JsonElement;
import com.example.kikkiapp.Callbacks.CallbackUpdateProfile;
import com.example.kikkiapp.Callbacks.CallbackVerifyOTP;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    //* News Info API ---------------------------------------------------- *//*

    @POST("continue/with-phone")
    @FormUrlEncoded
    Call<CallbackContinueWithPhone> continueWithPhone(@FieldMap Map<String, String> params);

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

    @POST("update/comment/{id}")
    @FormUrlEncoded
    Call<CallbackAddComment> updateComment(
            @Header("Authorization") String auth,
            @Path("id") String id,
            @FieldMap Map<String, String> params);

    @Multipart
    @POST("add/comment")
    Call<CallbackAddComment> addCommentWithImages(@Header("Authorization") String auth,
                                                  @PartMap Map<String, String> text,
                                                  @Part MultipartBody.Part image);

    @POST("attend/event")
    @FormUrlEncoded
    Call<CallbackStatus> attendEvent(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("update/filters")
    @FormUrlEncoded
    Call<CallbackStatus> updateFilters(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("send/message")
    @FormUrlEncoded
    Call<CallbackSendMessage> sendMessage(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("like/user")
    @FormUrlEncoded
    Call<CallbackStatus> likeUser(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("dislike/user")
    @FormUrlEncoded
    Call<CallbackStatus> dislikeUser(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("follow/user")
    @FormUrlEncoded
    Call<CallbackStatus> followUser(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("unfollow/user")
    @FormUrlEncoded
    Call<CallbackStatus> unFollowUser(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("block/user")
    @FormUrlEncoded
    Call<CallbackStatus> blockUser(
            @Header("Authorization") String auth,
            @FieldMap Map<String, String> params);

    @POST("rewind-swipes")
    Call<CallbackStatus> rewindSwipes(
            @Header("Authorization") String auth);

    @Multipart
    @POST("update/profile")
    Call<JsonElement> uploadFile(@Part MultipartBody.Part file, @Header("Authorization") String authorization);

    @Multipart
    @POST("update/profile")
    Call<JsonElement> uploadFile(@Part MultipartBody.Part file);

    @Multipart
    @POST("create/post")
    Call<CallbackStatus> createPostWithMedia(@Header("Authorization") String auth,
                                             @PartMap Map<String, RequestBody> text,
                                             @Part List<MultipartBody.Part> images);

    @Multipart
    @POST("update/post/{id}")
    Call<CallbackStatus> updatePostWithNewMedia(@Header("Authorization") String auth,
                                                @Path("id") String id,
                                                @PartMap Map<String, RequestBody> text,
                                                @Part List<MultipartBody.Part> images);

    @POST("update/post/{id}")
    @FormUrlEncoded
    Call<CallbackStatus> updatePost(@Header("Authorization") String auth,
                                    @Path("id") String id,
                                    @FieldMap Map<String, String> text);

    @Multipart
    @POST("update/profile")
    Call<CallbackUpdateProfile> updateProfileWithImages(@Header("Authorization") String auth,
                                                        @PartMap Map<String, String> text,
                                                        @Part List<MultipartBody.Part> images);

    @Multipart
    @POST("update/profile")
    Call<CallbackUpdateProfile> updateOtherImages(@Header("Authorization") String auth,
                                                  @Part List<MultipartBody.Part> images);

    @GET("me")
    Call<CallbackInstagramFields> instagramGetFields(@Query("fields") String fields,
                                                     @Query("access_token") String access_token);

    @GET("resend/phone-verification-code")
    Call<CallbackStatus> resendOTP(@Header("Authorization") String auth);

    @GET("meet")
    Call<CallbackGetMeetUsers> getMeetUsers(@Header("Authorization") String auth);

    @GET("get/filters")
    Call<CallbackGetFilters> getFilters(@Header("Authorization") String auth);

    @GET("likedislike/post/{id}")
    Call<CallbackStatus> likeDislikePost(@Header("Authorization") String auth,
                                         @Path("id") String id);

    @GET("conversation/messages")
    Call<CallbackGetConversationMessages> getConversationMessages1(@Header("Authorization") String auth,
                                                                   @Query("conversation_id") String conversation_id);

    @GET("conversation/messages")
    Call<CallbackGetConversationMessages> getConversationMessages2(@Header("Authorization") String auth,
                                                                   @Query("user_match_id") String conversation_id);

    @GET("post/comments")
    Call<CallbackGetPostComments> getPostComments(@Header("Authorization") String auth,
                                                  @Query("post_id") String id);

    @GET("post/comments")
    Call<CallbackGetPostComments> getSinglePostComments(@Header("Authorization") String auth,
                                                        @Query("post_id") String id,
                                                        @Query("offset") String next_offset);


    @GET("post")
    Call<CallbackSinglePost> getSinglePost(@Header("Authorization") String auth,
                                           @Query("id") String id);

    @GET("community")
    Call<CallbackGetCommunityPosts> getAllPosts(@Header("Authorization") String auth,
                                                @Query("offset") String next_offset);

    @GET("user/posts")
    Call<CallbackSpecificUserPosts> getUserPosts(@Header("Authorization") String auth,
                                                 @Query("offset") String next_offset,
                                                 @Query("user_id") String user_id);

    @GET("online-users")
    Call<CallbackGetOnlineUsers> getOnlineUsers(@Header("Authorization") String auth);

    @GET("get/events")
    Call<CallbackGetEvents> getEvents(@Header("Authorization") String auth,
                                      @Query("offset") String next_offset);

    @GET("pending/requests")
    Call<CallbackGetPendingRequests> getPendingRequests(@Header("Authorization") String auth,
                                                        @Query("offset") String next_offset);

    @GET("my/friends")
    Call<CallbackGetMyFriends> getMyFriends(@Header("Authorization") String auth,
                                            @Query("offset") String next_offset);

    @GET("user/friends")
    Call<CallbackGetMyFriends> getUserFriends(@Header("Authorization") String auth,
                                              @Query("user_id") String next_offset);

    @GET("sent/requests")
    Call<CallbackGetSentRequests> getSentRequests(@Header("Authorization") String auth,
                                                  @Query("offset") String next_offset);

    @GET("profile")
    Call<CallbackGetProfile> getProfile(@Header("Authorization") String auth,
                                        @Query("user_id") String user_id);

    @GET("event")
    Call<CallbackGetProfile> getSingleEvent(@Header("Authorization") String auth,
                                        @Query("id") String id);

    @GET("get/category/{name}")
    Call<CallbackGetCategory> getCategory(@Header("Authorization") String auth,
                                          @Path("name") String name);

    @GET("get/category/{name}")
    Call<CallbackGetCategoryChip> getCategoryChip(@Header("Authorization") String auth,
                                                  @Path("name") String name);

    @GET("posts")
    Call<CallbackGetKikiiPosts> getKikiiPosts(@Header("Authorization") String auth,
                                              @Query("offset") String next_offset);

    @GET("match")
    Call<CallbackGetMatch> getMatch(@Header("Authorization") String auth);

    @DELETE("delete/comment/{id}")
    Call<CallbackStatus> deleteComment(@Path("id") String id,
                                       @Header("Authorization") String auth);

    @DELETE("delete/conversation/{id}")
    Call<CallbackStatus> deleteConversation(@Path("id") String id,
                                            @Header("Authorization") String auth);

    @DELETE("delete/post/{id}")
    Call<CallbackStatus> deletePost(@Path("id") String id,
                                    @Header("Authorization") String auth);

    @POST("save/report")
    @FormUrlEncoded
    Call<CallbackStatus> reportPost(@Field("post_id") String id,
                                    @Header("Authorization") String auth);

    @POST("save/report")
    @FormUrlEncoded
    Call<CallbackStatus> reportComment(@Field("comment_id") String id,
                                       @Header("Authorization") String auth);

    @POST("save/report")
    @FormUrlEncoded
    Call<CallbackStatus> reportUser(@Field("user_id") String id,
                                    @Header("Authorization") String auth);
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
