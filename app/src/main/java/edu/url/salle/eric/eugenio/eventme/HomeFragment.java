package edu.url.salle.eric.eugenio.eventme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.url.salle.eric.eugenio.eventme.model.Event;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Recycler view
    private RecyclerView mEventRecycler;
    private EventAdapter mEventAdapter;

    // Filter chips
    private Button mSelectedChip;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        configureChips(view);
        configureRecycleView(view);

        return view;
    }

    // ----------------------------------------------
    // FILTER CHIPS
    // ----------------------------------------------

    // Sets the listener for all of the chips
    private void configureChips(View view) {
        Button chipAllEvents = view.findViewById(R.id.chip_allEvents);
        chipAllEvents.setOnClickListener(this::onCLickFilterEvents);

        Button chipMusic = view.findViewById(R.id.chip_music);
        chipMusic.setOnClickListener(this::onCLickFilterEvents);

        Button chipEducation = view.findViewById(R.id.chip_education);
        chipEducation.setOnClickListener(this::onCLickFilterEvents);

        Button chipSport = view.findViewById(R.id.chip_sport);
        chipSport.setOnClickListener(this::onCLickFilterEvents);

        Button chipGame = view.findViewById(R.id.chip_game);
        chipGame.setOnClickListener(this::onCLickFilterEvents);

        Button chipTravel = view.findViewById(R.id.chip_travel);
        chipTravel.setOnClickListener(this::onCLickFilterEvents);

        // Set initial chip
        mSelectedChip = chipAllEvents;
        mSelectedChip.setSelected(true);
    }

    public void onCLickFilterEvents(View view) {
        // Update filter only if chip selected is different than the current
        if (view.getId() != mSelectedChip.getId()) {
            // Restore previous chip
            mSelectedChip.setSelected(false);

            // Update current chip
            mSelectedChip = view.findViewById(view.getId());
            mSelectedChip.setSelected(true);

            // TODO: filter
        }
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mEventRecycler = view.findViewById(R.id.home_recyclerview_event);

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