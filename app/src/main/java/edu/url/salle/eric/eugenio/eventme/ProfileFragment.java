package edu.url.salle.eric.eugenio.eventme;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.url.salle.eric.eugenio.eventme.model.Event;

// TODO: create User class and remove hardcoded text from .xml

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        configureButtonSheet();
        configureTab(view);
        configureRecycleView(view);

        return view;
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

    @SuppressLint("NonConstantResourceId")
    private boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_editProfile:
                // TODO: start EditProfile activity
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
        if (actionId == ProfileBottomSheet.LOGOUT_ID) {
            // TODO: logout
            return;
        }
        // TODO: delete account
    }


    // ----------------------------------------------
    // TIMELINE
    // ----------------------------------------------

    public void onClickShowTimeline(View view) {
        // TODO: start Timeline activity
        Toast.makeText(view.getContext(), "Showing timeline", Toast.LENGTH_SHORT).show();
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

            // TODO: filter recycler view according selected tab
        }
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mEventRecycler = view.findViewById(R.id.profile_recyclerview_event);

        // ---Provisional--------------------------------------------------------------------

        List<Event> events = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            events.add(new Event("Event " + i, "Type", "Description",
                    100, "Location", new Date(), new Date()));
        }
        // ----------------------------------------------------------------------------------

        mEventAdapter = new EventAdapter(events);
        mEventRecycler.setAdapter(mEventAdapter);
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEventAdapter.setListener(this::onCLickStartEventActivity);
    }

    private void onCLickStartEventActivity(int position) {
        // TODO: start Event activity
        // Intent intent = new Intent(getActivity(), EventActivity.class);
        // intent.putExtra(EventActivity.EVENT_ID, position);
        // getActivity().startActivity(intent);
    }
}