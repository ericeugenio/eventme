package edu.url.salle.eric.eugenio.eventme.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private static List<Event> mEvents = new ArrayList<>();
    private EventListener mListener;

    private Fragment mFragment;

    public EventAdapter(Fragment fragment) {
        this.mFragment = fragment;
    }

    public static List<Event> getEvents() {
        return mEvents;
    }

    public static void setEvents(List<Event> events) {
        EventAdapter.mEvents = events;
    }

    public void setListener(EventListener eventListener) {
        this.mListener = eventListener;
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    @NonNull
    @Override
    public EventAdapter.EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_event_layout, parent, false);
        return new EventHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, final int position) {
        CardView cardView = holder.cardView;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d 'at' hh:mm a", Locale.ENGLISH);
        Event event = mEvents.get(position);

        ImageView imageView = cardView.findViewById(R.id.card_event_image);
        TextView typeTextView = cardView.findViewById(R.id.card_event_type);
        TextView dateTextView = cardView.findViewById(R.id.card_event_date);
        TextView nameTextView = cardView.findViewById(R.id.card_event_name);
        TextView locationTextView = cardView.findViewById(R.id.card_event_location);
        TextView numParticipantsTextView = cardView.findViewById(R.id.card_event_num_participants);

        // Image
        Glide.with(mFragment)
                .load(event.getImage())
                .placeholder(R.drawable.img_placeholder_event)
                .error(Event.getDefaultImage(event.getType()))
                .into(imageView);

        // Type, date, name, location
        String date = (event.getStartDate() == null) ? "Not defined" : dateFormat.format(event.getStartDate());

        typeTextView.setText(event.getType());
        dateTextView.setText(date);
        nameTextView.setText(event.getName());
        locationTextView.setText(event.getLocation());

        // Participants
        String participants = "Maximum participants: " + event.getTotalParticipants();
        numParticipantsTextView.setText(participants);

        // Set onCLick operation
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });
    }

    // Interface to decouple the Adapter
    public interface EventListener {
        void onClick(int position);
    }

    // View Holder
    public static class EventHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public EventHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}