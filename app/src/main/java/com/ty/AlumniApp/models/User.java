package com.ty.AlumniApp.models;

import android.os.Parcel;
import android.os.Parcelable;



public class User implements Parcelable{

    private String user_id;
    private long phone_number;
    private String email;
    private String username;

    private int graduationYear;
    private int entryYear;

    private String description;
    private String display_name;
    private String work;
    private String communication;
    private String socialMedia;

    public User(String user_id, long phone_number, String email, String username) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
    }

    public User() {

    }


    protected User(Parcel in) {
        user_id = in.readString();
        phone_number = in.readLong();
        email = in.readString();
        username = in.readString();
        graduationYear = in.readInt();
        entryYear = in.readInt();
        description = in.readString();
        display_name = in.readString();
        work = in.readString();
        communication = in.readString();
        socialMedia = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getCommunication() {
        return communication;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public String getDisplayName() {
        return display_name;
    }



    public String setEmail() {
        return email;
    }

    public void getUsername(String username) {
        this.username = username;
    }

    public String setUsername() {
        return username;
    }

    public void getUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String setUser_id() {
        return user_id;
    }






    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", graduationYear=" + graduationYear +
                ", entryYear=" + entryYear +
                ", work=" + work +
                ", socialMedia=" + socialMedia +
                ", communication =" + communication +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeLong(phone_number);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(String.valueOf(graduationYear));
        dest.writeString(String.valueOf(entryYear));
        dest.writeString(description);
        dest.writeString(display_name);
        dest.writeString(work);
        dest.writeString(communication);
        dest.writeString(socialMedia);
    }


}
