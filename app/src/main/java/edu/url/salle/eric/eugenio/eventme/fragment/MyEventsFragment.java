package edu.url.salle.eric.eugenio.eventme.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.adapter.EventAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventsFragment extends Fragment {

    // Tab
    private TextView mSelectedTab;

    // Recycler view
    private RecyclerView mEventRecycler;
    private EventAdapter mEventAdapter;

    private MyEventsFragment() {
        // Required empty private constructor
    }

    public static MyEventsFragment newInstance() {
        return new MyEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);

        configureTab(view);
        configureRecycleView(view);

        return view;
    }

    // ----------------------------------------------
    // TAB
    // ----------------------------------------------

    private void configureTab(View view) {
        TextView upcomingEvents = view.findViewById(R.id.myEvents_upcomingEvents);
        upcomingEvents.setOnClickListener(this::onClickChangeTab);

        TextView pastEvents = view.findViewById(R.id.myEvents_pastEvents);
        pastEvents.setOnClickListener(this::onClickChangeTab);

        // Set initial tab
        mSelectedTab = upcomingEvents;
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
            else {
                getPastEvents();
            }
        }
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mEventRecycler = view.findViewById(R.id.myEvents_recyclerview);

//        // ---Provisional--------------------------------------------------------------------
//
//        List<Event> events = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            events.add(new Event("Event " + i, "Type", "Description",
//                    100, "Location", new Date(), new Date()));
//        }
//        // ----------------------------------------------------------------------------------

        mEventAdapter = new EventAdapter(this);
        mEventRecycler.setAdapter(mEventAdapter);
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        getFutureEvents();
    }

    // ----------------------------------------------
    // API LOGIC
    // ----------------------------------------------

    private void getFutureEvents() {
        String token = User.getUser().getToken();
        long userId = User.getUser().getId();

        ApiAdapter.getInstance().getJoinedFutureEvents(token, userId).enqueue(new Callback<List<Event>>() {
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

        ApiAdapter.getInstance().getJoinedPastEvents(token, userId).enqueue(new Callback<List<Event>>() {
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