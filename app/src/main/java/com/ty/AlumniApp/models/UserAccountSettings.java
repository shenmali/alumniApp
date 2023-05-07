package com.ty.AlumniApp.models;

import android.os.Parcel;
import android.os.Parcelable;


public class UserAccountSettings implements Parcelable{

    private String description = "asdf";
    private String display_name = "asdf";
    private long followers;
    private long following;
    private long posts;
    private String profile_photo = "asdf";
    private String username = "asdf";
    private String whatsapp = "asdf";
    private String user_id = "asdf";

    private String work = "asdf";

    private String communication = "asdf";

    private String socialMedia = "asdf";


    private int graduationYear = 0;

    private int entryYear = 0;

    public UserAccountSettings(String description, String display_name, long followers,
                               long following, long posts, String profile_photo, String username,
                               String whatsapp, String user_id, String graduationYear, String entryYear, String work, String communication, String socialMedia ) {
        this.description = description;
        this.display_name = display_name;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.profile_photo = profile_photo;
        this.username = username;
        this.whatsapp = whatsapp;
        this.user_id = user_id;
        this.graduationYear = Integer.parseInt(graduationYear);
        this.entryYear = Integer.parseInt(entryYear);
        this.communication = communication;
        this.socialMedia = socialMedia;
        this.work = work;

    }

    public UserAccountSettings() {

    }

    protected UserAccountSettings(Parcel in) {
        description = in.readString();
        display_name = in.readString();
        followers = in.readLong();
        following = in.readLong();
        posts = in.readLong();
        profile_photo = in.readString();
        username = in.readString();
        whatsapp = in.readString();
        user_id = in.readString();
        graduationYear = in.readInt();
        entryYear = in.readInt();
        communication = in.readString();
        socialMedia = in.readString();
        work = in.readString();
    }

    public static final Creator<UserAccountSettings> CREATOR = new Creator<UserAccountSettings>() {
        @Override
        public UserAccountSettings createFromParcel(Parcel in) {
            return new UserAccountSettings(in);
        }

        @Override
        public UserAccountSettings[] newArray(int size) {
            return new UserAccountSettings[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public long getPosts() {
        return posts;
    }

    public void setPosts(long posts) {
        this.posts = posts;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public int getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }


    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "description='" + description + '\'' +
                ", display_name='" + display_name + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                ", profile_photo='" + profile_photo + '\'' +
                ", username='" + username + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                ", user_id='" + user_id + '\'' +
                ", graduationYear=" + graduationYear +
                ", entryYear=" + entryYear +
                ", work='" + work + '\'' +
                ", communication='" + communication + '\'' +
                ", socialMedia='" + socialMedia + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(display_name);
        dest.writeLong(followers);
        dest.writeLong(following);
        dest.writeLong(posts);
        dest.writeString(profile_photo);
        dest.writeString(username);
        dest.writeString(whatsapp);
        dest.writeString(user_id);
        dest.writeString(work);
        dest.writeString(communication);
        dest.writeString(socialMedia);
        dest.writeInt(graduationYear);
        dest.writeInt(entryYear);
    }


}
