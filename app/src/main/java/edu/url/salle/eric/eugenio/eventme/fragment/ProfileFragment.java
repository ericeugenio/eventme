package edu.url.salle.eric.eugenio.eventme.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.EditProfileActivity;
import edu.url.salle.eric.eugenio.eventme.LoginActivity;
import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.StoriesActivity;
import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.Friend;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    // Toolbar
    private Toolbar mToolbar;

    // Bottom sheet
    private ProfileBottomSheet mBottomSheet;

    // Timeline Button
    private ShapeableImageView mTimeLineButton;

    // Tab
    private TextView mSelectedTab;

    // Recycler view
    private RecyclerView mEventRecycler;
    private EventAdapter mEventAdapter;

    // Widgets
    private ShapeableImageView mProfileImage;
    private TextView mUsername, mEmail, mBio;
    private TextView mEventsCreated, mEventsJoined, mFriends;

    private ProfileFragment() {
        // Required empty private constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mToolbar = view.findViewById(R.id.profile_toolbar);

        // Set listener to view user timeline
        mTimeLineButton = view.findViewById(R.id.profile_image);
        mTimeLineButton.setOnClickListener(this::onClickShowTimeline);

        configureView(view);
        configureButtonSheet();
        configureTab(view);
        configureRecycleView(view);

        return view;
    }

    private void configureView(View view) {
        mProfileImage = view.findViewById(R.id.profile_image);
        mUsername = view.findViewById(R.id.profile_username);
        mEmail = view.findViewById(R.id.profile_email);
        mBio = view.findViewById(R.id.profile_bio);
        mEventsCreated = view.findViewById(R.id.profile_numEventsCreated);
        mEventsJoined = view.findViewById(R.id.profile_numEventsJoined);
        mFriends = view.findViewById(R.id.profile_numFriends);

        User user = User.getUser();

        // Image
        Glide.with(this)
                .load(user.getImage())
                .placeholder(R.drawable.img_placeholder_event)
                .error(R.drawable.img_default_profile)
                .into(mProfileImage);

        // Details
        String username = user.getName() + " " + user.getLastName();
        mUsername.setText(username);
        mEmail.setText(user.getEmail());

        if (user.getBio() == null) {
            mBio.setVisibility(View.GONE);
        }
        else {
            mBio.setText(user.getBio());
        }

        // Stats
        getJoinedEvents();
        getCreatedEvents();
        getFriends();
    }

    // ----------------------------------------------
    // TOOLBAR
    // ----------------------------------------------

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar.inflateMenu(R.menu.menu_profile);
        mToolbar.setOnMenuItemClickListener(this::onMenuItemClick);
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_editProfile:
                Intent intent = EditProfileActivity.newIntent(getActivity());
                getActivity().startActivity(intent);
                return true;
            case R.id.action_manageAccount:
                mBottomSheet.show(getParentFragmentManager(), ProfileBottomSheet.PROFILE_TAG);
                return true;
            default:
                return false;
        }
    }

    // ----------------------------------------------
    // BOTTOM SHEET
    // ----------------------------------------------

    private void configureButtonSheet() {
        mBottomSheet = new ProfileBottomSheet();
        mBottomSheet.setStyle(ProfileBottomSheet.STYLE_NORMAL, R.style.bottomSheet_style);
        mBottomSheet.setListener(this::onClickManageAccount);
    }

    private void onClickManageAccount(int actionId) {
        if (actionId == ProfileBottomSheet.DELETE_ACCOUNT_ID) {
            // TODO: delete account
        }

        User.clearUser();

        Intent intent = LoginActivity.newIntent(getActivity());
        getActivity().startActivity(intent);
        getActivity().finish();
    }


    // ----------------------------------------------
    // TIMELINE
    // ----------------------------------------------

    public void onClickShowTimeline(View view) {
        Intent intent = StoriesActivity.newIntent(getActivity());
        getActivity().startActivity(intent);
    }

    // ----------------------------------------------
    // TAB
    // ----------------------------------------------

    private void configureTab(View view) {
        TextView runningEvents = view.findViewById(R.id.profile_runningEvents);
        runningEvents.setOnClickListener(this::onClickChangeTab);

        TextView upcomingEvents = view.findViewById(R.id.profile_upcomingEvents);
        upcomingEvents.setOnClickListener(this::onClickChangeTab);

        TextView pastEvents = view.findViewById(R.id.profile_pastEvents);
        pastEvents.setOnClickListener(this::onClickChangeTab);

        // Set initial tab
        mSelectedTab = runningEvents;
        mSelectedTab.setSelected(true);
    }

    public void onClickChangeTab(View view) {
        // Update tab only if tab selected is different than the current
        if (view.getId() != mSelectedTab.getId()) {
            // Restore previous tab
            mSelectedTab.setSelected(false);

            // Update current tab
            mSelectedTab = view.findViewById(view.getId());
            mSelectedTab.setSelected(true);

            // Update events
            if (mSelectedTab.getText().toString().equals("upcoming events")) {
                getFutureEvents();
            }
            else if (mSelectedTab.getText().toString().equals("past events")) {
                getPastEvents();
            }
            else {
                getCurrentEvents();;
            }
        }
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mEventRecycler = view.findViewById(R.id.profile_recyclerview_event);

//        // ---Provisional--------------------------------------------------------------------
//
//        List<Event> events = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            events.add(new Event("Event " + i, "Type", "Description",
//                    100, "Location", new Date(), new Date()));
//        }
//
//        // ----------------------------------------------------------------------------------

        mEventAdapter = new EventAdapter(this);
        mEventRecycler.setAdapter(mEventAdapter);
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFutureEvents();
    }

    // ----------------------------------------------
    // API LOGIC
    // ----------------------------------------------

    private void getJoinedEvents() {
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getJoinedEvents(token, userId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    mEventsJoined.setText(String.valueOf(response.body().size()));
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    private void getCreatedEvents() {
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getCreatedEvents(token, userId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    mEventsCreated.setText(String.valueOf(response.body().size()));
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    private void getFriends() {
        String token = User.getUser().getToken();

        ApiAdapter.getInstance().getFriends(token).enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.isSuccessful()) {
                    mFriends.setText(String.valueOf(response.body().size()));
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });
    }

    private void getFutureEvents() {
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getCreatedFutureEvents(token, userId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    EventAdapter.setEvents(response.body());
                    mEventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    private void getPastEvents() {
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getCreatedPastEvents(token, userId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    EventAdapter.setEvents(response.body());
                    mEventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    private void getCurrentEvents() {
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getCreatedCurrentEvents(token, userId).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    EventAdapter.setEvents(response.body());
                    mEventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }
}