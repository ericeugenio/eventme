package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

// Progress bar retrieved from:
// https://github.com/shts/StoriesProgressView

public class StoriesActivity extends AppCompatActivity implements View.OnTouchListener, StoriesProgressView.StoriesListener {

    // Hold
    // Duration in ms
    private final static long STORIES_DURATION = 2000L;
    private final static long MIN_PRESS_DURATION = 500L;
    private long pressDuration;

    // Swipe
    private final static float SWIPE_DISTANCE = 250f;
    private float pressPos;

    // Widgets
    private StoriesProgressView mStoriesProgressView;
    private ImageView mImage;

    private List<Integer> mTimeline;
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

        // ---Provisional--------------------------------------------------------------------

//        mTimeline = new ArrayList<>();
//        mTimeline.add(R.drawable.img_story1);
//        mTimeline.add(R.drawable.img_story2);
//        mTimeline.add(R.drawable.img_story3);
//        mTimeline.add(R.drawable.img_story4);

        // ----------------------------------------------------------------------------------

        // TODO: get API data

        eventPointer = 0;
        updateView();

        mStoriesProgressView = findViewById(R.id.stories_progress_view);
        mStoriesProgressView.setStoriesCount(mTimeline.size());
        mStoriesProgressView.setStoryDuration(STORIES_DURATION);
        mStoriesProgressView.setStoriesListener(this);
        mStoriesProgressView.startStories();
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
        mImage.setImageResource(mTimeline.get(eventPointer));
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
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Released
            // Detect swipe down
            if (event.getY() - pressPos > SWIPE_DISTANCE) {
                finish();
                return true;
            }

            // Detect hold
            mStoriesProgressView.resume();
            return MIN_PRESS_DURATION < System.currentTimeMillis() - pressDuration;
        }
        else {
            return false;
        }
    }

    public void onClickUpdateStories(View view) {
        if (view.getId() == R.id.stories_skip) {
            mStoriesProgressView.skip();
        }
        else {
            mStoriesProgressView.reverse();
        }
    }
}