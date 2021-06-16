package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

    private TextView mDescription, mReadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mDescription = findViewById(R.id.event_description);
        mReadMore = findViewById(R.id.event_read_more);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, EventActivity.class);
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