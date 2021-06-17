package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    private static User user;

    private Token mToken;
    @Expose(serialize = false)
    @SerializedName("id")
    private long mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("last_name")
    private String mLastName;
    @Expose
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("password")
    private String mPassword;
    @Expose
    @SerializedName("image")
    private String mImage;

    public User() {
    }

    public User(String name, String lastName, String email, String password, String image) {
        this.mName = name;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mPassword = password;
        this.mImage = image;
    }

    public static User getUser() {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    public String getToken() {
        return mToken.getToken();
    }

    public void setToken(Token token) {
        this.mToken = token;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }
}
