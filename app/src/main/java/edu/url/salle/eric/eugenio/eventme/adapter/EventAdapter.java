package edu.url.salle.eric.eugenio.eventme.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private List<Event> mEvents;
    private EventListener mListener;

    public EventAdapter(List<Event> events) {
        this.mEvents = events;
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
        View participantsTextView = cardView.findViewById(R.id.card_event_participants);
        View currentParticipantsTextView = cardView.findViewById(R.id.card_event_current_participants);
        TextView numParticipantsTextView = cardView.findViewById(R.id.card_event_num_participants);

        // Image
        //Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), event.getImageID());
        //imageView.setImageDrawable(drawable);

        // Type, date, name, location
        String date = (event.getStartDate() == null) ? "Not defined" : dateFormat.format(event.getStartDate());

        typeTextView.setText(event.getType());
        dateTextView.setText(date);
        nameTextView.setText(event.getName());
        locationTextView.setText(event.getLocation());

        // Participants
        int participantsBarWidth = participantsTextView.getWidth();
        int currentParticipantsBarWidth = (event.getCurrentParticipants() * participantsBarWidth) / event.getTotalParticipants();
        //currentParticipantsTextView.setWidth(currentParticipantsBarWidth);

        String numParticipants = event.getCurrentParticipants() + "/" + event.getTotalParticipants();
        numParticipantsTextView.setText(numParticipants);

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
