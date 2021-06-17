package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;

public class EventActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_POS = "EVENT_POS";

    // Widgets
    private ImageView mImageHeader;
    private TextView mTitle, mLocation;
    private TextView mMonth, mNumDay, mWeekDay, mDuration, mCapacity;
    private TextView mDescription, mReadMore;
    private ShapeableImageView mOrganiserImage;
    private TextView mName, mEmail;

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

    private void configureView() {
        mImageHeader = findViewById(R.id.event_imageHeader);
        mTitle = findViewById(R.id.event_title);
        mLocation = findViewById(R.id.event_address);
        mMonth = findViewById(R.id.event_month);
        mNumDay = findViewById(R.id.event_numDay);
        mWeekDay = findViewById(R.id.event_weekDay);
        mDuration = findViewById(R.id.event_duration);
        mCapacity = findViewById(R.id.event_capacity);
        mDescription = findViewById(R.id.event_description);
        mReadMore = findViewById(R.id.event_read_more);
        mOrganiserImage = findViewById(R.id.event_organiser_image);
        mName = findViewById(R.id.event_organiser_name);
        mEmail = findViewById(R.id.event_organiser_email);

        int position = getIntent().getIntExtra(EXTRA_EVENT_POS, 0);
        mEvent = EventAdapter.getEvents().get(position);

        // Image
        Glide.with(this)
                .load(mEvent.getImage())
                .placeholder(R.drawable.img_placeholder_event)
                .error(Event.getDefaultImage(mEvent.getType()))
                .into(mImageHeader);

        // Title and location
        mTitle.setText(mEvent.getName());
        mLocation.setText(mEvent.getLocation());

        // Dates

        // Participants


        // Description
        mDescription.setText(mEvent.getDescription());
        if (mDescription.getLineCount() < 5 ) {
            mReadMore.setVisibility(View.GONE);
        }

        // Organiser
    }

    public void onClickGoBack(View view) {
        finish();
    }

    public void onClickExpandDescription(View view) {
        if (mReadMore.getText().toString().equals("Read more")) {
            mDescription.setMaxLines(Integer.MAX_VALUE);
            mDescription.setEllipsize(null);
            mReadMore.setText(R.string.event_read_less);
        }
        else {
            mDescription.setMaxLines(4);
            mDescription.setEllipsize(TextUtils.TruncateAt.END);
            mReadMore.setText(R.string.event_read_more);
        }
    }

    public void onClickJoinEvent(View view) {
    }

    public void onClickContactOrganiser(View view) {
    }
}