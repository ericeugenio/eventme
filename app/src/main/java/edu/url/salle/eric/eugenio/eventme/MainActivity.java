package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.url.salle.eric.eugenio.eventme.fragment.ChatsListFragment;
import edu.url.salle.eric.eugenio.eventme.fragment.HomeFragment;
import edu.url.salle.eric.eugenio.eventme.fragment.MyEventsFragment;
import edu.url.salle.eric.eugenio.eventme.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private Button mSelectedView;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectedView = findViewById(R.id.button_nav_home);
        mSelectedView.setSelected(true);

        // Add home fragment by default
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.nav_host_fragment, HomeFragment.getInstance());
        mFragmentTransaction.commit();
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MainActivity.class);
    }

    public void onClickChangeView(View view) {
        // Change view only if selected is different than the current
        if (view.getId() != mSelectedView.getId()) {
            // Restore previous view
            mSelectedView.setSelected(false);

            // Update current view
            mSelectedView = findViewById(view.getId());
            mSelectedView.setSelected(true);

            replaceFragment(getFragment(view.getId()));
        }
    }

    private Fragment getFragment(int id) {
        switch (id) {
            case R.id.button_nav_home:
                return HomeFragment.getInstance();
            case R.id.button_nav_chat:
                return new ChatsListFragment();
            case R.id.button_nav_myEvents:
                return new MyEventsFragment();
            case R.id.button_nav_profile:
                return new ProfileFragment();
            default:
                return null;
        }
    }

    private void replaceFragment(Fragment fragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        mFragmentTransaction.commit();
    }

    public void onClickNewEvent(View view) {
        Intent intent = NewEventActivity.newIntent(this);
        startActivity(intent);
    }
}