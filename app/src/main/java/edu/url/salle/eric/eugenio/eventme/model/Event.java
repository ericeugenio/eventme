package edu.url.salle.eric.eugenio.eventme.model;

import java.util.Date;

public class Event {

    private String mName;
    private String mType;
    private String mDescription;

    private int mTotalParticipants;
    private int mCurrentParticipants;

    // TODO: Change to image
    private int mImageID;

    // TODO : Change to android.location
    private String mLocation;

    private final Date mCreationDate;
    private Date mStartDate;
    private Date mEndDate;

    public Event(String name, String type, String description, int totalParticipants, String location, Date startDate, Date endDate) {
        this.mName = name;
        this.mType = type;
        this.mDescription = description;
        this.mTotalParticipants = totalParticipants;
        this.mLocation = location;
        this.mStartDate = startDate;
        this.mEndDate = endDate;

        //this.imageID = ;
        this.mCurrentParticipants = 0;
        this.mCreationDate = new Date();
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int mImageID) {
        this.mImageID = mImageID;
    }

    public int getTotalParticipants() {
        return mTotalParticipants;
    }

    public void setTotalParticipants(int mTotalParticipants) {
        this.mTotalParticipants = mTotalParticipants;
    }

    public int getCurrentParticipants() {
        return mCurrentParticipants;
    }

    public void setCurrentParticipants(int mCurrentParticipants) {
        this.mCurrentParticipants = mCurrentParticipants;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date mStartDate) {
        this.mStartDate = mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date mEndDate) {
        this.mEndDate = mEndDate;
    }
}
