package edu.url.salle.eric.eugenio.eventme.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.url.salle.eric.eugenio.eventme.EventActivity;
import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    // Recycler view
    private RecyclerView mEventRecycler;
    private EventAdapter mEventAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    // Filter chips
    private Button mSelectedChip;

    private HomeFragment() {
        // Required empty private constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
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

            // Update events
            String selectedChip = mSelectedChip.getText().toString();
            String type = (selectedChip.equals("All events")) ? null : selectedChip;
            updateEvents(type);

            mLinearLayoutManager.scrollToPosition(0);
        }
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mEventRecycler = view.findViewById(R.id.home_recyclerview_event);

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
        mEventAdapter.setListener(this::onCLickStartEventActivity);

        mEventRecycler.setAdapter(mEventAdapter);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mEventRecycler.setLayoutManager(mLinearLayoutManager);

        updateEvents(null);
    }

    private void onCLickStartEventActivity(int position) {
        if (isAdded()) {
            Intent intent = EventActivity.newIntent(getActivity(), position);
            getActivity().startActivity(intent);
        }
    }

    // ----------------------------------------------
    // API LOGIC
    // ----------------------------------------------

    private void updateEvents(String type) {
        ApiAdapter.getInstance().getAllEvents(type).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                EventAdapter.setEvents(filterPastEvents(response.body()));
                mEventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.api_connection_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Event> filterPastEvents(List<Event> events) {
        List<Event> filteredEvents = new ArrayList<>();

        for (Event e : events) {
            if (e.getStartDate() != null && e.getStartDate().after(new Date())) {
                filteredEvents.add(e);
            }
        }

        filteredEvents.sort((e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate()));

        return filteredEvents;
    }
}