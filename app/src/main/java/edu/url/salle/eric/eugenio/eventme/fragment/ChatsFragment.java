package edu.url.salle.eric.eugenio.eventme.fragment;

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

import edu.url.salle.eric.eugenio.eventme.ChatActivity;
import edu.url.salle.eric.eugenio.eventme.R;
import edu.url.salle.eric.eugenio.eventme.adapter.FriendAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Friend;

public class ChatsFragment extends Fragment {

    // Recycler view
    private RecyclerView mFriendRecycler;
    private FriendAdapter mFriendAdapter;

    public ChatsFragment() {
        // Required empty public constructor
    }

    public static ChatsFragment newInstance() {
        return new ChatsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

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
        for (int i = 1; i <= 3; i++) {
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
            Intent intent = ChatActivity.newIntent(getActivity(), position);
            getActivity().startActivity(intent);
        }
        // TODO: notify activity cannot start
    }
}