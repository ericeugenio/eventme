package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.User;
import jp.shts.android.storiesprogressview.StoriesProgressView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Progress bar retrieved from:
// https://github.com/shts/StoriesProgressView

public class StoriesActivity extends AppCompatActivity implements View.OnTouchListener, StoriesProgressView.StoriesListener {

    // Hold
    // Duration in ms
    private final static long STORIES_DURATION = 3000L;
    private final static long MIN_PRESS_DURATION = 500L;
    private long pressDuration;

    // Swipe
    private final static float SWIPE_DISTANCE = 250f;
    private float pressPos;

    // Widgets
    private StoriesProgressView mStoriesProgressView;
    private ImageView mImage;
    private TextView mName, mType, mDate, mDescription;

    private List<Event> mTimeline;
    private int eventPointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mImage = findViewById(R.id.stories_image);
        View viewReverse = findViewById(R.id.stories_reverse);
        View viewSkip = findViewById(R.id.stories_skip);

        viewReverse.setOnTouchListener(this);
        viewSkip.setOnTouchListener(this);

        mName = findViewById(R.id.stories_title);
        mType = findViewById(R.id.stories_type);
        mDate = findViewById(R.id.stories_date);
        mDescription = findViewById(R.id.stories_description);

//        // ---Provisional--------------------------------------------------------------------
//
//        mTimeline = new ArrayList<>();
//        mTimeline.add(R.drawable.img_story1);
//        mTimeline.add(R.drawable.img_story2);
//        mTimeline.add(R.drawable.img_story3);
//        mTimeline.add(R.drawable.img_story4);
//
//        // ----------------------------------------------------------------------------------

        // Get events
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getJoinedEvents(token, userId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    mTimeline = response.body();
                    if (mTimeline != null) {
                        mTimeline.sort((e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate()));
                    }

                    eventPointer = 0;
                    updateView();

                    mStoriesProgressView = findViewById(R.id.stories_progress_view);
                    mStoriesProgressView.setStoriesCount(mTimeline.size());
                    mStoriesProgressView.setStoryDuration(STORIES_DURATION);
                    mStoriesProgressView.setStoriesListener(StoriesActivity.this);
                    mStoriesProgressView.startStories();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, StoriesActivity.class);
    }

    // ----------------------------------------------
    // PROGRESS VIEW
    // ----------------------------------------------

    @Override
    public void onNext() {
        eventPointer++;
        updateView();
    }

    @Override
    public void onPrev() {
        if (eventPointer > 0) {
            eventPointer--;
            updateView();
        }
    }

    private void updateView() {
        Event event = mTimeline.get(eventPointer);

        // Image
        Glide.with(this)
                .load(event.getImage())
                .placeholder(R.drawable.img_placeholder_event)
                .error(Event.getDefaultImage(event.getType()))
                .into(mImage);

        // Details
        mName.setText(event.getName());
        mDescription.setText(event.getDescription());
        mType.setText(event.getType());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'at' h:mm a", Locale.ENGLISH);
        String date = "From " + sdf.format(event.getStartDate()) + " to " + sdf.format(event.getEndDate());
        mDate.setText(date);
    }

    @Override
    public void onComplete() {
        finish();
    }

    @Override
    protected void onDestroy() {
        mStoriesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Pressed
            pressPos = event.getY();

            pressDuration = System.currentTimeMillis();
            mStoriesProgressView.pause();

            return false;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Released
            // Detect swipe down
            if (event.getY() - pressPos > SWIPE_DISTANCE) {
                finish();
                return true;
            }

            // Detect hold
            mStoriesProgressView.resume();
            return MIN_PRESS_DURATION < System.currentTimeMillis() - pressDuration;
        } else {
            return false;
        }
    }

    public void onClickUpdateStories(View view) {
        if (view.getId() == R.id.stories_skip) {
            mStoriesProgressView.skip();
        } else {
            mStoriesProgressView.reverse();
        }
    }
}