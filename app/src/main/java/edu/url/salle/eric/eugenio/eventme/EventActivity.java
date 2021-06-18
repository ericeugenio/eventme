package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Attendance;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_POS = "EVENT_POS";

    // Widgets
    private ImageView mImageHeader;
    private TextView mTitle, mLocation;
    private TextView mMonth, mNumDay, mWeekDay, mDuration, mCapacity;
    private TextView mDescription, mReadMore;
    private ShapeableImageView mOrganiserImage;
    private TextView mName, mEmail;
    private Button mJoinButton;

    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        configureView();
    }

    public static Intent newIntent(Context packageContext, int eventPos) {
        Intent intent = new Intent(packageContext, EventActivity.class);
        intent.putExtra(EXTRA_EVENT_POS, eventPos);
        return intent;
    }

    // ----------------------------------------------
    // VIEW CONFIGURATION
    // ----------------------------------------------

    private void configureView() {
        int position = getIntent().getIntExtra(EXTRA_EVENT_POS, 0);
        mEvent = EventAdapter.getEvents().get(position);

        // Show delete button only if event is from user and has not started
        if (User.getUser().getId() == mEvent.getOrganiserId()) {
            if (new Date().before(mEvent.getStartDate())) {
                ImageButton delete = findViewById(R.id.event_delete);
                delete.setVisibility(View.VISIBLE);
            }
        }

        configureHeader();
        configureDates();
        configureCapacity();
        configureOrganiser();
        configureFooter();
    }

    private void configureHeader() {
        mImageHeader = findViewById(R.id.event_imageHeader);
        mTitle = findViewById(R.id.event_title);
        mLocation = findViewById(R.id.event_address);

        // Image
        Glide.with(this)
                .load(mEvent.getImage())
                .placeholder(R.drawable.img_placeholder_event)
                .error(Event.getDefaultImage(mEvent.getType()))
                .into(mImageHeader);

        // Title and location
        mTitle.setText(mEvent.getName());
        mLocation.setText(mEvent.getLocation());
    }

    private void configureDates() {
        mMonth = findViewById(R.id.event_month);
        mNumDay = findViewById(R.id.event_numDay);
        mWeekDay = findViewById(R.id.event_weekDay);
        mDuration = findViewById(R.id.event_duration);

        // Dates
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.ENGLISH);

        mMonth.setText(sdf.format(mEvent.getStartDate()).toUpperCase());

        sdf.applyLocalizedPattern("dd");
        mNumDay.setText(sdf.format(mEvent.getStartDate()));

        sdf.applyLocalizedPattern("EEEE");
        mWeekDay.setText(sdf.format(mEvent.getStartDate()));

        sdf.applyLocalizedPattern("h:mm a");
        String duration = sdf.format(mEvent.getStartDate()) + " - " + sdf.format(mEvent.getEndDate());
        mDuration.setText(duration);
    }

    private void configureCapacity() {
        mCapacity = findViewById(R.id.event_capacity);

        // Participants
        String token = User.getUser().getToken();
        ApiAdapter.getInstance().getEventAttendances(token, mEvent.getId()).enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                if (response.isSuccessful()) {
                    String capacity = (response.body() != null) ? response.body().size() + "/" : "0/";
                    capacity = capacity + mEvent.getTotalParticipants();
                    mCapacity.setText(capacity);
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {
            }
        });
    }

    private void configureOrganiser() {
        mOrganiserImage = findViewById(R.id.event_organiser_image);
        mName = findViewById(R.id.event_organiser_name);
        mEmail = findViewById(R.id.event_organiser_email);

        // Organiser
        String token = User.getUser().getToken();
        ApiAdapter.getInstance().getUser(token, mEvent.getOrganiserId()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    User user = response.body().get(0);

                    // Image
                    Glide.with(EventActivity.this)
                            .load(user.getImage())
                            .placeholder(R.drawable.img_placeholder_event)
                            .error(R.drawable.img_default_profile)
                            .into(mOrganiserImage);

                    String name = user.getName() + " " + user.getLastName();
                    mName.setText(name);
                    mEmail.setText(user.getEmail());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }

    private void configureFooter() {
        mDescription = findViewById(R.id.event_description);
        mReadMore = findViewById(R.id.event_read_more);
        mJoinButton = findViewById(R.id.event_joinButton);

        // Description
        mDescription.setText(mEvent.getDescription());
        if (mDescription.getLineCount() < 5) {
            mReadMore.setVisibility(View.GONE);
        }

        // Button
        if (mEvent.getStartDate().before(new Date())) {
            mJoinButton.setVisibility(View.GONE);
        }
        else {
            String token = User.getUser().getToken();
            ApiAdapter.getInstance().getJoinedFutureEvents(token, User.getUser().getId()).enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    if (response.isSuccessful()) {
                        for (Event e : response.body()) {
                            if (e.getId() == mEvent.getId()) {
                                mJoinButton.setText(R.string.event_cancel_button);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {

                }
            });
        }
    }

    // ----------------------------------------------
    // VIEW LOGIC
    // ----------------------------------------------

    public void onClickGoBack(View view) {
        finish();
    }

    public void onClickExpandDescription(View view) {
        if (mReadMore.getText().toString().equals("Read more")) {
            mDescription.setMaxLines(Integer.MAX_VALUE);
            mDescription.setEllipsize(null);
            mReadMore.setText(R.string.event_read_less);
        } else {
            mDescription.setMaxLines(4);
            mDescription.setEllipsize(TextUtils.TruncateAt.END);
            mReadMore.setText(R.string.event_read_more);
        }
    }

    public void onClickJoinEvent(View view) {
        if (mJoinButton.getText().toString().equalsIgnoreCase("Join Event")) {
            confirmAttendance();
        }
        else {
            cancelAttendance();
        }
    }

    public void onClickContactOrganiser(View view) {
        Intent intent = ChatActivity.newIntent(this, -1, mEvent.getOrganiserId());
        startActivity(intent);
    }

    public void onClickDeleteEvents(View view) {
        String token = User.getUser().getToken();
        ApiAdapter.getInstance().removeEvent(token, mEvent.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // ----------------------------------------------
    // API LOGIC
    // ----------------------------------------------

    private void confirmAttendance() {
        String token = User.getUser().getToken();

        ApiAdapter.getInstance().confirmAttendance(token, mEvent.getId(), 1, null).enqueue(new Callback<Attendance>() {
            @Override
            public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                if (response.isSuccessful()) {
                    // Increase attendance by one
                    String capacity = mCapacity.getText().toString();
                    int curParticipants = Integer.parseInt(capacity.substring(0, capacity.indexOf("/")));

                    capacity = ++curParticipants + "/" + mEvent.getTotalParticipants();
                    mCapacity.setText(capacity);

                    // Update button
                    mJoinButton.setText(R.string.event_cancel_button);
                }
            }

            @Override
            public void onFailure(Call<Attendance> call, Throwable t) {

            }

        });
    }

    private void cancelAttendance() {
        String token = User.getUser().getToken();

        ApiAdapter.getInstance().cancelAttendance(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mJoinButton.setText(R.string.event_join_button);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}