package edu.url.salle.eric.eugenio.eventme.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.model.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    public static final int MESSAGE_RECEIVED = 0;
    public static final int MESSAGE_SENT = 1;

    private static List<Message> mMessages;

    public MessageAdapter(List<Message> messages) {
        mMessages = messages;
    }

    public static List<Message> getMessages() {
        return mMessages;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: get authenticated user id
        long userId = 1;
        long senderId = mMessages.get(position).getSenderId();
        return (senderId == userId) ? MESSAGE_SENT : MESSAGE_RECEIVED;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = (viewType == MESSAGE_SENT) ? R.layout.card_chat_right : R.layout.card_chat_left;

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        return new MessageHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, final int position) {
        CardView cardView = holder.cardView;
        Message message = mMessages.get(position);

        // Text
        TextView messageContent = cardView.findViewById(R.id.card_chat_text);
        messageContent.setText(message.getContent());
    }

    // View Holder
    public static class MessageHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public MessageHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
