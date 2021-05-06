package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mSelectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectedView = findViewById(R.id.button_nav_profile);
        mSelectedView.setSelected(true);
    }

    public void onClickChangeView(View view) {
        // Change view only if selected is different than the current
        if (view.getId() != mSelectedView.getId()) {
            // Restore previous view
            mSelectedView.setSelected(false);

            // Update current view
            mSelectedView = findViewById(view.getId());
            mSelectedView.setSelected(true);

            // TODO: change fragment
        }
    }

    public void onClickNewEvent(View view) {
        Toast.makeText(this, "Creating new event", Toast.LENGTH_SHORT).show();
        // TODO: start NewEvent activity
    }
}