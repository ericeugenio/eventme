package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import edu.url.salle.eric.eugenio.eventme.R;

public class Event {

    @Expose(serialize = false)
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
    @Expose(serialize = false)
    @SerializedName("owner_id")
    private long mOrganiserId;

    public Event() {

    }

    public Event(String name, String type, String description, int totalParticipants, String location, String image, Date startDate, Date endDate) {
        this.mName = name;
        this.mType = type;
        this.mDescription = description;
        this.mTotalParticipants = totalParticipants;
        this.mLocation = location;
        this.mImage = image;
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

    public long getOrganiserId() {
        return mOrganiserId;
    }

    public void setOrganiserId(long organiserId) {
        this.mOrganiserId = organiserId;
    }

    public static int getDefaultImage(String type) {
        type = type.toLowerCase();

        switch (type) {
            case "music":
                return R.drawable.img_default_music;
            case "education":
                return R.drawable.img_default_education;
            case "sport":
                return R.drawable.img_default_sport;
            case "games":
                return R.drawable.img_default_games;
            case "travel":
                return R.drawable.img_default_travel;
            default:
                return R.drawable.img_default_event;
        }
    }
}
