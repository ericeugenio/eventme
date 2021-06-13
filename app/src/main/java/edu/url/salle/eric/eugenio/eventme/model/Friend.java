package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend {

    @Expose
    @SerializedName("id")
    private long mFriendId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("last_name")
    private String mLastName;
    @Expose
    @SerializedName("email")
    private String mEmail;

    public Friend() {

    }

    public Friend(long friendId, String name, String lastName, String email) {
        this.mFriendId = friendId;
        this.mName = name;
        this.mLastName = lastName;
        this.mEmail = email;
    }

    public long getFriendId() {
        return mFriendId;
    }

    public void setFriendId(long friendId) {
        this.mFriendId = friendId;
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
}
