
package com.example.kikkiapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteUser {

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
    private Object profilePic;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("upgraded")
    @Expose
    private Integer upgraded;
    @SerializedName("incognito")
    @Expose
    private Integer incognito;
    @SerializedName("show_location")
    @Expose
    private Integer showLocation;
    @SerializedName("gender_identity")
    @Expose
    private Object genderIdentity;
    @SerializedName("sexual_identity")
    @Expose
    private Object sexualIdentity;
    @SerializedName("pronouns")
    @Expose
    private Object pronouns;
    @SerializedName("bio")
    @Expose
    private Object bio;
    @SerializedName("relationship_status")
    @Expose
    private Object relationshipStatus;
    @SerializedName("height")
    @Expose
    private Object height;
    @SerializedName("looking_for")
    @Expose
    private Object lookingFor;
    @SerializedName("drink")
    @Expose
    private Object drink;
    @SerializedName("smoke")
    @Expose
    private Object smoke;
    @SerializedName("cannabis")
    @Expose
    private Object cannabis;
    @SerializedName("political_views")
    @Expose
    private Object politicalViews;
    @SerializedName("religion")
    @Expose
    private Object religion;
    @SerializedName("diet_like")
    @Expose
    private Object dietLike;
    @SerializedName("sign")
    @Expose
    private Object sign;
    @SerializedName("pets")
    @Expose
    private Object pets;
    @SerializedName("kids")
    @Expose
    private Object kids;
    @SerializedName("facebook")
    @Expose
    private Object facebook;
    @SerializedName("instagram")
    @Expose
    private Object instagram;
    @SerializedName("tiktok")
    @Expose
    private Object tiktok;
    @SerializedName("last_online")
    @Expose
    private String lastOnline;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
        this.profilePic = profilePic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getUpgraded() {
        return upgraded;
    }

    public void setUpgraded(Integer upgraded) {
        this.upgraded = upgraded;
    }

    public Integer getIncognito() {
        return incognito;
    }

    public void setIncognito(Integer incognito) {
        this.incognito = incognito;
    }

    public Integer getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(Integer showLocation) {
        this.showLocation = showLocation;
    }

    public Object getGenderIdentity() {
        return genderIdentity;
    }

    public void setGenderIdentity(Object genderIdentity) {
        this.genderIdentity = genderIdentity;
    }

    public Object getSexualIdentity() {
        return sexualIdentity;
    }

    public void setSexualIdentity(Object sexualIdentity) {
        this.sexualIdentity = sexualIdentity;
    }

    public Object getPronouns() {
        return pronouns;
    }

    public void setPronouns(Object pronouns) {
        this.pronouns = pronouns;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(Object relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public Object getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(Object lookingFor) {
        this.lookingFor = lookingFor;
    }

    public Object getDrink() {
        return drink;
    }

    public void setDrink(Object drink) {
        this.drink = drink;
    }

    public Object getSmoke() {
        return smoke;
    }

    public void setSmoke(Object smoke) {
        this.smoke = smoke;
    }

    public Object getCannabis() {
        return cannabis;
    }

    public void setCannabis(Object cannabis) {
        this.cannabis = cannabis;
    }

    public Object getPoliticalViews() {
        return politicalViews;
    }

    public void setPoliticalViews(Object politicalViews) {
        this.politicalViews = politicalViews;
    }

    public Object getReligion() {
        return religion;
    }

    public void setReligion(Object religion) {
        this.religion = religion;
    }

    public Object getDietLike() {
        return dietLike;
    }

    public void setDietLike(Object dietLike) {
        this.dietLike = dietLike;
    }

    public Object getSign() {
        return sign;
    }

    public void setSign(Object sign) {
        this.sign = sign;
    }

    public Object getPets() {
        return pets;
    }

    public void setPets(Object pets) {
        this.pets = pets;
    }

    public Object getKids() {
        return kids;
    }

    public void setKids(Object kids) {
        this.kids = kids;
    }

    public Object getFacebook() {
        return facebook;
    }

    public void setFacebook(Object facebook) {
        this.facebook = facebook;
    }

    public Object getInstagram() {
        return instagram;
    }

    public void setInstagram(Object instagram) {
        this.instagram = instagram;
    }

    public Object getTiktok() {
        return tiktok;
    }

    public void setTiktok(Object tiktok) {
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

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

}
