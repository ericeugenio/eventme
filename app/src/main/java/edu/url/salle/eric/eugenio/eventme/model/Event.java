package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {

    @Expose
    @SerializedName("id")
    private long mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("type")
    private String mType;
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Expose
    @SerializedName("n_participators")
    private int mTotalParticipants;
    private int mCurrentParticipants;

    @Expose
    @SerializedName("image")
    private String mImage;

    @Expose
    @SerializedName("location")
    private String mLocation;

    private Date mCreationDate;
    @Expose
    @SerializedName("eventStart_date")
    private Date mStartDate;
    @Expose
    @SerializedName("eventEnd_date")
    private Date mEndDate;

    public Event() {

    }

    public Event(String name, String type, String description, int totalParticipants, String location, Date startDate, Date endDate) {
        this.mName = name;
        this.mType = type;
        this.mDescription = description;
        this.mTotalParticipants = totalParticipants;
        this.mLocation = location;
        this.mStartDate = startDate;
        this.mEndDate = endDate;

        this.mCurrentParticipants = 0;
        this.mCreationDate = new Date();
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public int getTotalParticipants() {
        return mTotalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.mTotalParticipants = totalParticipants;
    }

    public int getCurrentParticipants() {
        return mCurrentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.mCurrentParticipants = currentParticipants;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        this.mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        this.mEndDate = endDate;
    }
}
