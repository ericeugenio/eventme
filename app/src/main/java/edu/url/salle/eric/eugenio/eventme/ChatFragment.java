package edu.url.salle.eric.eugenio.eventme;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.url.salle.eric.eugenio.eventme.adapters.FriendAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Friend;

public class ChatFragment extends Fragment {

    // Recycler view
    private RecyclerView mFriendRecycler;
    private FriendAdapter mFriendAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        configureRecycleView(view);

        return view;
    }

    // ----------------------------------------------
    // RECYCLER VIEW
    // ----------------------------------------------

    private void configureRecycleView(View view) {
        mFriendRecycler = view.findViewById(R.id.chat_recyclerview);

        // ---Provisional--------------------------------------------------------------------

        List<Friend> friends = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            friends.add(new Friend(i, "Friend", " " + i, "friend" + i + "@mail.com"));
        }

        // ----------------------------------------------------------------------------------

        mFriendAdapter = new FriendAdapter(friends);
        mFriendAdapter.setListener(this::onCLickStartChatActivity);

        mFriendRecycler.setAdapter(mFriendAdapter);
        mFriendRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void onCLickStartChatActivity(int position) {
        if (isAdded()) {
            Intent intent = IndividualChatActivity.newIntent(getActivity(), position);
            getActivity().startActivity(intent);
        }
        // TODO: notify activity cannot start
    }
}