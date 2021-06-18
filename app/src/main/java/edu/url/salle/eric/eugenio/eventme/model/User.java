package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    // User authenticated
    private static User mUser;

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

    private String mBio;

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
        if (mUser == null) {
            mUser = new User();
        }

        return mUser;
    }

    public static void clearUser() {
        mUser = null;
    }

    public void updateUser(User user) {
        this.mId = user.mId;
        this.mToken = user.mToken;
        this.mName = user.mName;
        this.mLastName = user.mLastName;
        this.mEmail = user.mEmail;
        this.mPassword = user.mPassword;
    }

    public String getToken() {
        return mToken.getToken();
    }

    public void setToken(Token token) {
        token.extendToken();
        this.mToken = token;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
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

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        this.mBio = bio;
    }
}
