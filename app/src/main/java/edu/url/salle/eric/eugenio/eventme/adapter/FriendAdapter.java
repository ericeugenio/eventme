package edu.url.salle.eric.eugenio.eventme.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.model.Friend;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {

    private static List<Friend> mFriends;
    private FriendListener mListener;

    public FriendAdapter(List<Friend> friends) {
        mFriends = friends;
    }

    public static List<Friend> getFriends() {
        return mFriends;
    }

    public void setListener(FriendListener friendListener) {
        this.mListener = friendListener;
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_friend_layout, parent, false);
        return new FriendHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, final int position) {
        CardView cardView = holder.cardView;
        Friend friend = mFriends.get(position);

        ShapeableImageView profileImageView = cardView.findViewById(R.id.card_friend_image);
        TextView nameTextView = cardView.findViewById(R.id.card_friend_name);
        TextView lastMessageTextView = cardView.findViewById(R.id.card_friend_lastMessage);
        View notificationView = cardView.findViewById(R.id.card_chat_notification);

        // Image
        // TODO: place friend image
        // Text
        String username = friend.getName() + friend.getLastName();
        nameTextView.setText(username);
        lastMessageTextView.setText(friend.getEmail());

        // Notification
        if (position % 2 == 0) notificationView.setBackgroundColor(Color.WHITE);

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
    public interface FriendListener {
        void onClick(int position);
    }

    // View Holder
    public static class FriendHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public FriendHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
