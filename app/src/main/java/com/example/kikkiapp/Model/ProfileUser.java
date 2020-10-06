
package com.example.kikkiapp.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileUser {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("phone_verified")
    @Expose
    private Integer phoneVerified;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("profile_verified")
    @Expose
    private Integer profileVerified;
    @SerializedName("gender_identity")
    @Expose
    private String genderIdentity;
    @SerializedName("sexual_identity")
    @Expose
    private Object sexualIdentity;
    @SerializedName("pronouns")
    @Expose
    private String pronouns;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("relationship_status")
    @Expose
    private String relationshipStatus;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("looking_for")
    @Expose
    private String lookingFor;
    @SerializedName("drink")
    @Expose
    private String drink;
    @SerializedName("smoke")
    @Expose
    private String smoke;
    @SerializedName("cannabis")
    @Expose
    private String cannabis;
    @SerializedName("political_views")
    @Expose
    private String politicalViews;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("diet_like")
    @Expose
    private String dietLike;
    @SerializedName("sign")
    @Expose
    private String sign;
    @SerializedName("pets")
    @Expose
    private String pets;
    @SerializedName("kids")
    @Expose
    private String kids;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("tiktok")
    @Expose
    private String tiktok;
    @SerializedName("last_online")
    @Expose
    private String lastOnline;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("friends_count")
    @Expose
    private Integer friends_count;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("profile_pics")
    @Expose
    private List<ProfilePic> profilePics = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(Integer friends_count) {
        this.friends_count = friends_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Integer phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getProfileVerified() {
        return profileVerified;
    }

    public void setProfileVerified(Integer profileVerified) {
        this.profileVerified = profileVerified;
    }

    public String getGenderIdentity() {
        return genderIdentity;
    }

    public void setGenderIdentity(String genderIdentity) {
        this.genderIdentity = genderIdentity;
    }

    public Object getSexualIdentity() {
        return sexualIdentity;
    }

    public void setSexualIdentity(Object sexualIdentity) {
        this.sexualIdentity = sexualIdentity;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getCannabis() {
        return cannabis;
    }

    public void setCannabis(String cannabis) {
        this.cannabis = cannabis;
    }

    public String getPoliticalViews() {
        return politicalViews;
    }

    public void setPoliticalViews(String politicalViews) {
        this.politicalViews = politicalViews;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getDietLike() {
        return dietLike;
    }

    public void setDietLike(String dietLike) {
        this.dietLike = dietLike;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTiktok() {
        return tiktok;
    }

    public void setTiktok(String tiktok) {
        this.tiktok = tiktok;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ProfilePic> getProfilePics() {
        return profilePics;
    }

    public void setProfilePics(List<ProfilePic> profilePics) {
        this.profilePics = profilePics;
    }

}
