package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @Expose
    @SerializedName("id")
    private long mAssistantId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("last_name")
    private String mLastName;
    @Expose
    @SerializedName("image")
    private String mImage;
    @Expose
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("punctuation")
    private int mPunctuation;
    @Expose
    @SerializedName("comentary")
    private String mComment;

    public Attendance() {
    }

    public Attendance(User user) {
        this.mAssistantId = user.getId();
        this.mName = user.getName();
        this.mLastName = user.getLastName();
        this.mImage = user.getImage();
        this.mEmail = user.getEmail();
    }

    public int getPunctuation() {
        return mPunctuation;
    }

    public void setPunctuation(int punctuation) {
        this.mPunctuation = punctuation;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }
}
